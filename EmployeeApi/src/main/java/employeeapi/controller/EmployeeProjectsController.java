package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeapi.resource.ProjectResource;
import employeeapi.resource.ProjectResourceAssembler;
import project.item.ProjectItem;

@RestController
@RequestMapping("/employee/projects")
public class EmployeeProjectsController {
    @Autowired
    private EmployeeProjectsClient employeeProjectsClient;
    @Autowired
    private ProjectResourceAssembler assembler;
    
    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getApi() {
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(EmployeeProjectsController.class).getEmployeeProjects(null)).withRel("/") );
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resources<ProjectResource>> getEmployeeProjects(@PathVariable("empId") Integer empId ) {
        Link linkSelf = linkTo(methodOn(EmployeeProjectsController.class).getEmployeeProjects(empId)).slash('/').withSelfRel();

        ResponseEntity<List<ProjectItem>> idsResponse = employeeProjectsClient.getEmployeeProjectsFull(empId);
        List<ProjectResource> resources = new ArrayList<>();
        for ( ProjectItem item: idsResponse.getBody() ) {
            resources.add( assembler.toResource(item));
        }
        //Wrap your resources in a Resources object.
        Resources<ProjectResource> resourceList = new Resources<ProjectResource>(resources, linkSelf);

        return new ResponseEntity<Resources<ProjectResource>>(resourceList, HttpStatus.OK);    
    }

    @FeignClient(name="EmployeeProject")
    public interface EmployeeProjectsClient {
        @GetMapping(value="/employee/projects/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<ProjectItem>> getEmployeeProjectsFull(@PathVariable("empId") Integer empId);
    }    
}
