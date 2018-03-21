package employeeinfo.item;

import java.math.BigDecimal;

import employeeaddress.item.EmployeeAddressItem;
import employeedetail.item.EmployeeDetailItem;
import employeeproject.item.EmployeeProjectItem;

public class EmployeeInfoItem {
    private final Integer empId;
    private final String address1;
    private final String address2;
    private final String address3;
    private final String address4;
    private final String state;
    private final String country;
    private final String name;
    private final String role;
    private final BigDecimal salary;
    private final String roleDescription;
    private final String projectId;
    private final String projectName;
    private final String techstack;

    public EmployeeInfoItem(
            EmployeeAddressItem employeeAddressItem, 
            EmployeeDetailItem employeeDetailItem, 
            EmployeeProjectItem employeeProjectItem 
    ) {
        if ( !employeeAddressItem.getEmpId().equals(employeeDetailItem.getEmpId()) 
            || !employeeDetailItem.getEmpId().equals(employeeProjectItem.getEmpId())) {
            throw new IllegalArgumentException("Invalid EmpIds");
        }
            
        empId = employeeAddressItem.getEmpId();
        address1 = employeeAddressItem.getAddress1();
        address2 = employeeAddressItem.getAddress2();
        address3 = employeeAddressItem.getAddress3();
        address4 = employeeAddressItem.getAddress4();
        state = employeeAddressItem.getState();
        country = employeeAddressItem.getCountry();
        name = employeeDetailItem.getName();
        role = employeeDetailItem.getRole();
        salary = employeeDetailItem.getSalary();
        roleDescription = employeeDetailItem.getRoleDescription();
        projectId = employeeProjectItem.getProjectId();
        projectName = employeeProjectItem.getProjectName();
        techstack = employeeProjectItem.getTechstack();
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
    public String getName() {
        return name;
    }
    public String getRole() {
        return role;
    }
    public BigDecimal getSalary() {
        return salary;
    }
    public String getRoleDescription() {
        return roleDescription;
    }
    public String getProjectId() {
        return projectId;
    }
    public String getProjectName() {
        return projectName;
    }
    public String getTechstack() {
        return techstack;
    }
    public Integer getEmpId() {
        return empId;
    }
}
