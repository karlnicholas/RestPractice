package employeeapi.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import employeeapi.controller.EmployeeProjectController;
import employeeproject.item.EmployeeProjectItem;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * @author Karl Nicholas
 */
@Component
public class EmployeeProjectResourceAssembler
        extends ResourceAssemblerSupport<EmployeeProjectItem, EmployeeProjectResource> {

    public EmployeeProjectResourceAssembler() {
        super(EmployeeProjectController.class, EmployeeProjectResource.class);
    }

    @Override
    public EmployeeProjectResource toResource(EmployeeProjectItem item) {

        // createResource(employeeAddressItem);
        EmployeeProjectResource resource = createResourceWithId(item.getEmpId(), item);
        resource.fromEmployeeProjectItem(item);
        // … do further mapping
//        resource.add(linkTo(methodOn(EmployeeProjectController.class).deleteEmployeeProject(item.getEmpId(), item.getProjectId())).withRel("delete"));        
//        resource.add(linkTo(methodOn(EmployeeProjectController.class).putEmployeeProject(item)).withRel("update"));
//        resource.add(linkTo(methodOn(EmployeeProjectController.class).postEmployeeProject(item)).withRel("create"));
        return resource;
    }

}