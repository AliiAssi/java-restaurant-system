package Controller.Generic_utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GenericEmpDeletionFromDB<T extends EmpItemForDeletion> {
    T item;
    public boolean deleteFromDB(int id, Connection connection) {
        // Ensure you have a valid connection before proceeding
        if (connection == null) {
            return false;
        }
        String sql = item.DeleteItem();
        System.out.println(sql);
        String secondSql = "DELETE FROM salary WHERE employee_id = ?";
        try (PreparedStatement secondStatement = connection.prepareStatement(secondSql)) {
            // Set parameter for the second delete query
            secondStatement.setInt(1, id);

            int secondRowsAffected = secondStatement.executeUpdate();
            if (secondRowsAffected > 0) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // Execute the first delete query
                    preparedStatement.setInt(1, id);
                    int rowsAffected = preparedStatement.executeUpdate();

                    // Check if the first deletion was successful
                    return rowsAffected > 0;
                }
            }
            // Return false if the second deletion was not successful
            return false;
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace(); // You might want to log the exception or throw a custom exception
            return false;
        }
    }


    public GenericEmpDeletionFromDB(T item){this.item =item;}

    public static void main(String[] args) throws SQLException {
        /*GenericDeletionFromDB<Chef> g = new GenericDeletionFromDB<Chef>(new Chef());
        System.out.println(g.deleteFromDB(5));
        GenericDeletionFromDB<GenericBasedProduct> g1 = new GenericDeletionFromDB<GenericBasedProduct>(new GenericBasedProduct("",0,new Drink(0)));
        System.out.println(g1.deleteFromDB(5));*/
    }
}
