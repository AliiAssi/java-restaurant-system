package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModelFromDataBase {
    private String name;
    private String gmail;
    private String password;
    private String type;

    // Singleton instance
    private static UserModelFromDataBase instance;

    // Private constructor to prevent instantiation
    private UserModelFromDataBase() {}

    // Public method to get the singleton instance
    public static synchronized UserModelFromDataBase getInstance() {
        if (instance == null) {
            instance = new UserModelFromDataBase();
        }
        return instance;
    }

    public UserModelFromDataBase(String name, String gmail, String password, String type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public static String authenticateUser(String gmail, String password) throws SQLException {
        DataBaseConnection con = DataBaseConnection.getInstance();
        Connection connection = con.getConnection();

        String query = "SELECT * FROM employee WHERE gmail = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gmail);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next())
                {
                    String type = resultSet.getString("type");
                    return type;
                }
                else
                {
                    return "no user founded";
                }
            }
        }
    }

}
