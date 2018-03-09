package employeeapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeproject.view.EmployeeProjectView;

@RestController
public class EmployeeProjectController {
    @Autowired
    EmployeeProjectClient employeeProjectClient;
    
    @GetMapping(value="/employee/project/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectView> getEmployeeProject(@PathVariable("id") Long id) {
        return employeeProjectClient.getEmployeeProject(id);
    }
    
    @PostMapping(value="/employee/project/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectView> postEmployeeProject(EmployeeProjectView employeeProjectView) {
        return employeeProjectClient.postEmployeeProject(employeeProjectView);
    }

    @PutMapping(value="/employee/project/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectView> putEmployeeProject(EmployeeProjectView employeeProjectView) {
        return employeeProjectClient.putEmployeeProject(employeeProjectView);
    }

    @DeleteMapping(value="/employee/project/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectView> deleteEmployeeProject(@PathVariable("id") Long id) {
        return employeeProjectClient.deleteEmployeeProject(id);
    }
    
    @FeignClient(name="EmployeeProject")
    interface EmployeeProjectClient {
        @GetMapping(value="/employee/project/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeProjectView> getEmployeeProject(@PathVariable("id") Long id);
        
        @PostMapping(value="/employee/project/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeProjectView> postEmployeeProject(EmployeeProjectView employeeProjectView);

        @PutMapping(value="/employee/project/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeProjectView> putEmployeeProject(EmployeeProjectView employeeProjectView);

        @DeleteMapping(value="/employee/project/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeProjectView> deleteEmployeeProject(@PathVariable("id") Long id);
    }    
}
