package Payroll;

public record Salaryinfo (
    double grossRate, double netSalary, double hourlyRate,
    double riceSubsidy, double phoneAllowance, double clothingAllowance, double totalAllowances,
    double pagibigDeduction, double philHealthDeduction, double sssDeduction, double withholdingTax, double totalDeductions
) {}

