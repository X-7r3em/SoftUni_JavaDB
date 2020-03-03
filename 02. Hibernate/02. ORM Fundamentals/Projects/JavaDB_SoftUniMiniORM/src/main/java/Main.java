import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Connector.createConnection("root", "1234", "mini_orm");
        Connection connection = Connector.getConnection();
        EntityManager<User> entityManager = new EntityManager<>(connection, User.class);
        User user = new User("Vasil", "1234", 15, Date.valueOf("2013-06-22"));
        entityManager.persists(user);
        user = new User("Geri", "Poznai", 16, Date.valueOf("2014-03-02"));
        entityManager.persists(user);
        user = new User("Nasko", "Poznai", 17, Date.valueOf("2016-06-02"));
        entityManager.persists(user);
        user = new User("Todor", "Poznai", 18, Date.valueOf("2017-12-02"));
        entityManager.persists(user);
        user = new User("Todor1", "Poznai", 19, Date.valueOf("2017-12-02"));
        entityManager.persists(user);
        user = new User("Todor2", "Poznai", 20, Date.valueOf("2017-12-02"));
        entityManager.persists(user);
        entityManager.delete(User.class, "age > 16");
    }
}
