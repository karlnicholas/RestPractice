package project.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectItem {
    private final Integer projectId;
    private final String projectName;
    private final String techstack;
    @JsonCreator
    public ProjectItem(
        @JsonProperty("projectId") Integer projectId, 
        @JsonProperty("projectName") String projectName, 
        @JsonProperty("techstack") String techstack
    ) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.techstack = techstack;
    }
    public Integer getProjectId() {
        return projectId;
    }
    public String getProjectName() {
        return projectName;
    }
    public String getTechstack() {
        return techstack;
    }
}
