package Model;

import Model.CommandDesignPatternForOrderActions.Chef;
import Model.ProductPackage.ComboProductDecorator;
import Model.ProductPackage.GenericBasedProduct;
import Model.ProductPackage.Product;
import Model.ProductPackage.category.Drink;
import Model.ProductPackage.category.Food;
import Model.ProductPackage.forDataBase.ForDBProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Admin extends MyAdapter{
    Connection connection = DataBaseConnection.getInstance().getConnection();
    private int _id;
    private String name;
    private String gmail;
    private String password;
    private Date createdAt;
    public Date getCreatedAt() {
        return createdAt;
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
    public Admin(int _id,String name,String gmail,String password) throws SQLException {
        this._id = _id;
        this.name =  name;
        this.gmail = gmail;
        this.password = password;
    }
    public Admin(int _id,String name,String gmail,Date createdAt) throws SQLException {
        this._id = _id;
        this.name =  name;
        this.gmail = gmail;
        this.createdAt = createdAt;
    }
    public Admin() throws SQLException {}
    public String getGmail() {
        return gmail;
    }
    public void setGmail(String email) {
        this.gmail = gmail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    // return the sql query for deleting an admin //same for the chef
    public String DeleteItem(){
        return "DELETE FROM `employee` WHERE id = ?";
    }

    public boolean InsertEmployee(String type, String name, String gmail, String password, String salary, String bonus) {
        // Insert employee information
        String insertEmployeeSQL = "INSERT INTO `employee`(`name`, `gmail`, `password`, `type`) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertEmployeeSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name);
            pstmt.setString(2, gmail);
            pstmt.setString(3, password);
            pstmt.setString(4, type);
            pstmt.executeUpdate();

            // Retrieve the ID of the inserted employee
            int employeeId = -1;
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employeeId = generatedKeys.getInt(1);
                }
            }

            // Insert salary information
            String insertSalarySQL = "INSERT INTO `salary`(`employee_id`, `salary`, `bonus`) VALUES (?, ?, ?)";

            try (PreparedStatement pstmtSalary = connection.prepareStatement(insertSalarySQL)) {
                pstmtSalary.setInt(1, employeeId);
                pstmtSalary.setInt(2, Integer.parseInt(salary));
                pstmtSalary.setInt(3, Integer.parseInt(bonus));
                pstmtSalary.executeUpdate();

                // Return true if both employee and salary information are inserted successfully
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<AdminInfo> getAllAdmins() {
        ArrayList<AdminInfo> admins = new ArrayList<>();

        String sql = "SELECT employee.id, employee.name, employee.gmail, salary.salary, salary.bonus, employee.createdAt " +
                "FROM employee, salary " +
                "WHERE employee.id = salary.employee_id " +
                "AND employee.type = 'admin'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String gmail = resultSet.getString("gmail");
                    String salary = resultSet.getString("salary");
                    String bonus = resultSet.getString("bonus");
                    java.util.Date createdAt = resultSet.getDate("createdAt");

                    // Create AdminInfo object and add it to the list
                    admins.add(new AdminInfo(new Admin(id, name, gmail,createdAt), salary, bonus));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately (log or throw a custom exception)
        }

        return admins;
    }

    public ArrayList<ChefInfo> getAllChefs() {
        ArrayList<ChefInfo> chefs = new ArrayList<>();

        String sql = "SELECT employee.id, employee.name, employee.gmail, salary.salary, salary.bonus, employee.createdAt " +
                "FROM employee, salary " +
                "WHERE employee.id = salary.employee_id " +
                "AND employee.type = 'chef'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String gmail = resultSet.getString("gmail");
                    String salary = resultSet.getString("salary");
                    String bonus = resultSet.getString("bonus");
                    java.util.Date createdAt = resultSet.getDate("createdAt");

                    // Create AdminInfo object and add it to the list
                    chefs.add(new ChefInfo(new Chef(id, name, gmail,createdAt), salary, bonus));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately (log or throw a custom exception)
        }

        return chefs;
    }
    public ArrayList<ForDBProduct> getProducts(){
        ArrayList<ForDBProduct> products = new ArrayList<>();
        String sql = "SELECT * FROM product ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int price = resultSet.getInt("price");
                    String category = resultSet.getString("category");
                    int  comboPrice = resultSet.getInt("comboPrice");

                    if(comboPrice == 0){
                        if(category.contains(new Drink().getCategoryName())){ // contains("Drink")
                            if(category.contains("cold")){
                                products.add(new ForDBProduct(id,new GenericBasedProduct<Drink>(name,price, new Drink(0))));
                            }
                            else {
                                products.add(new ForDBProduct(id,new GenericBasedProduct<Drink>(name,price, new Drink(1))));
                            }
                        }
                        else {
                            products.add(new ForDBProduct(id,new GenericBasedProduct<Food>(name,price, new Food(category))));
                        }
                    }
                    else{
                        int indexofLeft = name.indexOf("{");
                        int indexOfRight= name.indexOf("}");
                        String type     = name.substring(indexofLeft+1,indexOfRight);
                        if(category.contains(new Drink().getCategoryName())){ // contains("Drink")
                            if(category.contains("cold")){
                                products.add(new ForDBProduct(id,new ComboProductDecorator<>(new GenericBasedProduct<Drink>(name,price,new Drink(0)),type,comboPrice)));
                            }
                            else {
                                products.add(new ForDBProduct(id,new ComboProductDecorator<>(new GenericBasedProduct<Drink>(name,price,new Drink(1)),type,comboPrice)));
                            }
                        }
                        else {
                            products.add(new ForDBProduct(id,new ComboProductDecorator<>(new GenericBasedProduct<Food>(name,price,new Food(category)),type,comboPrice)));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately (log or throw a custom exception)
        }
        return products;
    }

    //update a product : based or combo
    public boolean updateProduct(ForDBProduct<? extends Product> forDBProduct){
            String sql = "UPDATE product SET name = ? ,price = ?,category=?,comboPrice = ? WHERE id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the salary update
            preparedStatement.setInt(5, forDBProduct.getId()); // id
            preparedStatement.setString(1, forDBProduct.getInstance().getName()); // name
            preparedStatement.setInt(2, forDBProduct.getInstance().getPrice()); // price
            preparedStatement.setString(3, forDBProduct.getInstance().getCategory()); // cat
            preparedStatement.setInt(4, forDBProduct.getInstance().getComboPrice()); // combo price

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected in update: " + rowsAffected);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
            return true;
    }


}
