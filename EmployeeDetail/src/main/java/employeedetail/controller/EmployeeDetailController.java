package employeedetail.controller;

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

import employeedetail.model.EmployeeDetail;
import employeedetail.service.EmployeeDetailRepository;
import employeedetail.item.EmployeeDetailItem;

@RestController
public class EmployeeDetailController {
    @Autowired
    private EmployeeDetailRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailController.class);
    
    @GetMapping(value="/employee/detail/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> getEmployeeDetail(@PathVariable("id") Long id) {
        logger.debug("ID = " + id);
        return ResponseEntity.ok(repository.getOne(id).asEmployeeDetailItem());
    }
    
    @PostMapping(value="/employee/detail/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> postEmployeeDetail(EmployeeDetailItem employeeDetailItem) {
        return ResponseEntity.ok(repository.save(new EmployeeDetail(employeeDetailItem)).asEmployeeDetailItem());
    }

    @PutMapping(value="/employee/detail/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> putEmployeeDetail(EmployeeDetailItem employeeDetailItem) {
        return ResponseEntity.ok(repository.save(new EmployeeDetail(employeeDetailItem)).asEmployeeDetailItem());
    }

    @DeleteMapping(value="/employee/detail/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> deleteEmployeeDetail(@PathVariable("id") Long id) {
        EmployeeDetail employeeDetail = repository.getOne(id);
        repository.delete(employeeDetail);        
        return ResponseEntity.ok(employeeDetail.asEmployeeDetailItem());
    }
}
