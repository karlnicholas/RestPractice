package employeeapi.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import employeedetail.item.EmployeeDetailItem;
import employeeapi.controller.EmployeeDetailController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * @author Karl Nicholas
 */
@Component
public class EmployeeDetailResourceAssembler
        extends ResourceAssemblerSupport<EmployeeDetailItem, EmployeeDetailResource> {

    public EmployeeDetailResourceAssembler() {
        super(EmployeeDetailController.class, EmployeeDetailResource.class);
    }

    @Override
    public EmployeeDetailResource toResource(EmployeeDetailItem item) {

        // createResource(employeeAddressItem);
        EmployeeDetailResource resource = createResourceWithId(item.getEmpId(), item);
        // … do further mapping
        resource.add(linkTo(methodOn(EmployeeDetailController.class).deleteEmployeeDetail(item.getEmpId())).withRel("delete"));        
        resource.add(linkTo(methodOn(EmployeeDetailController.class).putEmployeeDetail(item)).withRel("update"));
        resource.add(linkTo(methodOn(EmployeeDetailController.class).postEmployeeDetail(item)).withRel("create"));
        return resource;
    }

}