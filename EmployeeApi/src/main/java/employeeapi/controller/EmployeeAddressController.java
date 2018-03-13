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

import employeeaddress.item.EmployeeAddressItem;

@RestController
public class EmployeeAddressController {
    @Autowired
    EmployeeAddressClient employeeAddressClient;
    
    @GetMapping(value="/employee/address/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> getEmployeeAddress(@PathVariable("id") Long id) {
        return employeeAddressClient.getEmployeeAddress(id);
    }
    
    @PostMapping(value="/employee/address/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> postEmployeeAddress(EmployeeAddressItem employeeAddressItem) {
        return employeeAddressClient.postEmployeeAddress(employeeAddressItem);
    }

    @PutMapping(value="/employee/address/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> putEmployeeAddress(EmployeeAddressItem employeeAddressItem) {
        return employeeAddressClient.putEmployeeAddress(employeeAddressItem);
    }

    @DeleteMapping(value="/employee/address/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> deleteEmployeeAddress(@PathVariable("id") Long id) {
        return employeeAddressClient.deleteEmployeeAddress(id);
    }
    
    @FeignClient(name="EmployeeAddress")
    interface EmployeeAddressClient {
        @GetMapping(value="/employee/address/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeAddressItem> getEmployeeAddress(@PathVariable("id") Long id);
        
        @PostMapping(value="/employee/address/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeAddressItem> postEmployeeAddress(EmployeeAddressItem employeeAddressItem);

        @PutMapping(value="/employee/address/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeAddressItem> putEmployeeAddress(EmployeeAddressItem employeeAddressItem);

        @DeleteMapping(value="/employee/address/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeAddressItem> deleteEmployeeAddress(@PathVariable("id") Long id);
    }}
