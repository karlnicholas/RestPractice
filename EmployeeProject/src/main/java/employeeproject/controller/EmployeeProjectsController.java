package employeeproject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeproject.service.ProjectRepository;
import project.item.ProjectItem;

@RestController
@RequestMapping("/employee/projects")
public class EmployeeProjectsController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeProjectsController.class);
    @Autowired
    private ProjectRepository projectPepository;

    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectItem>> getProjects(@PathVariable("empId") Integer empId) {
        logger.info("empId = " + empId);
        return ResponseEntity.ok( projectPepository.findAllByEmployeeProjectEmpId(empId));
    }
}
