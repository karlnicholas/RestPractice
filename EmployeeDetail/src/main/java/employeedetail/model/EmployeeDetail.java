package employeedetail.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import employeedetail.item.EmployeeDetailItem;

@Entity
public class EmployeeDetail {
    @Id private Integer empId;
    private String name;
    private String role;
    private BigDecimal salary;
    private String roleDescription;

    public EmployeeDetail() {}
    public EmployeeDetail(EmployeeDetailItem employeeDetailItem) {
        empId = employeeDetailItem.getEmpId();
        name = employeeDetailItem.getName();
        role = employeeDetailItem.getRole();
        salary = employeeDetailItem.getSalary();
        roleDescription = employeeDetailItem.getRoleDescription();
    }
    
    public EmployeeDetailItem asEmployeeDetailItem() {
        EmployeeDetailItem employeeDetailItem = new EmployeeDetailItem();
        employeeDetailItem.setEmpId(empId);
        employeeDetailItem.setName(name);
        employeeDetailItem.setRole(role);
        employeeDetailItem.setSalary(salary);
        employeeDetailItem.setRoleDescription(roleDescription);
        return employeeDetailItem;
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

    // needed for test mocking
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((empId == null) ? 0 : empId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmployeeDetail other = (EmployeeDetail) obj;
        if (empId == null) {
            if (other.empId != null)
                return false;
        } else if (!empId.equals(other.empId))
            return false;
        return true;
    }
}
