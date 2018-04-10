package employeeaddress.item;

import lombok.Data;

@Data
public class EmployeeAddressItem {
    private Integer empId;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String state;
    private String country;
}
