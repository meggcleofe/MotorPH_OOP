package Payroll.Deductions;

public class DeductionService {
    private final PagibigDeductions pagibig;
    private final PhilHealthDeductions philHealth;
    private final SSSDeductions sss;
    private final WithHoldingTaxDeductions tax;
    
    // constructor using adapters
    public DeductionService() {
        this.pagibig = new PagibigAdapter();
        this.philHealth = new PhilHealthAdapter();
        this.sss = new SSSAdapter();
        this.tax = new TaxAdapter();
    }
    
 
    public DeductionService(PagibigDeductions pagibig, PhilHealthDeductions philHealth,
                           SSSDeductions sss, WithHoldingTaxDeductions tax) {
        this.pagibig = pagibig;
        this.philHealth = philHealth;
        this.sss = sss;
        this.tax = tax;
    }
    
    public double calculateTotalDeductions(double basicSalary, double netSalary) {
        return pagibig.calculate() +
               philHealth.calculate(basicSalary) +
               sss.calculate(basicSalary) +
               tax.calculate(netSalary);
    }
    
    //getters
    public double getPagibig() {
        return pagibig.calculate();
    }
    
    public double getPhilHealth(double basicSalary) {
        return philHealth.calculate(basicSalary);
    }
    
    public double getSSS(double basicSalary) {
        return sss.calculate(basicSalary);
    }
    
    public double getTax(double netSalary) {
        return tax.calculate(netSalary);
    }
}