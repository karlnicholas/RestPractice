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

import employeeaddress.view.EmployeeAddressView;

@RestController
public class EmployeeAddressController {
    @Autowired
    EmployeeAddressClient employeeAddressClient;
    
    @GetMapping(value="/employee/address/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressView> getEmployeeAddress(@PathVariable("id") Long id) {
        return employeeAddressClient.getEmployeeAddress(id);
    }
    
    @PostMapping(value="/employee/address/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressView> postEmployeeAddress(EmployeeAddressView employeeAddressView) {
        return employeeAddressClient.postEmployeeAddress(employeeAddressView);
    }

    @PutMapping(value="/employee/address/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressView> putEmployeeAddress(EmployeeAddressView employeeAddressView) {
        return employeeAddressClient.putEmployeeAddress(employeeAddressView);
    }

    @DeleteMapping(value="/employee/address/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressView> deleteEmployeeAddress(@PathVariable("id") Long id) {
        return employeeAddressClient.deleteEmployeeAddress(id);
    }
    
    @FeignClient(name="EmployeeAddress")
    interface EmployeeAddressClient {
        @GetMapping(value="/employee/address/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeAddressView> getEmployeeAddress(@PathVariable("id") Long id);
        
        @PostMapping(value="/employee/address/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeAddressView> postEmployeeAddress(EmployeeAddressView employeeAddressView);

        @PutMapping(value="/employee/address/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeAddressView> putEmployeeAddress(EmployeeAddressView employeeAddressView);

        @DeleteMapping(value="/employee/address/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeAddressView> deleteEmployeeAddress(@PathVariable("id") Long id);
    }    
}
