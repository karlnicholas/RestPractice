package employeeapi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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

import employeeproject.item.EmployeeProjectItem;

@RestController
@RequestMapping("/employee/project")
public class EmployeeProjectController {
    @Autowired
    private RestTemplate restTemplate;

    protected static final String serviceUrl = "http://EmployeeProject"; // EmployeeAddress is the name of the microservice we're calling
    
    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeProjectItem>> getEmployeeProjects(@PathVariable Integer empId ) {

        return restTemplate.exchange(serviceUrl + "/employee/project/{empId}",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeProjectItem>>() {}, empId);
    }

    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> postEmployeeProject(@RequestBody EmployeeProjectItem employeeProjectItem) {
        RequestEntity<EmployeeProjectItem> request = RequestEntity
            .post(URI.create(serviceUrl + "/employee/project/create"))
            .accept(MediaType.APPLICATION_JSON)
            .body(employeeProjectItem);
        return ResponseEntity.ok(restTemplate.exchange(request, EmployeeProjectItem.class).getBody());
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> putEmployeeProject(@RequestBody EmployeeProjectItem employeeProjectItem) {
        RequestEntity<EmployeeProjectItem> request = RequestEntity
            .put(URI.create(serviceUrl + "/employee/project/update"))
            .accept(MediaType.APPLICATION_JSON)
            .body(employeeProjectItem);
        return ResponseEntity.ok(restTemplate.exchange(request, EmployeeProjectItem.class).getBody());
    }

    @DeleteMapping(value="/delete/{empId}/{projectId}", produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteEmployeeProject(@PathVariable Integer empId, @PathVariable Integer projectId) {
        restTemplate.delete(serviceUrl + "/employee/project/delete/{empId}/{projectId}", empId, projectId);
        return ResponseEntity.ok( HttpStatus.OK.name() );
    }

}
