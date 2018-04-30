package employeeapi.resource;

import org.springframework.hateoas.ResourceSupport;

import employeeproject.item.EmployeeProjectItem;

public class EmployeeProjectResource extends ResourceSupport {
    private Integer empId;
    private Integer projectId;
    public void fromEmployeeProjectItem(EmployeeProjectItem employeeProjectItem) {
        empId = employeeProjectItem.getEmpId();
        projectId = employeeProjectItem.getProjectId();
    }
    public Integer getEmpId() {
        return empId;
    }
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
    public Integer getProjectId() {
        return projectId;
    }
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
