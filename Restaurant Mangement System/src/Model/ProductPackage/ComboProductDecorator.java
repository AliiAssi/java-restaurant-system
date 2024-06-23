package Model.ProductPackage;

import Controller.Generic_utils.EmpItemForDeletion;
import Controller.Generic_utils.ProductItemForDeletion;
import Model.DataBaseConnection;
import Model.ProductPackage.category.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * this is a decorator class {genericBasedProduct with some new attributes like the combo type and combo price}
 * */

public class ComboProductDecorator<T extends ProductCategory> implements ComboDecorator, ProductItemForDeletion,Product {
    Connection connection = DataBaseConnection.getInstance().getConnection();
    GenericBasedProduct<T> originalProduct;
    String comboType;
    int comboPrice;

    public ComboProductDecorator(GenericBasedProduct<T> genericProduct, String comboType, int comboPrice) throws SQLException {
        this.originalProduct = genericProduct;
        this.comboPrice = comboPrice;
        this.comboType = comboType;
    }


    @Override
    public String getComboType() {
        return comboType;
    }

    public int getComboPrice() {
        return comboPrice;
    }


    @Override
    public String getName() {
        return originalProduct.getName();
    }


    @Override
    public int getPrice() {
        return originalProduct.getPrice() + comboPrice;
    }

    @Override
    public String getCategory() {
        return originalProduct.getCategory();
    }

    // return the delete sql query
    @Override
    public String DeleteItem(int id) {
        return "DELETE FROM `product` WHERE id = " + id;
    }
    //inserting a product

    public boolean insertComboProduct() throws SQLException {
        String name = this.getName();
        int price = this.getPrice();
        String completedName = name + " with{" + this.comboType + "}";
        String category = this.getCategory();
        int comboPrice = this.comboPrice;
        String sql = "INSERT INTO `product`(`name`, `price`, `category`, `comboPrice`)" +
                " VALUES (?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, completedName);
            pstmt.setInt(2, price);
            pstmt.setString(3, category);
            pstmt.setInt(4, comboPrice);
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
