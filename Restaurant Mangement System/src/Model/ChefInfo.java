package Model;

import Model.CommandDesignPatternForOrderActions.Chef;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChefInfo {
    static Connection connection;

    static {
        try {
            connection = DataBaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * the purpose of this is same purpose of the adminInfo
     * **/

    private Chef chef;
    private String salary ;
    private String bonus;
    public ChefInfo(Chef chef,String salary,String bonus) throws SQLException {
        this.chef = chef;
        this.salary = salary;
        this.bonus = bonus;
    }
    public Chef getChef(){
        return chef;
    }
    public String getSalary(){
        return  salary;
    }
    public String getBonus(){
        return bonus;
    }

    // here we are getting the chef INfORMATONS from the data base using its gmail and password
    public static  ChefInfo ChefInfo (Chef chef) throws SQLException {
        String sql = "SELECT employee.id, name, createdAt, salary, bonus, type FROM employee " +
                "JOIN salary ON employee.id = salary.employee_id " +
                "WHERE gmail = ? AND password = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, chef.getGmail());
            preparedStatement.setString(2, chef.getPassword());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int salary = resultSet.getInt("salary");
                    int bonus  = resultSet.getInt("bonus");
                    return new ChefInfo(chef,salary+"",bonus+"");
                } else {
                    System.out.println("error ");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
