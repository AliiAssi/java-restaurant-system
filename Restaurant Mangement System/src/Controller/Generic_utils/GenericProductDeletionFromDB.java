package Controller.Generic_utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GenericProductDeletionFromDB <T extends ProductItemForDeletion>{
    T item;
    public boolean deleteFromDB(int id, Connection connection) {
        // Ensure you have a valid connection before proceeding
        if (connection == null) {
            return false;
        }

        // Assuming the "item" class has a method DeleteItem that returns the SQL query
        String sql = item.DeleteItem(id);

        try (PreparedStatement secondStatement = connection.prepareStatement(sql)) {
            // Execute the second delete query
            int secondRowsAffected = secondStatement.executeUpdate();

            // Check if the second deletion was successful
            if (secondRowsAffected > 0) {
                return true;
            }

            // Return false if the second deletion was not successful
            return false;
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace(); // You might want to log the exception or throw a custom exception
            return false;
        }
    }

    public GenericProductDeletionFromDB(T item){this.item =item;}

}
