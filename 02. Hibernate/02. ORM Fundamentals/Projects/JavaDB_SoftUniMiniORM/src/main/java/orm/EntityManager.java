package orm;

import orm.Annotations.Column;
import orm.Annotations.Entity;
import orm.Annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.function.Function;

public class EntityManager<E> implements DBContext<E> {
    private static final String DATABASE_NAME = "mini_orm";
    private static final String INSERT_STATEMENT = "INSERT INTO %s (%s) VALUES (%s);";
    private static final String UPDATE_STATEMENT = "UPDATE %s SET %s WHERE id = %s;";
    private static final String SELECT_STATEMENT = "SELECT * FROM %s WHERE %s;";
    private static final String DELETE_STATEMENT = "DELETE FROM %s WHERE %s;";
    private static final String SELECT_FIRST_STATEMENT = "SELECT * FROM %s WHERE %s LIMIT 1;";
    private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE %s (%s);";
    private static final String ALTER_TABLE_STATEMENT = "ALTER TABLE %s %s";
    private static final String TABLE_EXISTS_STATEMENT = "SELECT TABLE_NAME " +
            "FROM information_schema.TABLES " +
            "WHERE table_schema = DATABASE() AND table_name = ?;";

    private Connection connection;

    public EntityManager(Connection connection, Class<E> entity) throws SQLException {
        this.connection = connection;
        if (!this.doesTableExist(entity)) {
            this.doCreate(entity);
        } else {
            this.doAlter(entity);
        }
    }

    public boolean persists(E entity) throws IllegalAccessException, SQLException {
        Field primary = this.getId(entity.getClass());
        primary.setAccessible(true);
        Object value = primary.get(entity);

        if (value == null || (int) value <= 0) {
            return this.doInsert(entity, primary);
        }

        return this.doUpdate(entity, primary);
    }

    public Iterable<E> find(Class<E> entity)
            throws SQLException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {

        return find(entity, "1");
    }

    public Iterable<E> find(Class<E> entity, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String tableName = this.getTableName(entity);
        String query = String.format(SELECT_STATEMENT, tableName, where);
        Statement statement = this.connection.createStatement();
        ResultSet tableValues = statement.executeQuery(query);

        List<E> users = new ArrayList<>();
        while (tableValues.next()) {
            E instance = entity.getConstructor().newInstance();
            this.fillEntity(entity, tableValues, instance);
            users.add(instance);
        }

        return users;
    }

    public E findFirst(Class<E> table) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return this.findFirst(table, "1");
    }

    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String tableName = this.getTableName(table);
        String query = String.format(SELECT_FIRST_STATEMENT, tableName, where);
        Statement statement = this.connection.createStatement();
        ResultSet tableValues = statement.executeQuery(query);
        if (!tableValues.next()) {
            return null;
        }

        E instance = table.getConstructor().newInstance();
        this.fillEntity(table, tableValues, instance);
        return instance;
    }

    @Override
    public void delete(Class<E> entity, String where) throws SQLException {
        String tableName = this.getTableName(entity);
        String query = String.format(DELETE_STATEMENT, tableName, where);

        PreparedStatement statement = this.connection.prepareStatement(query);
        statement.executeUpdate();
    }

    @Override
    public void delete(Class<E> entity) throws SQLException {
        this.delete(entity, "1");
    }

    private void doCreate(Class<E> entity) throws SQLException {
        Queue<String> columns = new ArrayDeque<>();

        Field[] fields = entity.getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            String columnName = f.getAnnotation(Column.class).name();
            String fieldType = this.getDBType(f);
            String filedPrimaryKey = "";
            if (f.isAnnotationPresent(Id.class)) {
                filedPrimaryKey = "PRIMARY KEY AUTO_INCREMENT";
            }

            String columnDefinition = String.format("%s %s %s", columnName, fieldType, filedPrimaryKey);
            columns.add(columnDefinition);
        }

        String tableName = this.getTableName(entity);
        String createQuery = String.format(CREATE_TABLE_STATEMENT, tableName, String.join(", ", columns));
        PreparedStatement createTable = this.connection.prepareStatement(createQuery);
        createTable.executeUpdate();
    }

    private void doAlter(Class entity) throws SQLException {
        String tableName = this.getTableName(entity);
        Field[] entityFields = entity.getDeclaredFields();
        Set<String> currentColumnNames = this.getColumnNames(tableName);
        List<String> alterations = new ArrayList<>();
        for (Field f : entityFields) {
            f.setAccessible(true);
            String entityFieldName = f.getAnnotation(Column.class).name();
            if (!currentColumnNames.contains(entityFieldName)) {
                alterations.add(String.format("ADD %s %s", entityFieldName, this.getDBType(f)));
            }
        }

        String alterQuery = String.format(ALTER_TABLE_STATEMENT, tableName, String.join(", ", alterations));
        PreparedStatement alterStatement = this.connection.prepareStatement(alterQuery);
        alterStatement.executeUpdate();
    }

    private Field getId(Class entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key"));
    }

    private boolean doInsert(E entity, Field primary) throws SQLException {
        String tableName = this.getTableName(entity.getClass());
        String[] fieldNames = this.getFieldNames(entity);
        String[] fieldValues = this.getFieldValues(entity);

        String query = String.format(INSERT_STATEMENT,
                tableName, String.join(", ", fieldNames), String.join(", ", fieldValues));

        return this.connection.prepareStatement(query).execute();
    }

    private boolean doUpdate(E entity, Field primary) throws SQLException, IllegalAccessException {
        String tableName = this.getTableName(entity.getClass());
        String[] fieldNames = this.getFieldNames(entity);
        String[] fieldValues = this.getFieldValues(entity);
        String[] nameValuePairs = new String[fieldNames.length];
        for (int i = 0; i < nameValuePairs.length; i++) {
            nameValuePairs[i] = String.format("%s = %s", fieldNames[i], fieldValues[i]);
        }
        primary.setAccessible(true);
        int id = (int) primary.get(entity);

        String query = String.format(UPDATE_STATEMENT, tableName, String.join(", ", nameValuePairs), id);

        return this.connection.prepareStatement(query).execute();
    }

    private String getTableName(Class cl) {
        Entity table = (Entity) cl.getAnnotation(Entity.class);
        return table.name();
    }

    private String[] getFieldNames(E entity) {
        return this.columnIterator(field -> (field.getDeclaredAnnotation(Column.class)).name(), entity);
    }

    private String[] getFieldValues(E entity) {
        Function<Field, String> fieldToValue = f -> {
            f.setAccessible(true);
            try {
                return "'" + f.get(entity).toString() + "'";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return "";
            }
        };

        return this.columnIterator(fieldToValue, entity);
    }

    private String[] columnIterator(Function<Field, String> function, E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(function)
                .toArray(String[]::new);
    }

    private void fillEntity(Class<E> table, ResultSet rs, E entity) throws SQLException, IllegalAccessException {
        Field[] fields = table.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            String columnName = field.getAnnotation(Column.class).name();
            this.fillField(field, entity, rs, columnName);
        }
    }

    private void fillField(Field field, Object instance, ResultSet rs, String columnName)
            throws SQLException, IllegalAccessException {
        field.setAccessible(true);
        if (field.getType() == int.class || field.getType() == Integer.class) {
            field.set(instance, rs.getInt(columnName));
        } else if (field.getType() == long.class || field.getType() == Long.class) {
            field.set(instance, rs.getLong(columnName));
        } else if (field.getType() == String.class) {
            field.set(instance, rs.getString(columnName));
        } else if (field.getType() == Date.class) {
            field.set(instance, rs.getDate(columnName));
        }
    }

    private String getDBType(Field field) {
        String fieldType = null;

        if (field.getType() == int.class || field.getType() == Integer.class) {
            fieldType = "INT(11)";
        } else if (field.getType() == String.class) {
            fieldType = "VARCHAR(50)";
        } else if (field.getType() == Date.class) {
            fieldType = "DATE";
        }

        return fieldType;
    }

    private HashSet<String> getColumnNames(String tableName) throws SQLException {
        String getCurrentColumnNamesQuery =
                String.format("SELECT COLUMN_NAME " +
                        "FROM information_schema.COLUMNS " +
                        "WHERE table_schema = 'mini_orm'" +
                        "   AND table_name = '%s';", tableName);

        PreparedStatement getCurrentColumnNamesStatement = this.connection.prepareStatement(getCurrentColumnNamesQuery);
        ResultSet currentColumnNamesSet = getCurrentColumnNamesStatement.executeQuery();
        HashSet<String> currentColumns = new HashSet<>();
        while (currentColumnNamesSet.next()) {
            currentColumns.add(currentColumnNamesSet.getString("COLUMN_NAME"));
        }

        return currentColumns;
    }

    private boolean doesTableExist(Class<E> entity) throws SQLException {
        String tableName = this.getTableName(entity);
        PreparedStatement tableExistsStatement = this.connection.prepareStatement(TABLE_EXISTS_STATEMENT);
        tableExistsStatement.setString(1, tableName);
        ResultSet table = tableExistsStatement.executeQuery();
        return table.isBeforeFirst();
    }
}
