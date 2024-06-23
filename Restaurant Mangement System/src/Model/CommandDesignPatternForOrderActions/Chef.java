package Model.CommandDesignPatternForOrderActions;

import Controller.Generic_utils.EmpItemForDeletion;
import Model.ChefInfo;
import Model.DataBaseConnection;
import Model.RestaurantEmployee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
// Implementation of the Chef class that represents a restaurant employee and implements EmpItemForDeletion interface
public class Chef implements RestaurantEmployee, EmpItemForDeletion {
    Connection connection = DataBaseConnection.getInstance().getConnection();

    private int _id;
    private String name;
    private String gmail;
    private String password;
    private String workingTime;
    private Date createdAt;
    public Date getCreatedAt(){
        return createdAt;
    }

    public Chef() throws SQLException {}
    public Chef(String gmail,String password) throws SQLException {
        this.gmail = gmail;
        this.password = password;
    }
    public Chef(int _id,String name,String gmail,String password,String workingTime,Date createdAt) throws SQLException {
        this._id = _id;
        this.name =  name;
        this.gmail = gmail;
        this.password = password;
        this.workingTime = "-am";
        this.createdAt = createdAt;
    }
    public Chef(String name,String gmail,String password,Date createdAt) throws SQLException {
        this.name =  name;
        this.gmail = gmail;
        this.password = password;
        this.workingTime = "-am";
        this.createdAt = createdAt;
    }
    public Chef(int id, String name,String gmail,Date createdAt) throws SQLException {
        this._id = id;this.name = name;this.gmail=gmail;this.createdAt=createdAt;
    }
    public String getCreatedAtAsString() {
        if (createdAt != null) {
            // Use a SimpleDateFormat or another preferred date formatting method
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(createdAt);
        } else {
            return "Date not available";
        }
    }
    public int getId(){
        return _id;
    }

    public String getGmail() {
        return gmail;
    }
    public void setGmail(String email) {
        this.gmail = email;
    }
    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getChefTime() {
        return "-am";
    }
    public void setChefTime(String workingTime) {
        this.workingTime = workingTime;
    }
    // Method to change the status of an order to "completed"
    public boolean changeOrderStatus(int id) throws SQLException {
        System.out.println("Chef changes the order status");

        String sql = "UPDATE orderr SET status = 'completed' WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the update was successful
            return rowsAffected > 0;
        }
    }
    // Method to delete the order by its id
    public boolean deleteOrder(int id) throws SQLException {
        System.out.println("Chef received the request to cancel the order");
        DataBaseConnection con = DataBaseConnection.getInstance();
        Connection connection = con.getConnection();

        String sql = "DELETE FROM orderr WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        }
    }

    @Override
    public String DeleteItem() {
        return "DELETE FROM `employee` WHERE id = ? ";
    }

    public int getSalary() throws SQLException {
        ChefInfo chefInfo = ChefInfo.ChefInfo(this);
        int salary = Integer.parseInt(chefInfo.getSalary());
        return salary;
    }
    public int getBonus()throws SQLException {
        ChefInfo chefInfo = ChefInfo.ChefInfo(this);
        int bonus = Integer.parseInt(chefInfo.getBonus());
        return bonus;
    }
}
