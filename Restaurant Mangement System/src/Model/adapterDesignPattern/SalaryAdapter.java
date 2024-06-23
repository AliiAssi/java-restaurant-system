// Package declaration for the adapter design pattern in the Model package
package Model.adapterDesignPattern;

// Importing necessary classes from the Model package
import Model.ChefInfo;
import Model.CommandDesignPatternForOrderActions.Chef;

// Importing SQLException for handling database-related exceptions
import java.sql.SQLException;

// Implementation of the SalaryAdapter class that implements the SalaryConverter interface
public class SalaryAdapter implements SalaryConverter {
    // Private member variable to store the Chef object
    private Chef chef;

    // Constructor to initialize the SalaryAdapter with a Chef object
    public SalaryAdapter(Chef chef) {
        this.chef = chef;
    }

    // Implementation of the convertSalary method from the SalaryConverter interface
    @Override
    public double convertSalary(double originalSalary) {
        // Conversion logic: multiplying the original salary by 1500
        return originalSalary * 1500;
    }

    // Method to get the converted salary of the Chef
    public double getConvertedSalary() throws SQLException {
        // Retrieving ChefInfo object with information about the Chef, including salary
        ChefInfo chefInfo = ChefInfo.ChefInfo(chef);

        // Parsing the salary as a double from the ChefInfo object
        double originalSalary = Double.parseDouble(chefInfo.getSalary());

        // Returning the converted salary using the convertSalary method
        return convertSalary(originalSalary);
    }

    // Method to get the converted bonus of the Chef
    public double getConvertedBonus() throws SQLException {
        // Retrieving ChefInfo object with information about the Chef, including bonus
        ChefInfo chefInfo = ChefInfo.ChefInfo(chef);

        // Parsing the bonus as a double from the ChefInfo object
        double originalBonus = Double.parseDouble(chefInfo.getBonus());

        // Returning the converted bonus using the convertSalary method
        return convertSalary(originalBonus);
    }
}
