package Models;

public class FinanceEmployee extends Employee{ 

    private String allowanceReader;


public FinanceEmployee(String employeeId, String username, String firstName, 
                     String lastName, String birthday, String address,
                     String SSS, String PhilHealth, String TIN, String Pagibig,
                     String immediateSupervisor, String status, String position,
                     String userType, String password, String changePassword, int phone, String roleName,
                     String allowanceReader ) {

        //employee attributes inherited by Finance Employee
        super ( employeeId,  username,  firstName,  lastName,  birthday,  address, 
        SSS,  PhilHealth,  TIN,  Pagibig,  immediateSupervisor,  status,  position, 
        userType,  password,  changePassword, phone,  roleName); 

        this.allowanceReader = allowanceReader; }


          //log-in validation constructor
         public FinanceEmployee (String employeeId, String username, String roleName, String password, String firstName, String lastName, String changePassword) {
                super(employeeId, username, roleName, password, firstName, lastName, changePassword);

               }


public void accessLevel() {

                System.out.println("Accessing Finance Dashboard");
        }





}
