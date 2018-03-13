package employeeproject.item;

public class EmployeeProjectItem {
    private Long id;
    private String projectId;
    private String projectName;
    private String techstack;
    private String empId;
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
