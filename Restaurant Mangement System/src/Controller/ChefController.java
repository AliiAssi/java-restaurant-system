package Controller;

import Controller.Generic_utils.Pair;
import Model.CommandDesignPatternForOrderActions.*;
import Model.DataBaseConnection;
import Model.Order;
import Model.Strategy_Pattern_forSalaryCalculation.ChefSalaryStrategy;
import Model.Strategy_Pattern_forSalaryCalculation.EmployeeSalaryInfo;
import Model.adapterDesignPattern.SalaryAdapter;
import View.ChefView.ChefView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ChefController {
    ChefView view ;
    Chef model;
    Connection connection = DataBaseConnection.getInstance().getConnection();

    public ChefController(ChefView view, Chef model) throws SQLException {
        this.view  = view;
        this.model = model;
        view.setVisible(true);
        // get all orders from the DB then display it in the table
        view.setChefsViewOrdersActionListenner(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Order> orders = getAllOrders();
                DefaultTableModel model = new DefaultTableModel(
                        new Object[][]{},
                        new String[]{"Order ID", "Customer Name", "Products", "Order Price", "Status", "Address", "Placed On"}
                );

                // Populate the DefaultTableModel with data
                for (Order order : orders) {
                    Object[] rowData = {
                            order.getId(),
                            order.getUsername(),
                            order.getAllProducts(),
                            order.getOrderPrice(),
                            order.getStatus(),
                            order.getAddress(),
                            order.getPlacedOn()
                    };
                    model.addRow(rowData);
                }
                view.setjTable1(model);
            }
        });
        //using the strategy dp we will calculate the total price of the chef
        //using the mix between the adapter dp and strategy dp we will calculate the  total price in the lebanon unit
        view.setSalaryButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pair<Integer> salaryInfo = null;
                Chef chef = returnedChef(model.getGmail(),model.getPassword());
                salaryInfo = setSalaryInfo(chef.getGmail(), chef.getPassword());
                ChefSalaryStrategy chefStrategy1 = new ChefSalaryStrategy();
                EmployeeSalaryInfo employeeInfo1 = new EmployeeSalaryInfo();
                employeeInfo1.setSalaryStrategy(chefStrategy1);

                SalaryAdapter adapter = new SalaryAdapter(chef);
                double chefSalaryfromAdapter,chefBonusfromAdapter;
                try {
                    chefSalaryfromAdapter = adapter.getConvertedSalary();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    chefBonusfromAdapter = adapter.getConvertedBonus();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                /** using of the strategy design pattern to calculate  the price of the chef in order of its calculation chef strategy **/
                System.out.println(chefSalaryfromAdapter);
                int chefTotalSalary1 = employeeInfo1.calculateTotalSalary((int)chefSalaryfromAdapter,(int)chefBonusfromAdapter);
                /***/


                view.setPriceInLebanonUnit(chefSalaryfromAdapter+"");

                view.setTotalPriceInLebanonUnit(chefTotalSalary1+"");
                view.setChefName(chef.getName());
                view.setJoinDate(chef.getCreatedAt()+"");
                ChefSalaryStrategy chefStrategy = new ChefSalaryStrategy();
                EmployeeSalaryInfo employeeInfo = new EmployeeSalaryInfo();
                employeeInfo.setSalaryStrategy(chefStrategy);

                int chefBaseSalary = salaryInfo.getFirst();
                view.setPrice(chefBaseSalary+"");
                int chefBonus = salaryInfo.getSecond();
                view.setBonus(chefBonus+"");
                int chefTotalSalary = employeeInfo.calculateTotalSalary(chefBaseSalary, chefBonus);
                view.setTotalPrice(chefTotalSalary+"");

            }
        });
        // update the order by its id then refreshing the table
        view.setAcceptOrderActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(view.getId().equals("")){
                    view.setError();return;
                }
                int id = Integer.parseInt(view.getId());
                Chef chef = model;
                Command orderCommand = new OrderCommand(chef,id);
                invokerClass invokerClass = new invokerClass();
                invokerClass.setCommand(orderCommand);
                try {
                    invokerClass.notifyChef(); // Chef changing the order status to completed
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                ArrayList<Order> orders = getAllOrders();
                DefaultTableModel model = new DefaultTableModel(
                        new Object[][]{},
                        new String[]{"Order ID", "Customer Name", "Products", "Order Price", "Status", "Address", "Placed On"}
                );

                // Populate the DefaultTableModel with data
                for (Order order : orders) {
                    Object[] rowData = {
                            order.getId(),
                            order.getUsername(),
                            order.getAllProducts(),
                            order.getOrderPrice(),
                            order.getStatus(),
                            order.getAddress(),
                            order.getPlacedOn()
                    };
                    model.addRow(rowData);
                }
                view.setjTable1(model);
                view.setMessage("order completed !");

            }
        });
        // delete the order by its id then refreshing the table
        view.setRemoveOrderActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(view.getId().equals("")){
                    view.setError();return;
                }
                int id = Integer.parseInt(view.getId());
                Chef chef = model;
                Command orderCommand = new CancelOrderCommand(chef,id);
                invokerClass invokerClass = new invokerClass();
                invokerClass.setCommand(orderCommand);
                try {
                    invokerClass.notifyChef(); // Chef changing the order status to completed
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                ArrayList<Order> orders = getAllOrders();
                DefaultTableModel model = new DefaultTableModel(
                        new Object[][]{},
                        new String[]{"Order ID", "Customer Name", "Products", "Order Price", "Status", "Address", "Placed On"}
                );

                // Populate the DefaultTableModel with data
                for (Order order : orders) {
                    Object[] rowData = {
                            order.getId(),
                            order.getUsername(),
                            order.getAllProducts(),
                            order.getOrderPrice(),
                            order.getStatus(),
                            order.getAddress(),
                            order.getPlacedOn()
                    };
                    model.addRow(rowData);
                }
                view.setjTable1(model);
                view.setMessage("deleted successfully");
            }
        });
    }
    //gettin all orders info
    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> result = new ArrayList<>();
        String sql = "SELECT orderr.id AS id, employee.name AS name, orderr.totalProducts AS products, " +
                "orderr.totalPrice AS orderPrice, status, adress, placedOn " +
                "FROM orderr " +
                "INNER JOIN employee ON orderr.userId = employee.id " +
                "WHERE employee.type = 'customer'";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String products = resultSet.getString("products");
                String orderPrice = resultSet.getString("orderPrice");
                String status = resultSet.getString("status");
                String address = resultSet.getString("adress");

                // Parse the date string using DateTimeFormatter
                String placedOnString = resultSet.getString("placedOn");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime placedOn = LocalDateTime.parse(placedOnString, formatter);

                // Create Order object and add it to the result list
                Order order = new Order(id, products, Integer.parseInt(orderPrice), name, status, address, placedOn);
                result.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    // getting informations about the chef logged in
    public Chef returnedChef(String gmail,String password){
        Chef chef = null;
        String sql = "SELECT * FROM employee " +
                "WHERE gmail = ? AND password = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, gmail);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int _id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Date createdAt = resultSet.getDate("createdAt");
                    chef = new Chef(_id, name, model.getGmail(), model.getPassword(), "-am", createdAt);
                } else {
                    System.out.println("error in setting model");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return chef;
    }
    // getting new Pair(salary ,bonus)  of the chef logged in
    public Pair<Integer> setSalaryInfo(String gmail, String password) {
        String sql = "SELECT employee.id, name, createdAt, salary, bonus, type FROM employee " +
                "JOIN salary ON employee.id = salary.employee_id " +
                "WHERE gmail = ? AND password = ?;";
        Pair<Integer> pair  = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, gmail);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int salary = resultSet.getInt("salary");
                    int bonus  = resultSet.getInt("bonus");
                    pair = new Pair<Integer>(salary,bonus);
                } else {
                    System.out.println("error in setting pair salary info generic");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pair;
    }
}
