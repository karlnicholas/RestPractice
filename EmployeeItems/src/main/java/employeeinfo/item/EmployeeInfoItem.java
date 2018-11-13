package employeeinfo.item;

import java.util.List;

import employeeaddress.item.EmployeeAddressItem;
import employeedetail.item.EmployeeDetailItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.item.ProjectItem;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInfoItem {
    private EmployeeAddressItem employeeAddressItem;  
    private EmployeeDetailItem employeeDetailItem;
    private List<ProjectItem> projectItems;          
}
