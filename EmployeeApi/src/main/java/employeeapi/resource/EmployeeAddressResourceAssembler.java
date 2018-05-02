package employeeapi.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import employeeaddress.item.EmployeeAddressItem;
import employeeapi.controller.EmployeeAddressController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


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
    public EmployeeAddressResource toResource(EmployeeAddressItem item) {

        // createResource(employeeAddressItem);
        EmployeeAddressResource resource = createResourceWithId(item.getEmpId(), item);
        // … do further mapping
        resource.add(linkTo(methodOn(EmployeeAddressController.class).deleteEmployeeAddress(item.getEmpId())).withRel("delete"));        
        resource.add(linkTo(methodOn(EmployeeAddressController.class).putEmployeeAddress(item)).withRel("update"));
        resource.add(linkTo(methodOn(EmployeeAddressController.class).postEmployeeAddress(item)).withRel("create"));
        return resource;
    }

}