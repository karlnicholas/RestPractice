package employeeproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeproject.item.ProjectItem;
import employeeproject.item.SparseProjectItem;
import employeeproject.model.Project;
import employeeproject.service.ProjectRepository;

@RestController
@RequestMapping("/project")
public class ProjectController {
    
    @Autowired
    private ProjectRepository projectPepository;
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SparseProjectItem>> getProjects(Pageable pageable) {
        return ResponseEntity.ok(projectPepository.findAllBy(pageable));
    }
    
    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectItem> getProject(@PathVariable("projectId") Integer projectId) {
        logger.info("get: " + projectId);
        return ResponseEntity.ok(projectPepository.getOne(projectId).asProjectItem());
    }
    
    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectItem> postEmployeeProject(ProjectItem projectItem) {
        return ResponseEntity.ok(projectPepository.save(new Project(projectItem)).asProjectItem());
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectItem> putEmployeeProject(ProjectItem employeeProjectItem) {
        return ResponseEntity.ok(projectPepository.save(new Project(employeeProjectItem)).asProjectItem());
    }

    @DeleteMapping(value="/delete/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectItem> deleteEmployeeProject(@PathVariable("projectId") Integer projectId) {
        Project project = projectPepository.getOne(projectId);
        projectPepository.delete(project);        
        return ResponseEntity.ok(project.asProjectItem());
    }
    
}
