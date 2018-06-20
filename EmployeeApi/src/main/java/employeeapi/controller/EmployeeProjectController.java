package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import employeeapi.resource.EmployeeProjectResource;
import employeeapi.resource.EmployeeProjectResourceAssembler;
import employeeproject.item.EmployeeProjectItem;

@RestController
@RequestMapping("/employee/project")
public class EmployeeProjectController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EmployeeProjectResourceAssembler assembler;

    protected static final String serviceUrl = "http://EmployeeProject"; // EmployeeAddress is the name of the microservice we're calling
    
    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getApi() {
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(EmployeeProjectController.class).getEmployeeProjects(null)).withRel("project") );
        resource.add( linkTo(methodOn(EmployeeProjectController.class).postEmployeeProject(null)).withRel("create"));
        resource.add( linkTo(methodOn(EmployeeProjectController.class).putEmployeeProject(null)).withRel("update"));
        resource.add( linkTo(methodOn(EmployeeProjectController.class).deleteEmployeeProject(null, null)).withRel("delete"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resources<EmployeeProjectResource>> getEmployeeProjects(@PathVariable Integer empId ) {
        Link linkSelf = linkTo(methodOn(EmployeeProjectController.class).getEmployeeProjects(empId)).slash('/').withSelfRel();
        Link linkCreate = linkTo(methodOn(EmployeeProjectController.class).postEmployeeProject(null)).withRel("create");

        ResponseEntity<List<EmployeeProjectItem>> idsResponse = restTemplate.exchange(serviceUrl + "/employee/project/{empId}",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeProjectItem>>() {}, empId);

        List<EmployeeProjectResource> resources = new ArrayList<>();
        for ( EmployeeProjectItem item: idsResponse.getBody() ) {
            resources.add( assembler.toResource(item));
        }
        //Wrap your resources in a Resources object.
        Resources<EmployeeProjectResource> resourceList = new Resources<EmployeeProjectResource>(resources, linkSelf, linkCreate);

        return new ResponseEntity<Resources<EmployeeProjectResource>>(resourceList, HttpStatus.OK);    
    }

    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectResource> postEmployeeProject(@RequestBody EmployeeProjectItem employeeProjectItem) {
        RequestEntity<EmployeeProjectItem> request = RequestEntity
            .post(URI.create(serviceUrl + "/employee/project/create"))
            .accept(MediaType.APPLICATION_JSON)
            .body(employeeProjectItem);
        return ResponseEntity.ok( 
            assembler.toResource(restTemplate.exchange(request, EmployeeProjectItem.class).getBody()) 
        );
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectResource> putEmployeeProject(@RequestBody EmployeeProjectItem employeeProjectItem) {
        RequestEntity<EmployeeProjectItem> request = RequestEntity
            .put(URI.create(serviceUrl + "/employee/project/update"))
            .accept(MediaType.APPLICATION_JSON)
            .body(employeeProjectItem);
        return ResponseEntity.ok( 
            assembler.toResource(restTemplate.exchange(request, EmployeeProjectItem.class).getBody()) 
        );
    }

    @DeleteMapping(value="/delete/{empId}/{projectId}", produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteEmployeeProject(@PathVariable Integer empId, @PathVariable Integer projectId) {
        restTemplate.delete(serviceUrl + "/employee/project/delete/{empId}/{projectId}", empId, projectId);
        return ResponseEntity.ok( HttpStatus.OK.name() );
    }

}
