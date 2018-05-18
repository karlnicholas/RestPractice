package employeeproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import project.item.SparseProjectItem;
import employeeproject.model.Project;
import employeeproject.service.ProjectRepository;
import project.item.ProjectItem;

@RestController
@RequestMapping("/project")
public class ProjectController {
    
    @Autowired
    private ProjectRepository projectPepository;
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    
    @GetMapping(value="/projects", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SparseProjectItem>> getProject(Pageable pageable) {
        return ResponseEntity.ok(projectPepository.findAllBy(pageable));
    }
    
    @GetMapping(value="/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectItem> getProject(@PathVariable Integer projectId) {
        logger.info("get: " + projectId);
        return ResponseEntity.ok(projectPepository.getOne(projectId).asProjectItem());
    }
    
    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectItem> postEmployeeProject(@RequestBody ProjectItem projectItem) {
        return ResponseEntity.ok(projectPepository.save(new Project(projectItem)).asProjectItem());
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectItem> putEmployeeProject(@RequestBody ProjectItem projectItem) {
        return ResponseEntity.ok(projectPepository.save(new Project(projectItem)).asProjectItem());
    }

    @DeleteMapping(value="/delete/{projectId}", produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteEmployeeProject(@PathVariable Integer projectId) {
        projectPepository.deleteById(projectId);
        return ResponseEntity.ok(HttpStatus.OK.name());
    }
    
}
