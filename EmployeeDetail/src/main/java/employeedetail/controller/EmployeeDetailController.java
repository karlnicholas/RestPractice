package employeedetail.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE, params = { "page", "size" })
    public ResponseEntity<List<SparseEmployeeDetailItem>> findAllBy(@RequestParam( "page" ) int page, @RequestParam( "size" ) int size) {
        return ResponseEntity.ok(repository.findAllBy(PageRequest.of(page, size)));
    }
    
    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> getEmployeeDetail(@PathVariable("empId") Integer empId) {
        logger.debug("empId = " + empId);
        return ResponseEntity.ok(repository.getOne(empId).asEmployeeDetailItem());
    }
    
    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> postEmployeeDetail(EmployeeDetailItem employeeDetailItem) {
        return ResponseEntity.ok(repository.save(new EmployeeDetail(employeeDetailItem)).asEmployeeDetailItem());
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> putEmployeeDetail(EmployeeDetailItem employeeDetailItem) {
        return ResponseEntity.ok(repository.save(new EmployeeDetail(employeeDetailItem)).asEmployeeDetailItem());
    }

    @DeleteMapping(value="/delete/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> deleteEmployeeDetail(@PathVariable("id") Integer empId) {
        EmployeeDetail employeeDetail = repository.getOne(empId);
        repository.delete(employeeDetail);        
        return ResponseEntity.ok(employeeDetail.asEmployeeDetailItem());
    }
}
