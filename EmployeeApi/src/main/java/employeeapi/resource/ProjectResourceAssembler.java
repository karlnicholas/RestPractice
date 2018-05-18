package employeeapi.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import employeeapi.controller.ProjectController;
import project.item.ProjectItem;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * @author Karl Nicholas
 */
@Component
public class ProjectResourceAssembler
        extends ResourceAssemblerSupport<ProjectItem, ProjectResource> {

    public ProjectResourceAssembler() {
        super(ProjectController.class, ProjectResource.class);
    }

    @Override
    public ProjectResource toResource(ProjectItem item) {
        ProjectResource resource = createResourceWithId(item.getProjectId(), item);                
        resource.fromProjectItem(item);
        // … do further mapping
        resource.add(linkTo(methodOn(ProjectController.class).postProject(item)).withRel("create"));
        resource.add(linkTo(methodOn(ProjectController.class).putProject(item)).withRel("update"));
        resource.add(linkTo(methodOn(ProjectController.class).deleteProject(item.getProjectId())).withRel("delete"));        
        return resource;
    }

}