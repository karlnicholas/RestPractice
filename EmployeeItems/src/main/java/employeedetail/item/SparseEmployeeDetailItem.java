package employeedetail.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SparseEmployeeDetailItem {
    private final Integer empId;
    private final String name;
    @JsonCreator
    public SparseEmployeeDetailItem(
        @JsonProperty("empId") Integer empId, 
        @JsonProperty("name") String name
    ) {
        this.empId = empId;
        this.name = name;
    }
    public Integer getEmpId() {
        return empId;
    }
    public String getName() {
        return name;
    }
}
