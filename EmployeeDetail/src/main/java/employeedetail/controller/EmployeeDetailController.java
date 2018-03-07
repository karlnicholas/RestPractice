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
import employeedetail.view.EmployeeDetailView;

@RestController
public class EmployeeDetailController {
    @Autowired
    private EmployeeDetailRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailController.class);
    
    @GetMapping(value="/employee/detail/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailView> getEmployeeDetail(@PathVariable("id") Long id) {
        logger.debug("ID = " + id);
        return ResponseEntity.ok(new EmployeeDetailView(repository.getOne(id)));
    }
    
    @PostMapping(value="/employee/detail/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailView> postEmployeeDetail(EmployeeDetailView employeeDetailView) {
        return ResponseEntity.ok(new EmployeeDetailView(repository.save(employeeDetailView.asEmployeeDetail())));
    }

    @PutMapping(value="/employee/detail/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailView> putEmployeeDetail(EmployeeDetailView employeeDetailView) {
        return ResponseEntity.ok(new EmployeeDetailView(repository.save(employeeDetailView.asEmployeeDetail())));
    }

    @DeleteMapping(value="/employee/detail/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailView> deleteEmployeeDetail(@PathVariable("id") Long id) {
        EmployeeDetail ed = repository.getOne(id);
        repository.delete(ed);        
        return ResponseEntity.ok(new EmployeeDetailView(ed));
    }
}
