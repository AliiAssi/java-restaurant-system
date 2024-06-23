package Model.Strategy_Pattern_forSalaryCalculation;

// Context class that uses the strategy
public class EmployeeSalaryInfo {
    private SalaryCalculationStrategy salaryStrategy;

    public void setSalaryStrategy(SalaryCalculationStrategy strategy) {
        this.salaryStrategy = strategy;
    }

    /**
     * here in this class we take the strategy by parameter then we call its own calculation strategy method
     * */
    public int calculateTotalSalary(int baseSalary, int bonus) {
        if (salaryStrategy != null) {
            return salaryStrategy.calculateTotalSalary(baseSalary, bonus);
        } else {
            throw new IllegalStateException("Salary strategy not set.");
        }
    }
}
