package employeeapi.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import employeeaddress.item.EmployeeAddressItem;
import employeeapi.controller.EmployeeAddressController;

/**
 * @author Karl Nicholas
 */
@Component
public class EmployeeAddressResourceAssembler
        extends ResourceAssemblerSupport<EmployeeAddressItem, EmployeeAddressResource> {

    public EmployeeAddressResourceAssembler() {
        super(EmployeeAddressController.class, EmployeeAddressResource.class);
    }

    @Override
    public EmployeeAddressResource toResource(EmployeeAddressItem employeeAddressItem) {

        // createResource(employeeAddressItem);
        EmployeeAddressResource resource = createResourceWithId(employeeAddressItem.getEmpId(), employeeAddressItem);
        // … do further mapping
        return resource;
    }

}