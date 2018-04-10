package employeeproject.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProjectId implements Serializable {
    private Integer empId;    
    private String projectId;
}
