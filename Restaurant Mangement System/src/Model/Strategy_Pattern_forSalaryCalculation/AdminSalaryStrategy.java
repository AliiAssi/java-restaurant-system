package Model.Strategy_Pattern_forSalaryCalculation;

public class AdminSalaryStrategy implements SalaryCalculationStrategy {
    @Override
    public int calculateTotalSalary(int baseSalary, int bonus) {
        // Implement the total salary calculation for admin
        return bonus + (2 * baseSalary);
    }
}