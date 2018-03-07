package employeeproject.view;

import employeeproject.model.EmployeeProject;

public class EmployeeProjectView {
    private final Long id;
    private final String projectId;
    private final String projectName;
    private final String techstack;

    public EmployeeProjectView(EmployeeProject employeeProject) {
        id = employeeProject.getId();
        projectId = employeeProject.getProjectId();
        projectName = employeeProject.getProjectName();
        techstack = employeeProject.getTechstack();
    }
    
    public EmployeeProject asEmployeeProject() {
        EmployeeProject employeeProject = new EmployeeProject();
        employeeProject.setId(id);
        employeeProject.setProjectId(projectId);
        employeeProject.setProjectName(projectName);
        employeeProject.setTechstack(techstack);
        return employeeProject;
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
    public Long getId() {
        return id;
    }
}
