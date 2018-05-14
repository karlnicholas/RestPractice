package employeeproject.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import employeeproject.item.EmployeeProjectItem;

@Entity
@SuppressWarnings("serial")
public class EmployeeProject implements Serializable {
    @EmbeddedId
    private EmployeeProjectId employeeProjectId;

    public EmployeeProject() {}
    public EmployeeProject(Integer empId, Integer projectId) {
        employeeProjectId = new EmployeeProjectId(empId, projectId); 
    }

    public EmployeeProject(EmployeeProjectItem employeeProjectItem) {
        employeeProjectId = new EmployeeProjectId(
                employeeProjectItem.getEmpId(), 
                employeeProjectItem.getProjectId()
            );
    }
    public EmployeeProjectId getEmployeeProjectId() {
        return employeeProjectId;
    }

    public void setEmployeeProjectId(EmployeeProjectId employeeProjectId) {
        this.employeeProjectId = employeeProjectId;
    }
    public EmployeeProjectItem asEmployeeProjectItem() {
        return new EmployeeProjectItem( employeeProjectId.getEmpId(), employeeProjectId.getProjectId() ); 
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((employeeProjectId == null) ? 0 : employeeProjectId.hashCode());
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
        EmployeeProject other = (EmployeeProject) obj;
        if (employeeProjectId == null) {
            if (other.employeeProjectId != null)
                return false;
        } else if (!employeeProjectId.equals(other.employeeProjectId))
            return false;
        return true;
    }

}
