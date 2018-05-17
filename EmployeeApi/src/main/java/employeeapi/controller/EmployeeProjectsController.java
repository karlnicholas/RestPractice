package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

import com.google.common.collect.Lists;

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
        resource.add( linkTo(methodOn(EmployeeProjectsController.class).getEmployeeProjects(null)).withRel("empId") );
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resources<ProjectResource>> getEmployeeProjects(@PathVariable Integer empId ) {
        Link linkSelf = linkTo(methodOn(EmployeeProjectsController.class).getEmployeeProjects(empId)).withSelfRel();
        return new ResponseEntity<Resources<ProjectResource>>(new Resources<ProjectResource>(
                Lists.transform(employeeProjectsClient.getEmployeeProjectsFull(empId).getBody(),
                        item -> assembler.toResource(item)),
                linkSelf), HttpStatus.OK);
    }

    @FeignClient(name="EmployeeProject")
    public interface EmployeeProjectsClient {
        @GetMapping(value="/employee/projects/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<ProjectItem>> getEmployeeProjectsFull(@PathVariable("empId") Integer empId);
    }    
}
