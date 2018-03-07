package employeeproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import employeeproject.view.EmployeeProjectView;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames="empId")})
public class EmployeeProject {
    @Id @GeneratedValue
    private Long id;
    
    private String projectId;
    private String projectName;
    private String techstack;
    private String empId;

    public EmployeeProject() {}
    public EmployeeProject(EmployeeProjectView employeeProjectView) {
        id = employeeProjectView.getId();
        projectId = employeeProjectView.getProjectId();
        projectName = employeeProjectView.getProjectName();
        techstack = employeeProjectView.getTechstack();
        empId = employeeProjectView.getEmpId();
    }
    
    public EmployeeProjectView asEmployeeProjectView() {
        EmployeeProjectView employeeProjectView = new EmployeeProjectView();
        employeeProjectView.setId(id);
        employeeProjectView.setProjectId(projectId);
        employeeProjectView.setProjectName(projectName);
        employeeProjectView.setTechstack(techstack);
        employeeProjectView.setEmpId(empId);
        return employeeProjectView;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
