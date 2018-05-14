package employeedetail.item;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
public class EmployeeDetailItem {
    private Integer empId;
    private String name;
    private String role;
    @JsonProperty("salary")
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal salary;
    private String roleDescription;
}
