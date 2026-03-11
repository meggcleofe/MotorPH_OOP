package Payroll.Deductions;

public class PagibigAdapter implements PagibigDeductions {
    
    @Override
    public double calculate() {
        return Deductions.calculatePagibigDeduction();
    }
}