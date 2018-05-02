package employeeapi.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import employeeapi.controller.EmployeeInfoController;
import employeedetail.item.SparseEmployeeDetailItem;

/**
 * @author Karl Nicholas
 */
@Component
public class SparseEmployeeDetailResourceAssembler
        extends ResourceAssemblerSupport<SparseEmployeeDetailItem, SparseEmployeeDetailResource> {

    public SparseEmployeeDetailResourceAssembler() {
        super(EmployeeInfoController.class, SparseEmployeeDetailResource.class);
    }

    @Override
    public SparseEmployeeDetailResource toResource(SparseEmployeeDetailItem item) {

        // createResource(employeeAddressItem);
        SparseEmployeeDetailResource resource = createResourceWithId(item.getEmpId(), item);
        // … do further mapping
        return resource;
    }

}