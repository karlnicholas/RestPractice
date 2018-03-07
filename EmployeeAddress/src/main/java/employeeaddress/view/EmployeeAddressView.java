package employeeaddress.view;

import employeeaddress.model.EmployeeAddress;

public class EmployeeAddressView {
    private final Long id;
    
    private final String address1;
    private final String address2;
    private final String address3;
    private final String address4;
    private final String state;
    private final String country;
    
    public EmployeeAddressView(EmployeeAddress employeeAddress) {
        id = employeeAddress.getId();
        address1 = employeeAddress.getAddress1();
        address2 = employeeAddress.getAddress2();
        address3 = employeeAddress.getAddress3();
        address4 = employeeAddress.getAddress4();
        state = employeeAddress.getState();
        country = employeeAddress.getCountry();        
    }
    public EmployeeAddress asEmployeeAddress() {
        EmployeeAddress employeeAddress = new EmployeeAddress();
        employeeAddress.setId(id);
        employeeAddress.setAddress1(address1);
        employeeAddress.setAddress2(address2);
        employeeAddress.setAddress3(address3);
        employeeAddress.setAddress4(address4);
        employeeAddress.setState(state);
        employeeAddress.setCountry(country);
        return employeeAddress;
    }
    public Long getId() {
        return id;
    }
    public String getAddress1() {
        return address1;
    }
    public String getAddress2() {
        return address2;
    }
    public String getAddress3() {
        return address3;
    }
    public String getAddress4() {
        return address4;
    }
    public String getState() {
        return state;
    }
    public String getCountry() {
        return country;
    }
}
