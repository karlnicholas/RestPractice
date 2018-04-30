package employeeproject.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectItem {
    private final Integer projectId;
    private final String projectName;
    private final String techstack;
    public ProjectItem(ProjectItem projectItem) {
        this.projectId = projectItem.projectId;
        this.projectName = projectItem.projectName;
        this.techstack = projectItem.techstack;
    }
}
