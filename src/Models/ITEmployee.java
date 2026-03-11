package Models;

public class ITEmployee extends Employee {

private String systemAccess; 

ITEmployee (String employeeId, String username, String firstName, 
                     String lastName, String birthday, String address,
                     String SSS, String PhilHealth, String TIN, String Pagibig,
                     String immediateSupervisor, String status, String position,
                     String userType, String password, String changePassword, int phone, String roleName,
                     String hrDepartment, String accessLevel, String systemAccess) {

        super ( employeeId,  username,  firstName,  lastName,  birthday,  address, 
        SSS,  PhilHealth,  TIN,  Pagibig,  immediateSupervisor,  status,  position, 
        userType,  password,  changePassword, phone,  roleName); 

        this.systemAccess = systemAccess;  }


         //log-in validation constructor
         public ITEmployee (String employeeId, String username, String roleName, String password, String firstName, String lastName, String changePassword) {
                super(employeeId, username, roleName, password, firstName, lastName, changePassword);

               }




        public void accessLevel() {

                System.out.println("Accessing IT Dashboard");
        }



        public void manageSystem() {
        System.out.println("Accessing Systems"); 
        

        






}

    
}
