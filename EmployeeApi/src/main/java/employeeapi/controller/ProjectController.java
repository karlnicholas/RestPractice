package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeapi.resource.ProjectResource;
import employeeapi.resource.ProjectResourceAssembler;
import project.item.SparseProjectItem;
import project.item.ProjectItem;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectClient projectClient;
    @Autowired
    private ProjectResourceAssembler assembler;
    
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
        return ResponseEntity.ok(assembler.toResource(projectClient.getProject(projectId).getBody()));    
    }

    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResource> postProject(@RequestBody ProjectItem projectItem) {
        return ResponseEntity.ok( assembler.toResource(projectClient.postProject(projectItem).getBody()) );
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResource> putProject(@RequestBody ProjectItem projectItem) {
        return ResponseEntity.ok( assembler.toResource(projectClient.putProject(projectItem).getBody()) );
    }

    @DeleteMapping(value="/delete/{projectId}", produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteProject(@PathVariable Integer projectId) {
        return ResponseEntity.ok( projectClient.deleteProject(projectId).getBody() );
    }

    @FeignClient(name="EmployeeProject")
    public interface ProjectClient {
        @GetMapping(value="/project/projects", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Page<SparseProjectItem>> getProjects(Pageable pageable);

        @GetMapping(value="/project/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ProjectItem> getProject(@PathVariable("projectId") Integer projectId);
        
        @PostMapping(value="/project/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ProjectItem> postProject(@RequestBody ProjectItem projectItem);

        @PutMapping(value="/project/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ProjectItem> putProject(@RequestBody ProjectItem projectItem);

        @DeleteMapping(value="/project/delete/{projectId}", produces=MediaType.TEXT_PLAIN_VALUE)
        public ResponseEntity<String> deleteProject(@PathVariable("projectId") Integer projectId);
    }    
}
