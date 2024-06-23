1) How to laungh:
----------------------

To initiate the system, please follow these comprehensive steps:

*XAMPP Installation:
Begin by installing XAMPP, a robust and widely-used open-source cross-platform web server solution stack.
we will use it when we will import the data base file in the phpMyAdmin dashboard

*IDE Installation (IntelliJ idea platform is the ideal one for my project):
Select and install a suitable Integrated Development Environment (IDE) such as IntelliJ or Eclipse. These professional-grade IDEs offer a feature-rich environment for Java development, providing essential tools for coding, debugging, and project management.

*SQL Database Setup:
Import the provided SQL file (sqlFile.sql) into your database management system. This file contains the necessary database schema and initial data required for the proper functioning of the application. Ensure that the database connection details are configured appropriately within your project.

*Main Class Execution:
Execute the main class of your application. This pivotal step initiates the execution of the core functionality. 
make sure that the mysql server start by the order of the xampp panel


By meticulously following these steps, you will successfully launch the application and be ready to delve into its functionalities within your chosen IDE.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
2)projects participant names and ID:
------------------------------------
Ali Assi (ID: ?):


Ali Diab (ID: ?):


Leen Mesmar (ID: ? - Dropped Out):
Unfortunately, Ms. Leen Mesmar, initially part of the project, has disengaged from university studies and is no longer actively participating in the project. 

3)brief description of each source code file content:
-----------------------------------------------------
-----------------------------------------------------
in the folder controller:
--------------------------
generic_utils_folderL
---------------------
This directory includes a pair class, utilizing a generic type for calculating chef salary information. Additionally, an interface with two classes is present, facilitating the generic execution of item deletion within the project.

auth controller:
----------------
A class is designed to receive the sign in view and user model as parameters. It verifies if the user is a chef, opening the chef frame, or if the user is an admin, the admin frame is displayed. Otherwise, it sets the text fields in front of the user to "false"

admin controller:
-----------------
class that take the admin frame view and the admin model for checking if a button have clicked to make some actions : (make sure please testing the combobox in the admin view)
it contains the actions performed in each panel{we have admin ,chef,product panels }: refresh table,update ,delete,getting data from DB to fetch it then display it in the table

chef controller:
-----------------
The ChefController class, taking the chef frame and logged-in model, oversees actions like deleting and updating orders. It also manages fetching and displaying dynamic chef information, incorporating a strategy for calculating price and total price upon "My Info" button clicks.
                                                           *******************************************
in the view folder:
--------------------------
Within the view folder, the project encompasses 3 primary views: sign-in, admin, and chef 
                                                           *******************************************
in the model folder :
------------------------
adapter dp folder :
Within this folder, an interface and an adapter class have been implemented. The interface serves as a blueprint, while the adapter class facilitates the conversion of chef salary information to another unit in the context of Lebanon.
                 -------------------------
command dp folder :
i create the chef class:
implementing the RestaurantEmployee interface and EmpItemForDeletion interface, representing a chef in a restaurant management system. It includes methods for changing order status, deleting orders, and retrieving salary and bonus information from the database. The class also manages chef details such as name, email, password, and working time, utilizing a database connection for SQL operations.
i create the command interface
i create the 2 classes cancel and change order command in order to invoke 2 types of command 
i create the invoker class(in the order to implement the right structure of the command dp)
you can check the main class in this folder to see how the command will work
                   ---------------------------
product package folder:
1)it contains a category folder : 	
i use an adater class MyAdapter.java:this one is like an adapter class that implemet the all productCategory methods,i use in the Food class
in the food class: i use the adapet for implement inly one method from the interface by subclassing 
in the drink class : i implement the methods of the productCategory methods 
2)it contains also the folder "forDataBase":The "ForDBProduct" class is crafted to streamline the incorporation of the product model into a database context, focusing on the essential inclusion of the required ID.
3)GenericBasedProduct Class implements the Product interface and ProductItemForDeletion interface, offering a generic approach to product representation with features such as name, price, and category. It facilitates database operations, including insertion and deletion, and incorporates a generic product category for flexible classification within the application.
4)The "ComboProductDecorator" class, located in the "ProductPackage" package, implements the ComboDecorator, ProductItemForDeletion, and Product interfaces. It serves as a decorator for a generic product, adding combo features such as type and price. This class seamlessly integrates with the database, enabling operations like insertion and deletion, and provides enhanced product details, including the augmented price and unique combo type.
it contains 2 more interfaces used in the context of implementing the product code
 		------------------
strategy_pattern_for salary calculation folder:
1)The "EmployeeSalaryInfo" class, within the "Strategy_Pattern_forSalaryCalculation" package, acts as a context utilizing the strategy pattern for dynamic salary calculation. It allows flexibility by setting a SalaryCalculationStrategy and calculates the total salary based on a provided base salary and bonus.
2)it contains the admin salary staretgy  class + the chef salary staretgy  class and you can can see the Main class that descript the startegy design pattern
                       ----------------
it contains also:
*The "Admin" class extending the "MyAdapter" class for the same purpose of the MyAdapter class in the product. It encapsulates functionality for managing administrators, chefs, and product information in a restaurant management system, handling database interactions for employee details, product retrieval, and product updates
*The "AdminInfo" class in the "Model" package encapsulates admin-related information, associating an Admin object with salary and bonus details. It facilitates organized representation and retrieval of admin data in a concise manner.(same for the chefInfo)
*data base connection class:i guess no need to talk about it
*The "DataBaseConnection" class in the "Model" package establishes and manages a connection to a MySQL database for a restaurant management system. It utilizes the singleton pattern, ensuring a single instance of the connection is maintained, and provides access to the established database connection.
*The "Order" class in the "Model" package represents an order in a restaurant management system, encapsulating details such as ID, products, price, username, status, address, and placement timestamp. It provides a structured entity for managing and retrieving order information within the application.
*The "UserModelFromDataBase" class in the "Model" package implements a singleton pattern to represent user data retrieved from a database in a restaurant management system. It encapsulates user details such as name, Gmail, password, and type, providing a singleton instance and a method for user authentication based on input credentials
*The "RestaurantEmployee" interface in the "Model" package outlines methods for managing restaurant employees, including getting and setting user credentials, managing working time, changing order status, and deleting orders. It serves as a contract for implementing these functionalities across employee types in a restaurant management system.


