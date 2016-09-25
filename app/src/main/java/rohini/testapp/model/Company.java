package rohini.testapp.model;

public class Company {
    private long companyId;
    private String name;
    private String address;

    public Company(long companyId, String name, String address) {
        this.companyId = companyId;
        this.name = name;
        this.address = address;
    }

    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }


    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getCompanyId() {
        return companyId;
    }
}
