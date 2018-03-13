package employeeaddress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeaddress.model.EmployeeAddress;
import employeeaddress.service.EmployeeAddressRepository;
import employeeaddress.item.EmployeeAddressItem;

@RestController
public class EmployeeAddressController {
    @Autowired
    private EmployeeAddressRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeAddressController.class);
    
    @GetMapping(value="/employee/address/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> getEmployeeAddress(@PathVariable("id") Long id) {
        logger.debug("ID = " + id);
        return ResponseEntity.ok(repository.getOne(id).asEmployeeAddressItem());
    }
    
    @PostMapping(value="/employee/address/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> postEmployeeAddress(EmployeeAddressItem employeeAddressItem) {
        return ResponseEntity.ok(repository.save(new EmployeeAddress(employeeAddressItem)).asEmployeeAddressItem());
    }

    @PutMapping(value="/employee/address/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> putEmployeeAddress(EmployeeAddressItem employeeAddressItem) {
        return ResponseEntity.ok(repository.save(new EmployeeAddress(employeeAddressItem)).asEmployeeAddressItem());
    }

    @DeleteMapping(value="/employee/address/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> deleteEmployeeAddress(@PathVariable("id") Long id) {
        EmployeeAddress ed = repository.getOne(id);
        repository.delete(ed);        
        return ResponseEntity.ok(ed.asEmployeeAddressItem());
    }
}
