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

import employeeproject.item.EmployeeProjectItem;

@RestController
public class EmployeeProjectController {
    @Autowired
    private EmployeeProjectClient employeeProjectClient;
    
    @GetMapping(value="/employee/project/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> getEmployeeProject(@PathVariable("empId") Integer empId) {
        return employeeProjectClient.getEmployeeProject(empId);
    }
    
    @PostMapping(value="/employee/project/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> postEmployeeProject(EmployeeProjectItem employeeProjectItem) {
        return employeeProjectClient.postEmployeeProject(employeeProjectItem);
    }

    @PutMapping(value="/employee/project/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> putEmployeeProject(EmployeeProjectItem employeeProjectItem) {
        return employeeProjectClient.putEmployeeProject(employeeProjectItem);
    }

    @DeleteMapping(value="/employee/project/delete/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeProjectItem> deleteEmployeeProject(@PathVariable("empId") Integer empId) {
        return employeeProjectClient.deleteEmployeeProject(empId);
    }
    @FeignClient(name="EmployeeProject")
    public interface EmployeeProjectClient {
        @GetMapping(value="/employee/project/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeProjectItem> getEmployeeProject(@PathVariable("empId") Integer empId);
        
        @PostMapping(value="/employee/project/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeProjectItem> postEmployeeProject(EmployeeProjectItem employeeProjectItem);

        @PutMapping(value="/employee/project/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeProjectItem> putEmployeeProject(EmployeeProjectItem employeeProjectItem);

        @DeleteMapping(value="/employee/project/delete/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeProjectItem> deleteEmployeeProject(@PathVariable("empId") Integer id);
    }    
}
