package Model.ProductPackage.forDataBase;

import Model.DataBaseConnection;
import Model.ProductPackage.Product;

import java.sql.Connection;
import java.sql.SQLException;

public class ForDBProduct<T extends Product> implements ProductDB{
    Connection connection = DataBaseConnection.getInstance().getConnection();
    int id;
    T product;
    public ForDBProduct(int id, T product) throws SQLException {
        this.id = id;
        this.product = product;
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public T getInstance() {
        return product;
    }

    public static void main(String[] args) throws SQLException {
    }



}
