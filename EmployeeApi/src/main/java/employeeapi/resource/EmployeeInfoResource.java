package employeeapi.resource;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import employeeaddress.item.EmployeeAddressItem;
import employeedetail.item.EmployeeDetailItem;
import project.item.ProjectItem;

public class EmployeeInfoResource extends ResourceSupport {
    private Integer empId;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String state;
    private String country;
    private String name;
    private String role;
    private BigDecimal salary;
    private String roleDescription;
    private List<ProjectItem> projects;

    public EmployeeInfoResource() {}
    public EmployeeInfoResource(
            EmployeeAddressItem employeeAddressItem, 
            EmployeeDetailItem employeeDetailItem, 
            List<ProjectItem> employeeProjectItems 
    ) {
        if ( !employeeAddressItem.getEmpId().equals(employeeDetailItem.getEmpId()) ) {
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
        projects = employeeProjectItems;
    }
    public void fromEmployeeInfoResource(EmployeeInfoResource resource) {
        empId = resource.getEmpId();
        address1 = resource.getAddress1();
        address2 = resource.getAddress2();
        address3 = resource.getAddress3();
        address4 = resource.getAddress4();
        state = resource.getState();
        country = resource.getCountry();
        name = resource.getName();
        role = resource.getRole();
        salary = resource.getSalary();
        roleDescription = resource.getRoleDescription();
        projects = resource.projects;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public BigDecimal getSalary() {
        return salary;
    }
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    public String getRoleDescription() {
        return roleDescription;
    }
    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
    public List<ProjectItem> getProjects() {
        return projects;
    }
    public void setProjects(List<ProjectItem> projects) {
        this.projects = projects;
    }
}
