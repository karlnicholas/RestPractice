package employeeproject.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SparseProjectItem {
    private final Integer projectId;
    private final String projectName;
    @JsonCreator
    public SparseProjectItem(
        @JsonProperty("projectId") Integer projectId, 
        @JsonProperty("projectName") String projectName
    ) {
        this.projectId = projectId;
        this.projectName = projectName;
    }
    public Integer getProjectId() {
        return projectId;
    }
    public String getProjectName() {
        return projectName;
    }
}
