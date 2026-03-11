package Models;

public class HREmployee extends Employee {

    //additional attributes for HR
    private String accessLevel; //Hr, IT, Finance, Employee
    

     public HREmployee(String employeeId, String username, String firstName, 
                     String lastName, String birthday, String address,
                     String SSS, String PhilHealth, String TIN, String Pagibig,
                     String immediateSupervisor, String status, String position,
                     String userType, String password, String changePassword, int phone, String roleName,
                      String accessLevel) {

        //employee attributes inherited by HR
        super ( employeeId,  username,  firstName,  lastName,  birthday,  address, 
        SSS,  PhilHealth,  TIN,  Pagibig,  immediateSupervisor,  status,  position, 
        userType,  password,  changePassword, phone,  roleName); 


              
               this.accessLevel = accessLevel;  }


             //log-in validation constructor
               public HREmployee (String employeeId, String username, String roleName, String password, String firstName, String lastName, String changePassword) {
                super(employeeId, username, roleName, password, firstName, lastName, changePassword);

               }


public void accessLevel() {

                System.out.println("Accessing HR Dashboard");
        }





    
    } 


    

    

    

    

    

