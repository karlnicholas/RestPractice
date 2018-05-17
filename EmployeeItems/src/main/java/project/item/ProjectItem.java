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
    // needed for mocking unit tests
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
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
        ProjectItem other = (ProjectItem) obj;
        if (projectId == null) {
            if (other.projectId != null)
                return false;
        } else if (!projectId.equals(other.projectId))
            return false;
        return true;
    }
}
