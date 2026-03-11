package Payroll.Deductions;

public class TaxAdapter implements WithHoldingTaxDeductions {
    @Override
    public double calculate(double netSalary) {
        return Deductions.calculateWithholdingTax(netSalary);
    }
}