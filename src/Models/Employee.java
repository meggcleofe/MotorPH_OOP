package Models;


public class Employee { 

//employee attributes using encapsulated eme 
private String employeeId, username, firstName, lastName, birthday, address, 
       SSS, PhilHealth, TIN, Pagibig, immediateSupervisor, status, position, 
       userType, password, changePassword;

private int phone;
private final String roleName; 


//constructor for employee details 

public Employee (String employeeId, String username, String firstName, String lastName, String birthday, String address, 
       String SSS, String PhilHealth, String TIN, String Pagibig, String immediateSupervisor, String status, String position, 
       String userType, String password, String changePassword, int phone, String roleName) 

       {

        this.employeeId = employeeId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.SSS = SSS;
        this.TIN = TIN;
        this.PhilHealth = PhilHealth;
        this.Pagibig = Pagibig;
        this.immediateSupervisor = immediateSupervisor;
        this.status = status;
        this.position = position;
        this.userType = userType;
        this.password = password;
        this.changePassword = changePassword;
        this.phone = phone;
        this.roleName = roleName;

       }

       //constructor for employee log-in validation 

       public Employee (String employeeId, String username, String roleName, String password, String firstName, String lastName, String changePassword)
        
       {

        this.employeeId = employeeId;
        this.username = username;
        this.roleName = roleName;
        this.password = password;
        this.firstName = firstName;  
        this.lastName = lastName;
        this.changePassword = changePassword; 

    } 

    //getters and setters

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
    return firstName + " " + lastName;  // Combines first and last name :D
}


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getSSS() {
        return SSS;
    }

    public void setSSS(String SSS) {
        this.SSS = SSS;
    }

    public String getPhilHealth() {
        return PhilHealth;
    }

    public void setPhilHealth(String PhilHealth) {
        this.PhilHealth = PhilHealth;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getPagibig() {
        return Pagibig;
    }

    public void setPagibig(String Pagibig) {
        this.Pagibig = Pagibig;
    }

    public String getImmediateSupervisor() {
        return immediateSupervisor;
    }

    public void setImmediateSupervisor(String immediateSupervisor) {
        this.immediateSupervisor = immediateSupervisor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserType() {
        return roleName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

     public String getChangePassword() {
        return changePassword;
    }

     public String setChangePassword() {
        return changePassword;
        
    }

    




















    
}
