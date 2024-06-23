package Model.ProductPackage;

import Controller.Generic_utils.EmpItemForDeletion;
import Controller.Generic_utils.ProductItemForDeletion;
import Model.DataBaseConnection;
import Model.ProductPackage.category.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/*
this generic class will take by T two types of categories ==> maybe Drink,maybe Food

**/

public class GenericBasedProduct<T extends ProductCategory> implements Product, ProductItemForDeletion {
    private String name;
    private int price;
    private T productCategory;
    Connection connection = DataBaseConnection.getInstance().getConnection();

    public GenericBasedProduct(String name,int price,T productCategory) throws SQLException {
        this.price = price;
        this.name= name;
        this.productCategory = productCategory;
    }


    public GenericBasedProduct() throws SQLException {

    }



    public String getName(){
        return name;
    }
    public int getPrice(){
        return price;
    }

    @Override
    public String getCategory() {
        return productCategory.getCategory();
    }

    @Override
    public int getComboPrice() {
        return 0;
    }

    @Override
    public String getComboType() {
        return "";
    }



    // return the query sql that delete a product
    @Override
    public String DeleteItem(int id) {
        return "DELETE FROM `product` WHERE id =" + id;
    }
    //inserting a product
    public boolean insertBasedProduct() throws SQLException {
        String name = this.getName();
        int price = this.getPrice();
        String category = this.getCategory();
        String sql = "INSERT INTO `product`(`name`, `price`, `category`, `comboPrice`)" +
                " VALUES (?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, price);
            pstmt.setString(3, category);
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}