package employeeapi.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import employeeapi.controller.ProjectController;
import project.item.SparseProjectItem;

/**
 * @author Karl Nicholas
 */
@Component
public class SparseProjectResourceAssembler
        extends ResourceAssemblerSupport<SparseProjectItem, SparseProjectResource> {

    public SparseProjectResourceAssembler() {
        super(ProjectController.class, SparseProjectResource.class);
    }

    @Override
    public SparseProjectResource toResource(SparseProjectItem item) {
        SparseProjectResource resource = createResourceWithId(item.getProjectId(), item);                
        resource.fromProjectItem(item);
        // … do further mapping
        return resource;
    }

}