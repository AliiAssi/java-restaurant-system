package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int _id;
    private String allProducts ;
    private int orderPrice;
    private String username ;
    private LocalDateTime placedOn;
    private String status ;
    private String address;
    public Order(){}
    public Order(int _id, String allProducts, int price, String username, String status, String address , LocalDateTime placedOn){
        this._id = _id;
        this.allProducts = allProducts;
        this.status = status;
        this.address = address;
        this.placedOn  = placedOn;
        this.username = username;
        this.orderPrice = price;
    }
    public int getId() {
        return _id;
    }

    public String getAllProducts() {
        return allProducts;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getPlacedOn() {
        return placedOn;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }


}
