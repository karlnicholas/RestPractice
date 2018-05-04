package employeeproject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import employeeproject.item.EmployeeProjectItem;
import employeeproject.model.EmployeeProject;
import employeeproject.model.EmployeeProjectId;
import employeeproject.service.EmployeeProjectRepository;

@RestController
@RequestMapping("/employee/project")
public class EmployeeProjectController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeProjectController.class);
    @Autowired
    private EmployeeProjectRepository employeeProjectPepository;

    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeProjectItem>> getProjects(@PathVariable("empId") Integer empId) {
        logger.info("empId = " + empId);
        // can't get projection working.
        return ResponseEntity.ok(Lists.transform(
                employeeProjectPepository.findAll(Example.of(new EmployeeProject(empId, null))), 
                EmployeeProject::asEmployeeProjectItem
            )
        );
    }
    
    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> postEmployeeProject(EmployeeProjectItem employeeProjectItem) {
        return ResponseEntity.ok(
            employeeProjectPepository.save(
                new EmployeeProject(employeeProjectItem))
            .asEmployeeProjectItem()
        );
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> putEmployeeProject(EmployeeProjectItem employeeProjectItem) {
        return ResponseEntity.ok(
                employeeProjectPepository.save(
                    new EmployeeProject(employeeProjectItem))
                .asEmployeeProjectItem()
            );
    }

    @DeleteMapping(value="/delete/{empId}/{projectId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> deleteEmployeeProject(
        @PathVariable("empId") Integer empId, 
        @PathVariable("projectId") Integer projectId
    ) {
        EmployeeProject employeeProject = employeeProjectPepository.getOne(new EmployeeProjectId(empId, projectId));
        employeeProjectPepository.delete(employeeProject);        
        return ResponseEntity.ok(employeeProject.asEmployeeProjectItem());
    }
    
}
