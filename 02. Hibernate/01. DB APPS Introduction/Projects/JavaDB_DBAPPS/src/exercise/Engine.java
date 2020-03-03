package exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Engine implements Runnable {
    private Connection connection;
    private BufferedReader br;

    public Engine(Connection connection) {
        this.connection = connection;
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        try {
            //this.getVillainsNames();
            //this.getMinionNames();
            //this.addMinion();
            //this.changeTownNamesCasing();
            //this.removeVillain();
            //this.printAllMinionNames();
            //this.increaseMinionsAge();
            this.increaseAgeStoredProcedure();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void getVillainsNames() throws SQLException {
        String query =
                "SELECT CONCAT(v.name, ' ',COUNT(mv.minion_id)) AS output " +
                        "FROM villains AS v " +
                        "INNER JOIN minions_villains AS mv ON v.id = mv.villain_id " +
                        "GROUP BY v.id " +
                        "HAVING COUNT(mv.minion_id)> 15 " +
                        "ORDER BY COUNT(mv.minion_id) DESC;";

        PreparedStatement stmt = this.connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("output"));
        }

        this.connection.close();
    }

    private void getMinionNames() throws SQLException, IOException {
        String query =
                "SELECT " +
                        "    v.name villain_name, " +
                        "    m.name minion_name, " +
                        "    m.age minion_age " +
                        "FROM minions AS m " +
                        "INNER JOIN minions_villains AS mv ON m.id = mv.minion_id " +
                        "INNER JOIN villains v ON mv.villain_id = v.id " +
                        "WHERE v.id = ? " +
                        "GROUP BY m.id;";

        int id = Integer.parseInt(this.br.readLine());

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.setDouble(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.printf("No villain with ID %d exists in the database.", id);
            return;
        }

        StringBuilder output = new StringBuilder(String.format("Villain: %s%n",
                resultSet.getString("villain_name")));

        resultSet.beforeFirst();

        int counter = 1;
        while (resultSet.next()) {
            output.append(String.format("%d. %s %d%n", counter++,
                    resultSet.getString("minion_name"),
                    resultSet.getInt("minion_age")));
        }

        System.out.println(output.toString());
        this.connection.close();
    }

    private void addMinion() throws SQLException, IOException {
        //Villains and minions can be with the same name. They are not unique
        String[] tokens = this.br.readLine().split("\\s+");
        String minionName = tokens[1];
        if (minionName == null || minionName.isBlank()) {
            throw new IllegalArgumentException("Invalid minion name.");
        }

        int minionAge = Integer.parseInt(tokens[2]);
        if (minionAge < 0) {
            throw new IllegalArgumentException("Invalid minion age.");
        }

        String townName = tokens[3];
        if (townName == null || townName.isBlank()) {
            throw new IllegalArgumentException("Invalid town name.");
        }

        tokens = this.br.readLine().split("\\s+");
        String villainName = tokens[1];
        if (villainName == null || villainName.isBlank()) {
            throw new IllegalArgumentException("Invalid villain name");
        }

        String townCountQuery =
                "SELECT COUNT(t.name) count " +
                        "FROM towns t " +
                        "WHERE t.name = ?";

        PreparedStatement townCount = this.connection.prepareStatement(townCountQuery);
        townCount.setString(1, townName);

        ResultSet rs = townCount.executeQuery();
        rs.next();

        if (rs.getInt("count") == 0) {
            String insertTownQuery = "INSERT INTO towns (name) VALUES (?);";
            PreparedStatement insertTown = this.connection.prepareStatement(insertTownQuery);
            insertTown.setString(1, townName);

            insertTown.executeUpdate();
            System.out.printf("Town %s was added to the database.%n", townName);
        }

        String countVillainQuery =
                "SELECT COUNT(v.name) count " +
                        "FROM villains v " +
                        "WHERE v.name = ?;";

        PreparedStatement countVillain = this.connection.prepareStatement(countVillainQuery);
        countVillain.setString(1, villainName);

        ResultSet villainCount = countVillain.executeQuery();
        villainCount.next();
        if (villainCount.getInt("count") == 0) {
            String insertVillainQuery = "INSERT INTO villains (name, evilness_factor) VALUES(?, 'evil');";
            PreparedStatement insertVillain = this.connection.prepareStatement(insertVillainQuery);
            insertVillain.setString(1, villainName);

            insertVillain.executeUpdate();
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }

        String insertMinionQuery =
                "INSERT INTO minions (name, age, town_id) " +
                        "VALUES (?, ?, (SELECT t.id FROM towns t WHERE t.name = ?));";

        PreparedStatement insertMinion = this.connection.prepareStatement(insertMinionQuery);
        insertMinion.setString(1, minionName);
        insertMinion.setInt(2, minionAge);
        insertMinion.setString(3, townName);

        insertMinion.executeUpdate();
        String addMinionToVillainQuery =
                "INSERT INTO minions_villains (minion_id, villain_id) VALUES " +
                        "    ((SELECT id FROM minions WHERE name = ? ORDER BY id DESC LIMIT 1), " +
                        "     (SELECT id FROM villains WHERE name = ? ORDER BY id DESC LIMIT 1));";
        PreparedStatement addMinionToVillain = this.connection.prepareStatement(addMinionToVillainQuery);
        addMinionToVillain.setString(1, minionName);
        addMinionToVillain.setString(2, villainName);
        addMinionToVillain.executeUpdate();
        System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);

        this.connection.close();
    }

    private void changeTownNamesCasing() throws SQLException, IOException {
        String townsCountQuery =
                "SELECT COUNT(t.name) count " +
                        "FROM towns t " +
                        "WHERE t.country = ?;";

        PreparedStatement townStatement = this.connection.prepareStatement(townsCountQuery);

        String countryName = this.br.readLine();
        townStatement.setString(1, countryName);

        ResultSet resultSet = townStatement.executeQuery();
        resultSet.next();
        int townsCount = resultSet.getInt("count");
        if (townsCount == 0) {
            System.out.println("No town names were affected.");
            return;
        }

        String updateQuery =
                "UPDATE towns " +
                        "SET name = UPPER(name) " +
                        "WHERE country = ?;";

        PreparedStatement updateTownNames = this.connection.prepareStatement(updateQuery);
        updateTownNames.setString(1, countryName);
        updateTownNames.executeUpdate();

        String getUpdatedTownsQuery =
                "SELECT t.name " +
                        "FROM towns t " +
                        "WHERE t.country = ?";

        PreparedStatement getUpdatedTownsStatement = this.connection.prepareStatement(getUpdatedTownsQuery);
        getUpdatedTownsStatement.setString(1, countryName);
        ResultSet updatedTowns = getUpdatedTownsStatement.executeQuery();

        List<String> towns = new ArrayList<>();
        while (updatedTowns.next()) {
            towns.add(updatedTowns.getString("name"));
        }
        System.out.printf("%d town names were affected.%n", townsCount);
        System.out.println(towns);

        this.connection.close();
    }

    private void removeVillain() throws IOException, SQLException {
        int villainId = Integer.parseInt(this.br.readLine());
        String checkVillainIdQuery =
                "SELECT v.name, COUNT(mv.villain_id) count " +
                        "FROM villains v " +
                        "JOIN minions_villains mv ON v.id = mv.villain_id " +
                        "WHERE v.id = ? " +
                        "HAVING COUNT(v.id) > 0";

        PreparedStatement checkVillainIdStatement = this.connection.prepareStatement(checkVillainIdQuery);
        checkVillainIdStatement.setInt(1, villainId);
        ResultSet getVillainNameAndMinions = checkVillainIdStatement.executeQuery();
        if (!getVillainNameAndMinions.next()) {
            System.out.println("No such villain was found");
            return;
        }

        String villainName = getVillainNameAndMinions.getString("name");
        int minionsCount = getVillainNameAndMinions.getInt("count");

        String removeVillainProcedureQuery =
                "CREATE PROCEDURE udp_remove_villain_by_id(id INT(11)) " +
                        "BEGIN " +
                        "   DELETE FROM minions_villains mv " +
                        "   WHERE mv.villain_id = id; " +
                        "   DELETE FROM villains v " +
                        "   WHERE v.id = id; " +
                        "END ";

        PreparedStatement removeVillainProcedureStatement = this.connection.prepareStatement(removeVillainProcedureQuery);
        removeVillainProcedureStatement.executeUpdate();

        String callUPD = "CALL udp_remove_villain_by_id(?)";
        CallableStatement removeVillainProcedure = this.connection.prepareCall(callUPD);
        removeVillainProcedure.setInt(1, villainId);
        removeVillainProcedure.executeUpdate();

        System.out.printf(
                "%s was deleted%n" +
                        "%d minions released", villainName, minionsCount);
    }

    private void printAllMinionNames() throws IOException, SQLException {
        String getAllMinionNamesQuery =
                "SELECT m.name " +
                "FROM minions m " +
                "ORDER BY m.id;";

        Statement getAllMinionNamesStatement = this.connection.createStatement();

        ResultSet minionNames = getAllMinionNamesStatement.executeQuery(getAllMinionNamesQuery);

        List<String> names = new ArrayList<>();
        while (minionNames.next()){
            names.add(minionNames.getString("name"));
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            if (i % 2 == 0){
                output.append(names.get(i / 2)).append(System.lineSeparator());
            } else {
                output.append(names.get(names.size() - 1 - i / 2)).append(System.lineSeparator());
            }
        }

        System.out.println(output.toString());
    }

    private void increaseMinionsAge()throws  IOException, SQLException{
        String[] minionsIDs = this.br.readLine().split("\\s+");
        String queryPlaceholder = ", ?".repeat(minionsIDs.length).substring(2);
        String updateMinionNameAndAgeQuery =
                "UPDATE minions m " +
                "SET m.age = m.age + 1, m.name = LOWER(m.name) " +
                "WHERE m.id IN (" + queryPlaceholder +");";

        PreparedStatement updateMinionNameAndAgeStatement =
                this.connection.prepareStatement(updateMinionNameAndAgeQuery);

        for (int i = 0; i < minionsIDs.length; i++) {
            updateMinionNameAndAgeStatement.setInt(i + 1, Integer.parseInt(minionsIDs[i]));
        }

        updateMinionNameAndAgeStatement.executeUpdate();

        String getAllMinionNamesAndAgesQuery =
                "SELECT m.name, m.age " +
                "FROM minions m " +
                "ORDER BY m.id;";

        Statement getAllMinionNamesAndAgesStatement = this.connection.createStatement();

        ResultSet minionsNameAndAge = getAllMinionNamesAndAgesStatement.executeQuery(getAllMinionNamesAndAgesQuery);

        StringBuilder output = new StringBuilder();
        while (minionsNameAndAge.next()){
            output.append(minionsNameAndAge.getString("name"))
                    .append(" ")
                    .append(minionsNameAndAge.getInt("age"))
                    .append(System.lineSeparator());
        }

        System.out.print(output);
    }

    private void increaseAgeStoredProcedure() throws IOException, SQLException {
        String getOlderUSPQuery =
                "CREATE PROCEDURE usp_get_older(minion_id INT(11)) " +
                "BEGIN " +
                "    UPDATE minions m " +
                "    SET m.age = m.age + 1 " +
                "    WHERE m.id = minion_id; " +
                "END";

        Statement getOlderUSPStatement = this.connection.createStatement();

        getOlderUSPStatement.executeUpdate(getOlderUSPQuery);

        String callProcedureQuery = "CALL usp_get_older(?);";

        CallableStatement getOlderProcedure = this.connection.prepareCall(callProcedureQuery);

        int minionId = Integer.parseInt(this.br.readLine());
        getOlderProcedure.setInt(1, minionId);

        getOlderProcedure.executeUpdate();

        String getMinionNameAndAgeQuery =
                "SELECT m.name, m.age " +
                "FROM minions m " +
                "WHERE m.id = ?;";

        PreparedStatement getMinionNameAndAgeStatement = this.connection.prepareStatement(getMinionNameAndAgeQuery);
        getMinionNameAndAgeStatement.setInt(1, minionId);

        ResultSet minionNameAndAge = getMinionNameAndAgeStatement.executeQuery();

        if (minionNameAndAge.next()){
            System.out.printf("%s %d",
                    minionNameAndAge.getString("name"),
                    minionNameAndAge.getInt("age"));
        }
    }
}
