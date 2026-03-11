package Payroll;

import java.io.IOException;
import java.time.LocalDateTime;

public interface NetSalaryCalculator {

    
    double calculateNetSalary(String employeeId, LocalDateTime startDate, LocalDateTime endDate) throws IOException;


    
}
