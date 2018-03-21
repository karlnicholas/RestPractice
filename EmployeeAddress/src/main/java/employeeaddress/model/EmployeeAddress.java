package employeeaddress.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import employeeaddress.item.EmployeeAddressItem;

@Entity
public class EmployeeAddress {
    @Id private Integer empId;
    
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String state;
    private String country;

    public EmployeeAddress() {}
    
    public EmployeeAddress(EmployeeAddressItem employeeAddressItem) {
        empId = employeeAddressItem.getEmpId();
        address1 = employeeAddressItem.getAddress1();
        address2 = employeeAddressItem.getAddress2();
        address3 = employeeAddressItem.getAddress3();
        address4 = employeeAddressItem.getAddress4();
        state = employeeAddressItem.getState();
        country = employeeAddressItem.getCountry();  
    }
    public EmployeeAddressItem asEmployeeAddressItem() {
        EmployeeAddressItem employeeAddressItem = new EmployeeAddressItem();
        employeeAddressItem.setEmpId(empId);
        employeeAddressItem.setAddress1(address1);
        employeeAddressItem.setAddress2(address2);
        employeeAddressItem.setAddress3(address3);
        employeeAddressItem.setAddress4(address4);
        employeeAddressItem.setState(state);
        employeeAddressItem.setCountry(country);
        return employeeAddressItem;
    }
    public Integer getEmpId() {
        return empId;
    }
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }   
    public String getAddress1() {
        return address1;
    }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    public String getAddress3() {
        return address3;
    }
    public void setAddress3(String address3) {
        this.address3 = address3;
    }
    public String getAddress4() {
        return address4;
    }
    public void setAddress4(String address4) {
        this.address4 = address4;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}
