package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
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

import employeeapi.resource.ProjectResource;
import employeeapi.resource.ProjectResourceAssembler;
import project.item.ProjectItem;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProjectResourceAssembler assembler;

    protected static final String serviceUrl = "http://Project"; // EmployeeAddress is the name of the microservice we're calling
    
    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getApi() {
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(ProjectController.class).getProject(null)).withRel("project") );
        resource.add( linkTo(methodOn(ProjectController.class).postProject(null)).withRel("create"));
        resource.add( linkTo(methodOn(ProjectController.class).putProject(null)).withRel("update"));
        resource.add( linkTo(methodOn(ProjectController.class).deleteProject(null)).withRel("delete"));
        return ResponseEntity.ok(resource);
    }
    @GetMapping(value="/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResource> getProject(@PathVariable Integer projectId ) {
        ProjectItem response = restTemplate.getForObject( 
                serviceUrl + "/project/{projectId}", 
                ProjectItem.class, 
                projectId);
        return ResponseEntity.ok( assembler.toResource(response) );
    }

    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResource> postProject(@RequestBody ProjectItem projectItem) {
        RequestEntity<ProjectItem> request = RequestEntity
            .post(URI.create(serviceUrl + "/project/create"))
            .accept(MediaType.APPLICATION_JSON)
            .body(projectItem);
        return ResponseEntity.ok( 
            assembler.toResource(request.getBody()) 
        );
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResource> putProject(@RequestBody ProjectItem projectItem) {
        RequestEntity<ProjectItem> request = RequestEntity
                .put(URI.create(serviceUrl + "/project/update"))
                .accept(MediaType.APPLICATION_JSON)
                .body(projectItem);
            return ResponseEntity.ok( 
                assembler.toResource(request.getBody()) 
            );
    }

    @DeleteMapping(value="/delete/{projectId}", produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteProject(@PathVariable Integer projectId) {
        restTemplate.delete(serviceUrl + "/project/delete/{projectId}", projectId);
        return ResponseEntity.ok( HttpStatus.OK.name() );
    }
}
