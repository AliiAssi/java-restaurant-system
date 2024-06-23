package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private Connection connection;
    private static DataBaseConnection instance;
    final private String DB_URL = "jdbc:mysql://localhost/restaurant management system?serverTimezone=UTC";
    final private String USERNAME = "root";
    final private String PASS = "";

    // Private constructor to prevent direct instantiation
    private DataBaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Failed to connect to the database.");
        }
    }

    public static DataBaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DataBaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
