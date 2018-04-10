package employeeapi.resource;

import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import employeeapi.controller.EmployeeInfoController;

/**
 * @author Karl Nicholas
 */
@Component
public class EmployeeInfoResourceAssembler
        extends IdentifiableResourceAssemblerSupport<EmployeeInfoResource, EmployeeInfoResource> {

    public EmployeeInfoResourceAssembler() {
        super(EmployeeInfoController.class, EmployeeInfoResource.class);
    }

    @Override
    public EmployeeInfoResource toResource(EmployeeInfoResource employeeInfoResource) {

        // createResource(employeeAddressItem);
        EmployeeInfoResource resource = createResourceWithId(employeeInfoResource.getEmpId(), employeeInfoResource);
        resource.fromEmployeeInfoResource(employeeInfoResource);
        // … do further mapping
        return resource;
    }

}