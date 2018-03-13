package employeedetail.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import employeedetail.item.EmployeeDetailItem;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames="empId")})
public class EmployeeDetail {
    @Id @GeneratedValue
    private Long id;    
    private String name;
    private String empId;
    private String role;
    private BigDecimal salary;
    private String roleDescription;

    public EmployeeDetail() {}
    public EmployeeDetail(EmployeeDetailItem employeeDetailItem) {
        id = employeeDetailItem.getId();
        name = employeeDetailItem.getName();
        role = employeeDetailItem.getRole();
        salary = employeeDetailItem.getSalary();
        roleDescription = employeeDetailItem.getRoleDescription();
        empId = employeeDetailItem.getEmpId();
    }
    
    public EmployeeDetailItem asEmployeeDetailItem() {
        EmployeeDetailItem employeeDetailItem = new EmployeeDetailItem();
        employeeDetailItem.setId(id);
        employeeDetailItem.setName(name);
        employeeDetailItem.setEmpId(empId);
        employeeDetailItem.setRole(role);
        employeeDetailItem.setSalary(salary);
        employeeDetailItem.setRoleDescription(roleDescription);
        return employeeDetailItem;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
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
