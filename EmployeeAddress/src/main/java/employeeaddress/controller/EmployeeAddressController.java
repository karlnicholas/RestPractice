package employeeaddress.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeaddress.item.EmployeeAddressItem;
import employeeaddress.model.EmployeeAddress;
import employeeaddress.service.EmployeeAddressRepository;

@RestController
@RequestMapping("/employee/address")
public class EmployeeAddressController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeAddressController.class);
    private final EmployeeAddressRepository repository;

    public EmployeeAddressController( EmployeeAddressRepository repository ) {
        this.repository = repository;
    }
    
    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> getEmployeeAddress(@PathVariable("empId") Integer empId) {
        logger.debug("EmployeeAddressController::getEmployeeAddress empId = " + empId);
        return ResponseEntity.ok(repository.getOne(empId).asEmployeeAddressItem());
    }
    
    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> postEmployeeAddress(EmployeeAddressItem employeeAddressItem) {
        return new ResponseEntity<>(repository.save(new EmployeeAddress(employeeAddressItem)).asEmployeeAddressItem(), HttpStatus.CREATED);
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> putEmployeeAddress(EmployeeAddressItem employeeAddressItem) {
        return ResponseEntity.ok(repository.save(new EmployeeAddress(employeeAddressItem)).asEmployeeAddressItem());
    }

    @DeleteMapping(value="/delete/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> deleteEmployeeAddress(@PathVariable("empId") Integer empId) {
        EmployeeAddress ed = repository.getOne(empId);
        repository.delete(ed);        
        return ResponseEntity.ok(ed.asEmployeeAddressItem());
    }
}
