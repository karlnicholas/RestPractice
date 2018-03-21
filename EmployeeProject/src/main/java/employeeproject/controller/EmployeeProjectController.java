package employeeproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeproject.model.EmployeeProject;
import employeeproject.service.EmployeeProjectRepository;
import employeeproject.item.EmployeeProjectItem;

@RestController
public class EmployeeProjectController {
    @Autowired
    private EmployeeProjectRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeProjectController.class);
    
    @GetMapping(value="/employee/project/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> getEmployeeProject(@PathVariable("empId") Integer empId) {
        logger.debug("empId = " + empId);
        return ResponseEntity.ok(repository.getOne(empId).asEmployeeProjectItem());
    }
    
    @PostMapping(value="/employee/project/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> postEmployeeProject(EmployeeProjectItem employeeProjectItem) {
        return ResponseEntity.ok(repository.save(new EmployeeProject(employeeProjectItem)).asEmployeeProjectItem());
    }

    @PutMapping(value="/employee/project/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> putEmployeeProject(EmployeeProjectItem employeeProjectItem) {
        return ResponseEntity.ok(repository.save(new EmployeeProject(employeeProjectItem)).asEmployeeProjectItem());
    }

    @DeleteMapping(value="/employee/project/delete/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> deleteEmployeeProject(@PathVariable("id") Integer empId) {
        EmployeeProject employeeProject = repository.getOne(empId);
        repository.delete(employeeProject);        
        return ResponseEntity.ok(employeeProject.asEmployeeProjectItem());
    }
}
