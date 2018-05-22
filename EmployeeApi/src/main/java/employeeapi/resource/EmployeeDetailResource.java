package employeeapi.resource;

import java.math.BigDecimal;

import org.springframework.hateoas.ResourceSupport;

import employeedetail.item.EmployeeDetailItem;

public class EmployeeDetailResource extends ResourceSupport {
    private Integer empId;
    private String name;
    private String role;
    private BigDecimal salary;
    private String roleDescription;
    public void fromEmployeeDetailItem(EmployeeDetailItem employeeDetailItem) {
        empId = employeeDetailItem.getEmpId();
        name = employeeDetailItem.getName();
        role = employeeDetailItem.getRole();
        salary = employeeDetailItem.getSalary();
        roleDescription = employeeDetailItem.getRoleDescription();
    }
    public Integer getEmpId() {
        return empId;
    }
    public void setEmpId(Integer empId) {
        this.empId = empId;
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
}
