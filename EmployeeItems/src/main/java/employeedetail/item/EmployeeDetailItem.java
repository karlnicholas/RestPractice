package employeedetail.item;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EmployeeDetailItem {
    private Integer empId;
    private String name;
    private String role;
    private BigDecimal salary;
    private String roleDescription;

}
