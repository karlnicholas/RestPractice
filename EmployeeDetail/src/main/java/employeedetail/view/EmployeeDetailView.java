package employeedetail.view;

import java.math.BigDecimal;

import employeedetail.model.EmployeeDetail;

public class EmployeeDetailView {
    private final Long id;
    private final String name;
    private final String empId;
    private final String role;
    private final BigDecimal salary;
    private final String roleDescription;

    public EmployeeDetailView(EmployeeDetail employeeDetail) {
        id = employeeDetail.getId();
        name = employeeDetail.getName();
        empId = employeeDetail.getEmpId();
        role = employeeDetail.getRole();
        salary = employeeDetail.getSalary();
        roleDescription = employeeDetail.getRoleDescription();
    }
    
    public EmployeeDetail asEmployeeDetail() {
        EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(id);
        employeeDetail.setName(name);
        employeeDetail.setEmpId(empId);
        employeeDetail.setRole(role);
        employeeDetail.setSalary(salary);
        employeeDetail.setRoleDescription(roleDescription);
        return employeeDetail;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmpId() {
        return empId;
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

}
