package employeeproject.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import employeeproject.item.EmployeeProjectItem;

@Entity
public class EmployeeProject {
    @Id private Integer empId;    
    private String projectId;
    private String projectName;
    private String techstack;

    public EmployeeProject() {}
    public EmployeeProject(EmployeeProjectItem employeeProjectItem) {
        empId = employeeProjectItem.getEmpId();
        projectId = employeeProjectItem.getProjectId();
        projectName = employeeProjectItem.getProjectName();
        techstack = employeeProjectItem.getTechstack();
    }
    
    public EmployeeProjectItem asEmployeeProjectItem() {
        EmployeeProjectItem employeeProjectItem = new EmployeeProjectItem();
        employeeProjectItem.setEmpId(empId);
        employeeProjectItem.setProjectId(projectId);
        employeeProjectItem.setProjectName(projectName);
        employeeProjectItem.setTechstack(techstack);
        return employeeProjectItem;
    }
    public Integer getEmpId() {
        return empId;
    }
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getTechstack() {
        return techstack;
    }
    public void setTechstack(String techstack) {
        this.techstack = techstack;
    }
}
