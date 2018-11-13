package employeeapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
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

import project.item.ProjectItem;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private RestTemplate restTemplate;

    protected static final String serviceUrl = "http://EmployeeProject"; // EmployeeAddress is the name of the microservice we're calling

    @GetMapping(value="/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectItem> getProject(@PathVariable Integer projectId ) {
        ProjectItem response = restTemplate.getForObject( 
                serviceUrl + "/project/{projectId}", 
                ProjectItem.class, 
                projectId);
        return ResponseEntity.ok( response );
    }

    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectItem> postProject(@RequestBody ProjectItem projectItem) {
        RequestEntity<ProjectItem> request = RequestEntity
            .post(URI.create(serviceUrl + "/project/create"))
            .accept(MediaType.APPLICATION_JSON)
            .body(projectItem);
        return ResponseEntity.ok(request.getBody());
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectItem> putProject(@RequestBody ProjectItem projectItem) {
        RequestEntity<ProjectItem> request = RequestEntity
                .put(URI.create(serviceUrl + "/project/update"))
                .accept(MediaType.APPLICATION_JSON)
                .body(projectItem);
            return ResponseEntity.ok(request.getBody());
    }

    @DeleteMapping(value="/delete/{projectId}", produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteProject(@PathVariable Integer projectId) {
        restTemplate.delete(serviceUrl + "/project/delete/{projectId}", projectId);
        return ResponseEntity.ok( HttpStatus.OK.name() );
    }
}
