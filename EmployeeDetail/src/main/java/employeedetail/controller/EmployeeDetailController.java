package employeedetail.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import employeedetail.model.EmployeeDetail;
import employeedetail.service.EmployeeDetailRepository;
import employeedetail.item.EmployeeDetailItem;
import employeedetail.item.SparseEmployeeDetailItem;

@RestController
@RequestMapping("/employee/detail")
public class EmployeeDetailController {
    @Autowired
    private EmployeeDetailRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailController.class);
    
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SparseEmployeeDetailItem>> findAllBy(Pageable pageable) {
        return ResponseEntity.ok(repository.findAllBy(pageable));
    }
    
    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> getEmployeeDetail(@PathVariable Integer empId) {
        logger.debug("empId = " + empId);
        return ResponseEntity.ok(repository.getOne(empId).asEmployeeDetailItem());
    }
    
    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> postEmployeeDetail(@RequestBody EmployeeDetailItem employeeDetailItem) {
        return ResponseEntity.ok(
            repository.save(
                new EmployeeDetail(employeeDetailItem)
            ).asEmployeeDetailItem()
        );
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> putEmployeeDetail(@RequestBody EmployeeDetailItem employeeDetailItem) {
        return ResponseEntity.ok(
            repository.save(
                new EmployeeDetail(employeeDetailItem)
            ).asEmployeeDetailItem()
        );
    }

    @DeleteMapping(value="/delete/{empId}")
    public ResponseEntity<String> deleteEmployeeDetail(@PathVariable Integer empId) {
        repository.deleteById(empId);        
        return ResponseEntity.ok(HttpStatus.OK.toString());
    }
}
