package employeeaddress.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import employeeaddress.item.EmployeeAddressItem;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames="empId")})
public class EmployeeAddress {
    @Id @GeneratedValue
    private Long id;
    
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String state;
    private String country;
    private String empId;

    public EmployeeAddress() {}
    
    public EmployeeAddress(EmployeeAddressItem employeeAddressItem) {
        id = employeeAddressItem.getId();
        address1 = employeeAddressItem.getAddress1();
        address2 = employeeAddressItem.getAddress2();
        address3 = employeeAddressItem.getAddress3();
        address4 = employeeAddressItem.getAddress4();
        state = employeeAddressItem.getState();
        country = employeeAddressItem.getCountry();  
        empId = employeeAddressItem.getEmpId();
    }
    public EmployeeAddressItem asEmployeeAddressItem() {
        EmployeeAddressItem employeeAddressItem = new EmployeeAddressItem();
        employeeAddressItem.setId(id);
        employeeAddressItem.setAddress1(address1);
        employeeAddressItem.setAddress2(address2);
        employeeAddressItem.setAddress3(address3);
        employeeAddressItem.setAddress4(address4);
        employeeAddressItem.setState(state);
        employeeAddressItem.setCountry(country);
        employeeAddressItem.setEmpId(empId);
        return employeeAddressItem;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }   
}
