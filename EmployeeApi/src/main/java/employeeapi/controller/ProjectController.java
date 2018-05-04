package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
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

import employeeapi.resource.ProjectResource;
import employeeapi.resource.ProjectResourceAssembler;
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
        resource.add( linkTo(methodOn(ProjectController.class).getProjects(null, null)).withRel("projects") );
        resource.add( linkTo(methodOn(ProjectController.class).getProject(null)).withRel("project") );
        resource.add( linkTo(methodOn(ProjectController.class).postProject(null)).withRel("create"));
        resource.add( linkTo(methodOn(ProjectController.class).putProject(null)).withRel("update"));
        resource.add( linkTo(methodOn(ProjectController.class).deleteProject(null)).withRel("delete"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value="/projects", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResources<ProjectResource>> getProjects(
        Pageable pageable, 
        PagedResourcesAssembler<ProjectItem> pagedResourcesAssembler        
    ) {
        ResponseEntity<Page<ProjectItem>> idsResponse = projectClient.getProjects(pageable);                
        PagedResources<ProjectResource> pagedResources = pagedResourcesAssembler.toResource(idsResponse.getBody(), assembler);        
        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
    }

    @GetMapping(value="/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResource> getProject(@PathVariable("projectId") Integer projectId ) {
        return ResponseEntity.ok(assembler.toResource(projectClient.getProject(projectId).getBody()));    
    }

    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResource> postProject(ProjectItem projectItem) {
        return ResponseEntity.ok( assembler.toResource(projectClient.postProject(projectItem).getBody()) );
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResource> putProject(ProjectItem projectItem) {
        return ResponseEntity.ok( assembler.toResource(projectClient.putProject(projectItem).getBody()) );
    }

    @DeleteMapping(value="/delete/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResource> deleteProject(@PathVariable("projectId") Integer projectId) {
        return ResponseEntity.ok( assembler.toResource(projectClient.deleteProject(projectId).getBody()) );
    }

    @FeignClient(name="EmployeeProject")
    public interface ProjectClient {
        @GetMapping(value="/project", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Page<ProjectItem>> getProjects(Pageable pageable);

        @GetMapping(value="/project/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ProjectItem> getProject(@PathVariable("projectId") Integer projectId);
        
        @PostMapping(value="/project/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ProjectItem> postProject(ProjectItem projectItem);

        @PutMapping(value="/project/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ProjectItem> putProject(ProjectItem projectItem);

        @DeleteMapping(value="/project/delete/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ProjectItem> deleteProject(@PathVariable("projectId") Integer projectId);
    }    
}
