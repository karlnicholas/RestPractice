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
import employeeaddress.view.EmployeeAddressView;

@RestController
public class EmployeeAddressController {
    @Autowired
    private EmployeeAddressRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeAddressController.class);
    
    @GetMapping(value="/employee/address/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressView> getEmployeeAddress(@PathVariable("id") Long id) {
        logger.debug("ID = " + id);
        return ResponseEntity.ok(repository.getOne(id).asEmployeeAddressView());
    }
    
    @PostMapping(value="/employee/address/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressView> postEmployeeAddress(EmployeeAddressView employeeAddressView) {
        return ResponseEntity.ok(repository.save(new EmployeeAddress(employeeAddressView)).asEmployeeAddressView());
    }

    @PutMapping(value="/employee/address/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressView> putEmployeeAddress(EmployeeAddressView employeeAddressView) {
        return ResponseEntity.ok(repository.save(new EmployeeAddress(employeeAddressView)).asEmployeeAddressView());
    }

    @DeleteMapping(value="/employee/address/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressView> deleteEmployeeAddress(@PathVariable("id") Long id) {
        EmployeeAddress ed = repository.getOne(id);
        repository.delete(ed);        
        return ResponseEntity.ok(ed.asEmployeeAddressView());
    }
}
