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

import employeeaddress.item.EmployeeAddressItem;

@RestController
@RequestMapping("/employee/address")
public class EmployeeAddressController {
    @Autowired
    private RestTemplate restTemplate;
    
    public static final String serviceUrl = "http://EmployeeAddress"; // EmployeeAddress is the name of the microservice we're calling
    
    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> getEmployeeAddress(@PathVariable Integer empId) {
        EmployeeAddressItem employeeAddressItem = restTemplate.getForObject( 
            serviceUrl + "/employee/address/{empId}", 
            EmployeeAddressItem.class, 
            empId);
        return ResponseEntity.ok( employeeAddressItem );
    }

    
    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> postEmployeeAddress(@RequestBody EmployeeAddressItem employeeAddressItem) {
        RequestEntity<EmployeeAddressItem> request = RequestEntity
            .post(URI.create(serviceUrl + "/employee/address/create"))
            .accept(MediaType.APPLICATION_JSON)
            .body(employeeAddressItem);
        return ResponseEntity.ok(restTemplate.exchange(request, EmployeeAddressItem.class).getBody());
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> putEmployeeAddress(@RequestBody EmployeeAddressItem employeeAddressItem) {
        RequestEntity<EmployeeAddressItem> request = RequestEntity
            .put(URI.create(serviceUrl + "/employee/address/update"))
            .accept(MediaType.APPLICATION_JSON)
            .body(employeeAddressItem);
        return ResponseEntity.ok(restTemplate.exchange(request, EmployeeAddressItem.class).getBody());
    }

    @DeleteMapping(value="/delete/{empId}", produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteEmployeeAddress(@PathVariable Integer empId) {
        restTemplate.delete( serviceUrl + "/employee/address/delete/{empId}", empId);
        return ResponseEntity.ok( HttpStatus.OK.name() );
    }
    
}
