package employeeapi.resource;

import org.springframework.hateoas.ResourceSupport;

import employeedetail.item.SparseEmployeeDetailItem;

public class SparseEmployeeDetailResource extends ResourceSupport {
    private Integer empId;
    private String name;
    
    public void fromSparseEmployeeDetailItem(SparseEmployeeDetailItem item) {
        this.empId = item.getEmpId();
        this.name = item.getName();
    }

    public Integer getEmpId() {
        return empId;
    }
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
