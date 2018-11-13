package employeeapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import project.item.ProjectItem;

@RestController
@RequestMapping("/employee/projects")
public class EmployeeProjectsController {
    @Autowired
    private RestTemplate restTemplate;

    public static final String serviceUrl = "http://EmployeeProject"; // EmployeeProjects is the name of the microservice we're calling
    
    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectItem>> getEmployeeProjects(@PathVariable Integer empId ) {
        return restTemplate.exchange(serviceUrl + "/employee/projects/{empId}",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<ProjectItem>>() {}, empId);

    }
}
