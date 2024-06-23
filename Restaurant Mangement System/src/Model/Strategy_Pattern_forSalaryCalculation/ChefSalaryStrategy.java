package Model.Strategy_Pattern_forSalaryCalculation;

public class ChefSalaryStrategy implements SalaryCalculationStrategy {
    @Override
    public int calculateTotalSalary(int baseSalary, int bonus) {
        // Implement the total salary calculation for chef
        return bonus + (baseSalary);
    }
}