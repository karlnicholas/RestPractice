package employeeproject.item;

import lombok.Data;

@Data
public class EmployeeProjectItem {
    private Integer empId;
    private String projectId;
    private String projectName;
    private String techstack;
}
