package Payroll.Deductions;

public class SSSAdapter implements SSSDeductions {
    @Override
    public double calculate(double basicSalary) {
        return Deductions.calculateSSSDeduction(basicSalary);
    }
}
