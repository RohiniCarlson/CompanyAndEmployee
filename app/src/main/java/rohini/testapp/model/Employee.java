package rohini.testapp.model;

public class Employee {
    private long employeeId;
    private String firstName;
    private String lastName;
    private int age;
    private long companyId;

    public Employee(String firstNname, String lastName, int age, long companyId) {
        this.firstName = firstNname;
        this.lastName = lastName;
        this.age = age;
        this.companyId = companyId;
    }

    public Employee(long employeeId, String firstName, String lastName, int age, long companyId) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.companyId = companyId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public int getAge() {

        return age;
    }

    public long getCompanyId() {

        return companyId;
    }


    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
}
