package lab;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class DiabloApp {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String user = scanner.nextLine();
        String password = scanner.nextLine();
        Properties prop = new Properties();
        prop.setProperty("user", user);
        prop.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", prop);

        String query =
                "SELECT CONCAT(u.first_name, ' ',u.last_name) AS full_name, " +
                        "COUNT(ug.game_id) AS game_count " +
                        "FROM users AS u " +
                        "JOIN users_games AS ug ON u.id = ug.user_id " +
                        "WHERE u.user_name = ? " +
                        "GROUP BY u.user_name " +
                        "HAVING u.user_name IS NOT NULL";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        String username = scanner.nextLine();
        preparedStatement.setString(1, username);

        ResultSet rs = preparedStatement.executeQuery();

        if (!rs.next()) {
            System.out.println("No such user exists");
            return;
        }

        rs.beforeFirst();

        rs.next();
        System.out.printf("User: %s%n" +
                        "%s has played %d games", username,
                rs.getString("full_name"),
                rs.getInt("game_count"));


        connection.close();
    }
}
