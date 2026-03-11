package Payroll;

// csv reader must be implemented first 

import java.io.IOException;
import java.util.logging.Logger;

public class HourlyRateCalculator {
    private static final Logger LOGGER = Logger.getLogger(HourlyRateCalculator.class.getName());
    private static final int STANDARD_HOURS = 160;
    
    public double calculate(String employeeId, Salaryinfo salaryinfo) throws IOException {
        if (salaryinfo == null) {
            LOGGER.warning("Salary data not found for ID: " + employeeId);
            return 0.0;
        }
        
        double hourlyRate = salaryinfo.hourlyRate();  // Using record's accessor method
        if (hourlyRate <= 0.0) {
            // Note: Salaryinfo doesn't have getBasicSalary(), so you'd need to modify this
            hourlyRate = 0.0; // or calculate from somewhere else
            LOGGER.warning("Hourly rate missing. Calculated: " + hourlyRate);
        }
        return hourlyRate;
    }
}