package lab;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class SoftUniApp {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "1234");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soft_uni", properties);
        String statementContent = "SELECT CONCAT(e.first_name, ' ',e.last_name) AS full_name FROM employees AS e WHERE e.salary > ?";
        PreparedStatement statement = connection.prepareStatement(statementContent);

        Scanner scanner = new Scanner(System.in);
        String margin = scanner.nextLine();
        statement.setDouble(1, Double.parseDouble(margin));

        ResultSet rs = statement.executeQuery();
        IterableResultSet iterableResultSet = new IterableResultSet(rs);
        for (String name : iterableResultSet) {
            System.out.println(name);
        }

        connection.close();
    }
}
