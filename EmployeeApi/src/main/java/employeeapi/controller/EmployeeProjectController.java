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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeapi.resource.EmployeeProjectResource;
import employeeapi.resource.EmployeeProjectResourceAssembler;
import employeeproject.item.EmployeeProjectItem;

@RestController
@RequestMapping("/employee/project")
public class EmployeeProjectController {
    @Autowired
    private EmployeeProjectClient employeeProjectClient;
    @Autowired
    private EmployeeProjectResourceAssembler assembler;
    
    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getApi() {
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(EmployeeProjectController.class).getEmployeeProjects(null)).withRel("/") );
        resource.add( linkTo(methodOn(EmployeeProjectController.class).postEmployeeProject(null)).withRel("create"));
        resource.add( linkTo(methodOn(EmployeeProjectController.class).putEmployeeProject(null)).withRel("update"));
        resource.add( linkTo(methodOn(EmployeeProjectController.class).deleteEmployeeProject(null, null)).withRel("delete"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resources<EmployeeProjectResource>> getEmployeeProjects(@PathVariable("empId") Integer empId ) {
        Link linkSelf = linkTo(methodOn(EmployeeProjectController.class).getEmployeeProjects(empId)).slash('/').withSelfRel();
        Link linkCreate = linkTo(methodOn(EmployeeProjectController.class).postEmployeeProject(null)).withRel("create");

        ResponseEntity<List<EmployeeProjectItem>> idsResponse = employeeProjectClient.getEmployeeProjects(empId);
        List<EmployeeProjectResource> resources = new ArrayList<>();
        for ( EmployeeProjectItem item: idsResponse.getBody() ) {
            resources.add( assembler.toResource(item));
        }
        //Wrap your resources in a Resources object.
        Resources<EmployeeProjectResource> resourceList = new Resources<EmployeeProjectResource>(resources, linkSelf, linkCreate);

        return new ResponseEntity<Resources<EmployeeProjectResource>>(resourceList, HttpStatus.OK);    
    }

    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectResource> postEmployeeProject(EmployeeProjectItem employeeProjectItem) {
        return ResponseEntity.ok( assembler.toResource(employeeProjectClient.postEmployeeProject(employeeProjectItem).getBody()) );
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectResource> putEmployeeProject(EmployeeProjectItem employeeProjectItem) {
        return ResponseEntity.ok( assembler.toResource(employeeProjectClient.putEmployeeProject(employeeProjectItem).getBody()) );
    }

    @DeleteMapping(value="/delete/{empId}/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectResource> deleteEmployeeProject(@PathVariable("empId") Integer empId, @PathVariable("projectId") Integer projectId) {
        return ResponseEntity.ok( assembler.toResource(employeeProjectClient.deleteEmployeeProject(empId, projectId).getBody()) );
    }

    @FeignClient(name="EmployeeProject")
    public interface EmployeeProjectClient {
        @GetMapping(value="/employee/project/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<EmployeeProjectItem>> getEmployeeProjects(@PathVariable("empId") Integer empId);
        
        @PostMapping(value="/employee/project/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeProjectItem> postEmployeeProject(EmployeeProjectItem employeeProjectItem);

        @PutMapping(value="/employee/project/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeProjectItem> putEmployeeProject(EmployeeProjectItem employeeProjectItem);

        @DeleteMapping(value="/employee/project/delete/{empId}/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeProjectItem> deleteEmployeeProject(@PathVariable("empId") Integer empId, @PathVariable("projectId") Integer projectId);
    }    
}
