package employeeapi.resource;

import org.springframework.hateoas.ResourceSupport;

import project.item.ProjectItem;

public class ProjectResource extends ResourceSupport {
    private Integer projectId;
    private String projectName;
    private String techstack;
    public void fromProjectItem(ProjectItem projectItem) {
        projectId = projectItem.getProjectId();
        projectName = projectItem.getProjectName();
        techstack = projectItem.getTechstack();
    }
    public Integer getProjectId() {
        return projectId;
    }
    public void setProjectId(Integer projectId) {
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
