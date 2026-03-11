package Payroll;

public class Salary {

    //attributes 
    
    private double basicSalary,hourlyRate,grossRate;
  
    //constructor
    public Salary(double basicSalary, double hourlyRate, double grossRate) {


        this.basicSalary = basicSalary;
        this.hourlyRate = hourlyRate;
        this.grossRate = grossRate;
    }


    //implementing getters
    public double getBasicSalary() {
        return basicSalary;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getGrossRate() {
        return grossRate;
    }

    
}
