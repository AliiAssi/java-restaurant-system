package Controller;

import Controller.Generic_utils.GenericEmpDeletionFromDB;
import Controller.Generic_utils.EmpItemForDeletion;
import Controller.Generic_utils.GenericProductDeletionFromDB;
import Controller.Generic_utils.ProductItemForDeletion;
import Model.Admin;
import Model.AdminInfo;
import Model.ChefInfo;
import Model.CommandDesignPatternForOrderActions.Chef;
import Model.DataBaseConnection;
import Model.ProductPackage.ComboProductDecorator;
import Model.ProductPackage.GenericBasedProduct;
import Model.ProductPackage.Product;
import Model.ProductPackage.category.Drink;
import Model.ProductPackage.category.Food;
import Model.ProductPackage.forDataBase.ForDBProduct;
import Model.Strategy_Pattern_forSalaryCalculation.AdminSalaryStrategy;
import Model.Strategy_Pattern_forSalaryCalculation.ChefSalaryStrategy;
import Model.Strategy_Pattern_forSalaryCalculation.EmployeeSalaryInfo;
import View.AdminView.AdminView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.IntStream;
/*
here we will implement all admin actions
we will use the lambda function when we search for the product with a specified id in a list of builder products
the buidler product can be a based product or a combo product
* */
public class AdminController {
    public Connection connection= DataBaseConnection.getInstance().getConnection();


    Admin model;
    AdminView view;
    public AdminController(Admin admin, AdminView view) throws SQLException {
        this.model = admin;
        this.view = view;
        //TODO
        // getting the value selected  of the combo box  for getting the convenable data for and  display it
        view.setComboBox1ActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s1 = (String) view.getComboBox1().getSelectedItem();
                if(s1.equals("Admin")){
                    ArrayList<AdminInfo> adminInfos = admin.getAllAdmins();
                    DefaultTableModel model = null;
                    try {
                        model = new AdminController().getAdminDefault(adminInfos);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    view.setAdminsTable(model);

                }

                else if(s1.equals("Chef")){
                    ArrayList<ChefInfo> chefInfos = admin.getAllChefs();
                    DefaultTableModel model = null;
                    try {
                        model = new AdminController().getChefAll(chefInfos);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    view.setChefsTable(model);
                }

                else if(s1.equals("Product")){
                    ArrayList<ForDBProduct> products = model.getProducts();
                    DefaultTableModel model = null;
                    try {
                        model = new AdminController().productsModel(products);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    view.setProductsTable(model);
                }
            }
        });
        //TODO
        // getting only the admins for display it in the table
        view.refreshAdminTableButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<AdminInfo> adminInfos = admin.getAllAdmins();
                DefaultTableModel model = null;
                try {
                    model = new AdminController().getAdminDefault(adminInfos);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                view.setAdminsTable(model);
            }
        });
        //TODO
        // delete the admin action
        view.setDeleteAdminActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(view.idOfAdminToDelete().equals(""))
                {
                    view.idOfAdminMakeMessage("invalid id !");return;
                }
                String i = view.idOfAdminToDelete();
                int id = Integer.parseInt(i);
                AdminController a = null;
                try {
                    a = new AdminController();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    System.out.println("deleting an admin");
                    boolean g = a.deleteEmp(connection, id,new Admin());
                    if(g){
                        view.idOfAdminMakeMessage("deleted successfully");
                    }
                    return;

                } catch (SQLException ex) {
                    view.idOfAdminMakeMessage("invalid");
                    throw new RuntimeException(ex);
                }
            }
        });
        //TODO
        // update the admin with the id entered by the user
        view.setUpdateAdminButtonListenner(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Your input values
                String s2 = view.getTextFieldValue2(); // id
                String s7 = view.getTextFieldValue7(); // name
                String s8 = view.getTextFieldValue8(); // gmail
                String s9 = view.getTextFieldValue9(); // pass
                String s10 = view.getTextFieldValue10(); // salary|bonus

                // Split the salary and bonus
                String temp = "\\|";
                String[] salaryBonus = s10.split(temp);

                // Check if the split resulted in both salary and bonus
                if (salaryBonus.length < 2) {
                    view.setjTextField10("false");
                    return;
                }

                // Check if any of the values are empty
                if (s10.equals("") || salaryBonus[0].equals("") || salaryBonus[1].equals("")) {
                    view.setjTextField10("false");
                    return;
                }

                // Update the salary and bonus in the 'salary' table
                String updateSalarySql = "UPDATE `salary` SET `salary`= ?, `bonus`= ? WHERE employee_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateSalarySql)) {

                    // Set parameters for the salary update
                    preparedStatement.setString(1, salaryBonus[0]); // salary
                    preparedStatement.setString(2, salaryBonus[1]); // bonus
                    preparedStatement.setString(3, s2); // employee_id

                    // Execute the update
                    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println("Rows affected in salary update: " + rowsAffected);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                // Update the employee details in the 'employee' table
                String updateEmployeeSql = "UPDATE `employee` SET `name`= ?, `gmail`= ?, `password`=? WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateEmployeeSql)) {

                    // Set parameters for the employee update
                    preparedStatement.setString(1, s7); // name
                    preparedStatement.setString(2, s8); // gmail
                    preparedStatement.setString(3, s9); // password
                    preparedStatement.setString(4, s2); // id

                    // Execute the update
                    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println("Rows affected in employee update: " + rowsAffected);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        //TODO
        // update the product with the id entered by the user
        view.setUpPRActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Storing the values of the text fields in variables
                String name = view.getTextFieldValue15();
                String price = view.getTextFieldValue16();
                String comboType = view.getTextFieldValue17();
                String comboPrice= view.getTextFieldValue18();
                String id = view.getTextFieldValue6();
                String category = view.getComboBox2();
                String type = view.getLast();
                if (!comboType.equals("")   ) {
                    try {
                        if (category.contains("Drink")) {
                            int drinkType = type.equals("cold") ? 0 : 1;
                            ComboProductDecorator<Drink> comboProductDecorator = new ComboProductDecorator<>(
                                    new GenericBasedProduct<>(name, Integer.parseInt(price), new Drink(drinkType)),
                                    comboType, Integer.parseInt(comboPrice));
                            ForDBProduct<ComboProductDecorator> forDBProduct =
                                    new ForDBProduct<ComboProductDecorator>(Integer.parseInt(id),comboProductDecorator);
                            model.updateProduct(forDBProduct);
                            System.out.println("combo , drink");
                            view.setActionPanel();
                            return;
                        }

                        ComboProductDecorator<Food> comboProductDecorator = new ComboProductDecorator<>(
                                new GenericBasedProduct<>(name, Integer.parseInt(price), new Food(category)),
                                comboType, Integer.parseInt(comboPrice));
                        ForDBProduct<ComboProductDecorator> forDBProduct =
                                new ForDBProduct<ComboProductDecorator>(Integer.parseInt(id),comboProductDecorator);
                        model.updateProduct(forDBProduct);
                        System.out.println("combo , food");

                        view.setActionPanel();

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    try {
                        if (category.contains("Drink")) {
                            int drinkType = type.equals("cold") ? 0 : 1;
                            GenericBasedProduct<Drink> drinkGenericBasedProduct = new GenericBasedProduct<>(name,
                                    Integer.parseInt(price),new Drink(drinkType));
                            ForDBProduct<GenericBasedProduct> forDBProduct =
                                    new ForDBProduct<GenericBasedProduct>(Integer.parseInt(id),drinkGenericBasedProduct);
                            model.updateProduct(forDBProduct);
                            System.out.println("based , drink");
                            view.setActionPanel();
                            return;
                        }

                        GenericBasedProduct<Food> food = new GenericBasedProduct<>(name,
                                Integer.parseInt(price),new Food(category));
                        ForDBProduct<GenericBasedProduct> forDBProduct =
                                new ForDBProduct<GenericBasedProduct>(Integer.parseInt(id),food);
                        view.setActionPanel();
                        model.updateProduct(forDBProduct);
                        System.out.println("based , food");
                        view.setActionPanel();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        //TODO
        // search the employee with the id entered by the user
        view.setSearchButtonActionListenner(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id  = view.getTextFieldValue2();
                if(id.equals("")) return;
                String sql = "SELECT employee.id, employee.name, employee.gmail, salary.salary, salary.bonus, employee.password " +
                        "FROM employee " +
                        "JOIN salary ON employee.id = salary.employee_id " +
                        "WHERE employee.id = ?";


                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                    // Set the parameter for the prepared statement
                    preparedStatement.setString(1, id);

                    // Execute the query
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            // Retrieve data from the result set
                            int _id = resultSet.getInt("id");
                            String name = resultSet.getString("name");
                            String gmail = resultSet.getString("gmail");
                            String password = resultSet.getString("password");
                            int salary = resultSet.getInt("salary");
                            int bonus = resultSet.getInt("bonus");
                            view.setjTextField2(_id+"");
                            view.setjTextField7(name+"");
                            view.setjTextField8(gmail+"");
                            view.setjTextField9(password+"");
                            view.setjTextField10(salary+"|"+bonus);
                        }
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        //TODO
        // add employee to the data base
        view.setAddBtnListenner(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user inputs
                String name = view.getNameForAddedAdmin();
                String pass1 = view.getPass1();
                String salaryBonus = view.getsalaryBonus();
                String gmail = view.getGmail();
                String password = view.getPassword();
                int length = pass1.length();
                if (length > 0) {
                    char lastChar = pass1.charAt(length - 1);
                    if (lastChar == '|') {
                        view.setSalaryBonus();
                        return;
                    }
                }

                // Check if salaryBonus contains at least two elements separated by '|'
                if (salaryBonus.split("\\|").length < 2) {
                    view.setSalaryBonus();
                    return;
                }

                // Check if the first or second element after splitting salaryBonus is an empty string
                if (salaryBonus.split("\\|")[0].equals("") || salaryBonus.split("\\|")[1].equals("")) {
                    view.setSalaryBonus();
                    return;
                }

                // Splitting salaryBonus using "\\|"
                String[] temp = salaryBonus.split("\\|");
                char lastCharacterInGmail = gmail.charAt(gmail.length() - 1);
                if(lastCharacterInGmail == '|'){
                    if (model.InsertEmployee("admin",name,gmail.substring(0,gmail.length()-1),password,temp[0],temp[1])){
                        view.getPanelAdd().setVisible(false);
                    }
                }
                else {
                    if (model.InsertEmployee("chef",name,gmail,password,temp[0],temp[1])){
                        view.getPanelAdd().setVisible(false);
                    }
                }



            }
        });
        //TODO
        // refresh the chefs table
        view.setRefreshChef(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<ChefInfo> chefInfos = admin.getAllChefs();
                DefaultTableModel model = null;
                try {
                    model = new AdminController().getChefAll(chefInfos);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                view.setChefsTable(model);
            }
        });
        //TODO
        // delete the chef that have the id entered by the user
        view.setDeleteChefBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(view.getTextFieldValue3().equals(""))
                {
                    view.chefMessageId("invalid id !");return;
                }
                String i = view.getTextFieldValue3();
                int id = Integer.parseInt(i);
                AdminController a = null;
                try {
                    a = new AdminController();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    boolean g = a.deleteEmp(connection, id,new Chef());
                    if(g){
                        view.chefMessageId("deleted successfully");
                    }
                    return;

                } catch (SQLException ex) {
                    view.idOfAdminMakeMessage("invalid");
                    throw new RuntimeException(ex);
                }
            }
        });
        //TODO
        // search chef by its id entered by user
        view.setSearchChef(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = view.getTextFieldValue4();
                if(id.equals("")) return;
                String sql = "SELECT employee.id, employee.name, employee.gmail, salary.salary, salary.bonus, employee.password " +
                        "FROM employee " +
                        "JOIN salary ON employee.id = salary.employee_id " +
                        "WHERE employee.id = ?";


                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                    // Set the parameter for the prepared statement
                    preparedStatement.setString(1, id);

                    // Execute the query
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            // Retrieve data from the result set
                            int _id = resultSet.getInt("id");
                            String name = resultSet.getString("name");
                            String gmail = resultSet.getString("gmail");
                            String password = resultSet.getString("password");
                            int salary = resultSet.getInt("salary");
                            int bonus = resultSet.getInt("bonus");
                            view.setChefId(_id+"");
                            view.setChefName(name+"");
                            view.setChefGmail(gmail+"");
                            view.setChefPass(password+"");
                            view.setChefSB(salary+"|"+bonus);
                        }
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        //TODO
        // update the chef using the id entered by the user
        view.setUpdateChefBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s11, s12, s13, s14;
                s11 = view.getTextFieldValue11();//name
                s12 = view.getTextFieldValue12();//email
                s13 = view.getTextFieldValue13();//pass
                s14 = view.getTextFieldValue14();//salary bonus
                String s4 = view.getTextFieldValue4();//id
                // Split the salary and bonus
                String[] salaryBonus = s14.split("\\|");
                // Check if the split resulted in both salary and bonus
                if (salaryBonus.length < 2) {
                    view.setChefSB("false");
                    return;
                }

                // Check if any of the values are empty
                if (s14.equals("") || salaryBonus[0].equals("") || salaryBonus[1].equals("")) {
                    view.setChefSB("false");
                    return;
                }
                // Update the salary and bonus in the 'salary' table
                String updateSalarySql = "UPDATE `salary` SET `salary`= ?, `bonus`= ? WHERE employee_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateSalarySql)) {

                    // Set parameters for the salary update
                    preparedStatement.setString(1, salaryBonus[0]); // salary
                    preparedStatement.setString(2, salaryBonus[1]); // bonus
                    preparedStatement.setString(3, s4); // employee_id

                    // Execute the update
                    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println("Rows affected in salary update: " + rowsAffected);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                // Update the employee details in the 'employee' table
                String updateEmployeeSql = "UPDATE `employee` SET `name`= ?, `gmail`= ?, `password`=? WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateEmployeeSql)) {

                    // Set parameters for the employee update
                    preparedStatement.setString(1, s11); // name
                    preparedStatement.setString(2, s12); // gmail
                    preparedStatement.setString(3, s13); // password
                    preparedStatement.setString(4, s4); // id

                    // Execute the update
                    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println("Rows affected in employee update: " + rowsAffected);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        //TODO
        // delete the product that have the id entered by the user
        view.setDeletePrBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(view.getPrIdForDelete().equals("")){return;} // if empty
                GenericProductDeletionFromDB genericProductDeletionFromDB = null;
                try {
                    genericProductDeletionFromDB = new GenericProductDeletionFromDB<>(new GenericBasedProduct<>());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                genericProductDeletionFromDB.deleteFromDB(Integer.parseInt(view.getPrIdForDelete()),connection);
            }
        });
        //TODO
        // setting the add product action
        view.setAddProductBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = view.getNamePr();
                String price = view.getPricePr();
                String category = view.getCatPr();
                String type = view.getTypePr();
                String comboPrice = view.getCT();
                String comboType =  view.getCP();

                if (!comboType.equals("")) {
                    try {
                        if (category.equals("Drink")) {
                            int drinkType = type.equals("cold") ? 0 : 1;
                            ComboProductDecorator<Drink> comboProductDecorator = new ComboProductDecorator<>(
                                    new GenericBasedProduct<>(name, Integer.parseInt(price), new Drink(drinkType)),
                                    comboType, Integer.parseInt(comboPrice));
                            comboProductDecorator.insertComboProduct();
                            view.setActionPanel();
                            return;
                        }

                        ComboProductDecorator<Food> comboProductDecorator = new ComboProductDecorator<>(
                                new GenericBasedProduct<>(name, Integer.parseInt(price), new Food(category)),
                                comboType, Integer.parseInt(comboPrice));
                        comboProductDecorator.insertComboProduct();
                        view.setActionPanel();
                        return;
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    try {
                        if (category.equals("Drink")) {
                            int drinkType = type.equals("cold") ? 0 : 1;
                            GenericBasedProduct<Drink> drinkGenericBasedProduct = new GenericBasedProduct<>(name,
                                    Integer.parseInt(price),new Drink(drinkType));
                            drinkGenericBasedProduct.insertBasedProduct();
                            view.setActionPanel();
                            return;
                        }

                        GenericBasedProduct<Food> drinkGenericBasedProduct = new GenericBasedProduct<>(name,
                                Integer.parseInt(price),new Food(category));
                        drinkGenericBasedProduct.insertBasedProduct();
                        view.setActionPanel();
                        return;
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        //TODO
        // search the product that maybe have the id entered by the user
        view.setSearchPrBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = view.getTextFieldValue6();
                if(id.equals("")) {view.setErrorId();return;}
                ArrayList<ForDBProduct> products = model.getProducts();

                // Using Java Streams to find the index of the product with matching id
                int position = IntStream.range(0, products.size())
                        .filter(i -> products.get(i).getId() == (Integer.parseInt(id)))
                        .findFirst()
                        .orElse(-1);

                if (position != -1) {
                    // Product found, and 'position' now holds its index in the ArrayList
                    ForDBProduct<Product> forDBProduct = products.get(position);
                    if(forDBProduct.getInstance().getComboPrice() == 0){
                        System.out.println("without combo");
                        view.setjTextField15(forDBProduct.getInstance().getName());
                        view.setjTextField16(forDBProduct.getInstance().getPrice()+"");
                    }
                    else {
                        view.setjTextField15(forDBProduct.getInstance().getName());
                        view.setjTextField16(forDBProduct.getInstance().getPrice()+"");
                        view.setjTextField17(forDBProduct.getInstance().getComboType());
                        view.setjTextField18(forDBProduct.getInstance().getComboPrice()+"");
                    }
                } else {
                    view.setErrorId();
                    System.out.println("Product with id " + id + " not found");
                }

            }
        });
        //TODO
        // refresh the products table
        view.setRefreshProducts(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<ForDBProduct> products = model.getProducts();
                DefaultTableModel model = null;
                try {
                    model = new AdminController().productsModel(products);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                view.setProductsTable(model);
            }
        });
    }

    // Method to generate a DefaultTableModel for displaying AdminInfo data
    public DefaultTableModel getAdminDefault(ArrayList<AdminInfo> adminInfos) {
        // Create a DefaultTableModel with column names
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name", "gmail", "salary", "bonus", "total salary", "join date"}
        );

        // Iterate through the AdminInfo objects in the ArrayList
        for (AdminInfo data : adminInfos) {
            // Create an instance of EmployeeSalaryInfo for salary calculations
            EmployeeSalaryInfo employeeInfo = new EmployeeSalaryInfo();

            // Using the strategy pattern for salary calculation based on the admin role
            AdminSalaryStrategy adminStrategy = new AdminSalaryStrategy();
            employeeInfo.setSalaryStrategy(adminStrategy);

            // Extract salary and bonus information from AdminInfo
            int adminBaseSalary = Integer.parseInt(data.getSalary());
            int adminBonus = Integer.parseInt(data.getBonus());

            // Calculate the total salary using the specified strategy
            int adminTotalSalary = employeeInfo.calculateTotalSalary(adminBaseSalary, adminBonus);

            // Create an Object array with AdminInfo data for each row in the table
            Object[] rowData = {
                    data.getAdmin().getId(),
                    data.getAdmin().getName(),
                    data.getAdmin().getGmail(),
                    data.getSalary(),
                    data.getBonus(),
                    adminTotalSalary,
                    data.getAdmin().getCreatedAtAsString()
            };

            // Add the row data to the DefaultTableModel
            model.addRow(rowData);
        }

        // Return the populated DefaultTableModel
        return model;
    }
    // Method to generate a DefaultTableModel for displaying chef data model
    public DefaultTableModel getChefAll(ArrayList<ChefInfo> adminInfos){
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name", "gmail", "salary", "bonus","total salary","join date"}
        );

        // Populate the DefaultTableModel with data
        for (ChefInfo data : adminInfos) {
            EmployeeSalaryInfo employeeInfo = new EmployeeSalaryInfo();
            // using the strategy pattern
            ChefSalaryStrategy adminStrategy = new ChefSalaryStrategy();
            employeeInfo.setSalaryStrategy(adminStrategy);
            int adminBaseSalary = Integer.parseInt(data.getSalary());
            int adminBonus = Integer.parseInt(data.getBonus());
            int adminTotalSalary = employeeInfo.calculateTotalSalary(adminBaseSalary, adminBonus);
            Object[] rowData = {
                    data.getChef().getId(),
                    data.getChef().getName(),
                    data.getChef().getGmail(),
                    data.getSalary(),
                    data.getBonus(),
                    adminTotalSalary,
                    data.getChef().getCreatedAtAsString()
            };
            model.addRow(rowData);
        }
        return model;
    }
    // Method to generate a DefaultTableModel for displaying products data
    public DefaultTableModel productsModel(ArrayList<ForDBProduct> forDBProducts){
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name", "price", "category", "combo price"}
        );

        // Populate the DefaultTableModel with data
        for (ForDBProduct data : forDBProducts) {
            Object[] rowData = {
                    data.getId(),
                    data.getInstance().getName(),
                    data.getInstance().getPrice(),
                    data.getInstance().getCategory(),
                    data.getInstance().getComboPrice()
            };
            model.addRow(rowData);
        }
        return model;
    }
    public AdminController() throws SQLException {}



    // Method to delete an employee record from the database using a generic approach
    // The generic type T extends EmpItemForDeletion, ensuring the parameter item is of a valid type
    public <T extends EmpItemForDeletion> boolean deleteEmp(Connection connection, int id, T item) {
        // Assuming GenericEmpDeletionFromDB has a method that performs the deletion
        // Create an instance of GenericEmpDeletionFromDB with the specified item
        GenericEmpDeletionFromDB deletionFromDB = new GenericEmpDeletionFromDB(item);

        // Perform the deletion using the GenericEmpDeletionFromDB instance
        // The deleteFromDB method takes the employee ID and database connection as parameters
        boolean deletionResult = deletionFromDB.deleteFromDB(id, connection);

        // Return the result of the deletion operation (true if successful, false otherwise)
        return deletionResult;
    }



    // Method to delete a product record from the database using a generic approach
    // The generic type T extends ProductItemForDeletion, ensuring the parameter item is of a valid type
    public <T extends ProductItemForDeletion> boolean deleteProduct(Connection connection, int id, T item) {
        // Assuming GenericProductDeletionFromDB has a method that performs the deletion
        // Create an instance of GenericProductDeletionFromDB with the specified item
        GenericProductDeletionFromDB deletionFromDB = new GenericProductDeletionFromDB(item);

        // Perform the deletion using the GenericProductDeletionFromDB instance
        // The deleteFromDB method takes the product ID and database connection as parameters
        boolean deletionResult = deletionFromDB.deleteFromDB(id, connection);

        // Return the result of the deletion operation (true if successful, false otherwise)
        return deletionResult;
    }


    // Method to insert a new admin into the database
    // Parameters include admin type, name, Gmail, password, salary, and bonus
    public boolean insertAdmin(String type, String name, String gmail, String password, String salary, String bonus) {
        // Delegate the insertion operation to the model (assuming model is an instance of a class
        // that handles database operations, and InsertEmployee method performs the insertion)
        // The method returns true if the insertion is successful, false otherwise
        return model.InsertEmployee(type, name, gmail, password, salary, bonus);
    }



    public AdminController (Admin admin) throws SQLException {
        this.model = admin;
    }

}
