package employeeapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
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

import employeedetail.item.EmployeeDetailItem;

@RestController
@RequestMapping("/employee/detail")
public class EmployeeDetailController {
    @Autowired
    private RestTemplate restTemplate;
    
    public static final String serviceUrl = "http://EmployeeDetail"; // EmployeeAddress is the name of the microservice we're calling

    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> getEmployeeDetail(@PathVariable Integer empId) {
        EmployeeDetailItem employeeDetailItem = restTemplate.getForObject( 
                serviceUrl + "/employee/detail/{empId}", 
                EmployeeDetailItem.class, 
                empId);
        return ResponseEntity.ok( employeeDetailItem );
    }
    
    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> postEmployeeDetail(@RequestBody EmployeeDetailItem employeeDetailItem) {
        RequestEntity<EmployeeDetailItem> request = RequestEntity
            .post(URI.create(serviceUrl + "/employee/detail/create"))
            .accept(MediaType.APPLICATION_JSON)
            .body(employeeDetailItem);
        return ResponseEntity.ok(restTemplate.exchange(request, EmployeeDetailItem.class).getBody());
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> putEmployeeDetail(@RequestBody EmployeeDetailItem employeeDetailItem) {
        RequestEntity<EmployeeDetailItem> request = RequestEntity
            .put(URI.create(serviceUrl + "/employee/detail/update"))
            .accept(MediaType.APPLICATION_JSON)
            .body(employeeDetailItem);
        return ResponseEntity.ok(restTemplate.exchange(request, EmployeeDetailItem.class).getBody());
    }

    @DeleteMapping(value="/delete/{empId}", produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteEmployeeDetail(@PathVariable Integer empId) {
        restTemplate.delete(serviceUrl + "/employee/detail/delete/{empId}", empId);
        return ResponseEntity.ok( HttpStatus.OK.name() );
    }
    
}
