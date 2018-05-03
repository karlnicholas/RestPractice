package project.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import project.item.ProjectItem;

@Entity
public class Project {
    @Id private Integer projectId;
    private String projectName;
    private String techstack;

    public Project() {}
    public Project(ProjectItem projectItem) {
        this.projectId = projectItem.getProjectId();
        this.projectName = projectItem.getProjectName();
        this.techstack = projectItem.getTechstack();
    }
    public ProjectItem asProjectItem() {
        return new ProjectItem(projectId, projectName, techstack);
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
