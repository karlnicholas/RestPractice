package employeeapi.resource;

import org.springframework.hateoas.ResourceSupport;

import project.item.SparseProjectItem;

public class SparseProjectResource extends ResourceSupport {
    private Integer projectId;
    private String projectName;
    public void fromProjectItem(SparseProjectItem sparseProjectItem) {
        projectId = sparseProjectItem.getProjectId();
        projectName = sparseProjectItem.getProjectName();
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
}
