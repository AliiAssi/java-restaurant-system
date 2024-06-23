package Model.Strategy_Pattern_forSalaryCalculation;

public class Main {
    public static void main(String[] args) {
        EmployeeSalaryInfo employeeInfo = new EmployeeSalaryInfo();

        // For admin
        AdminSalaryStrategy adminStrategy = new AdminSalaryStrategy();
        employeeInfo.setSalaryStrategy(adminStrategy);
        int adminBaseSalary = 100; // Replace with the actual base salary from the database
        int adminBonus = 30; // Replace with the actual bonus from the database
        int adminTotalSalary = employeeInfo.calculateTotalSalary(adminBaseSalary, adminBonus);

        // For chef
        ChefSalaryStrategy chefStrategy = new ChefSalaryStrategy();
        employeeInfo.setSalaryStrategy(chefStrategy);
        int chefBaseSalary = 120; // Replace with the actual base salary from the database
        int chefBonus = 25; // Replace with the actual bonus from the database
        int chefTotalSalary = employeeInfo.calculateTotalSalary(chefBaseSalary, chefBonus);
    }
}