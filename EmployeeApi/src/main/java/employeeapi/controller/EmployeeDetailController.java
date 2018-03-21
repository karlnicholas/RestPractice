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

import employeedetail.item.EmployeeDetailItem;

@RestController
public class EmployeeDetailController {
    @Autowired
    private EmployeeDetailClient employeeDetailClient;
    
    @GetMapping(value="/employee/detail/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> getEmployeeDetail(@PathVariable("empId") Integer empId) {
        return employeeDetailClient.getEmployeeDetail(empId);
    }
    
    @PostMapping(value="/employee/detail/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> postEmployeeDetail(EmployeeDetailItem employeeDetailItem) {
        return employeeDetailClient.postEmployeeDetail(employeeDetailItem);
    }

    @PutMapping(value="/employee/detail/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> putEmployeeDetail(EmployeeDetailItem employeeDetailItem) {
        return employeeDetailClient.putEmployeeDetail(employeeDetailItem);
    }

    @DeleteMapping(value="/employee/detail/delete/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailItem> deleteEmployeeDetail(@PathVariable("empId") Integer empId) {
        return employeeDetailClient.deleteEmployeeDetail(empId);
    }
    
    @FeignClient(name="EmployeeDetail")
    public interface EmployeeDetailClient {
        @GetMapping(value="/employee/detail/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeDetailItem> getEmployeeDetail(@PathVariable("empId") Integer empId);
        
        @PostMapping(value="/employee/detail/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeDetailItem> postEmployeeDetail(EmployeeDetailItem employeeDetailItem);

        @PutMapping(value="/employee/detail/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeDetailItem> putEmployeeDetail(EmployeeDetailItem employeeDetailItem);

        @DeleteMapping(value="/employee/detail/delete/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeDetailItem> deleteEmployeeDetail(@PathVariable("empId") Integer empId);
    }
}
