package Payroll.Deductions;

public class PhilHealthAdapter implements PhilHealthDeductions {
    @Override
    public double calculate(double basicSalary) {
        return Deductions.calculatePhilHealthDeduction(basicSalary);
    }
}