package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQL {

    private static Connection connection;

    private static void createConnection() throws Exception {

        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/adyapana_db", "root", "Pasindu328@Bhathiya");
        }

    }

    public static ResultSet executeSearch(String query) throws Exception {

        createConnection();
        return connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);

    }

    public static Integer executeIUD(String query) throws Exception {
        createConnection();

        Statement statement = connection.createStatement();
        statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int generatedId = generatedKeys.getInt(1);
            return generatedId;
        }

        return null;
    }

}
