package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.ResourceSupport;
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
import employeeapi.resource.EmployeeAddressResource;
import employeeapi.resource.EmployeeAddressResourceAssembler;

@RestController
@RequestMapping("/employee/address")
public class EmployeeAddressController {
    @Autowired
    private EmployeeAddressClient employeeAddressClient;
    @Autowired
    private EmployeeAddressResourceAssembler assembler;
    
    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getApi() {
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(EmployeeAddressController.class).getEmployeeAddress(null)).withRel("empId") );
        resource.add( linkTo(methodOn(EmployeeAddressController.class).postEmployeeAddress(null)).withRel("create"));
        resource.add( linkTo(methodOn(EmployeeAddressController.class).putEmployeeAddress(null)).withRel("update"));
        resource.add( linkTo(methodOn(EmployeeAddressController.class).deleteEmployeeAddress(null)).withRel("delete"));
        return ResponseEntity.ok(resource);
    }
    
    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressResource> getEmployeeAddress(@PathVariable("empId") Integer empId) {
        return ResponseEntity.ok( assembler.toResource(employeeAddressClient.getEmployeeAddress(empId).getBody()) );
    }
    
    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressResource> postEmployeeAddress(EmployeeAddressItem employeeAddressItem) {
        return ResponseEntity.ok( assembler.toResource(employeeAddressClient.postEmployeeAddress(employeeAddressItem).getBody()) );
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressResource> putEmployeeAddress(EmployeeAddressItem employeeAddressItem) {
        return ResponseEntity.ok( assembler.toResource(employeeAddressClient.putEmployeeAddress(employeeAddressItem).getBody()) );
    }

    @DeleteMapping(value="/delete/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressResource> deleteEmployeeAddress(@PathVariable("empId") Integer empId) {
        return ResponseEntity.ok( assembler.toResource(employeeAddressClient.deleteEmployeeAddress(empId).getBody()) );
    }
    
    // use a feign client to access the EmployeeAddress server so that it "loadbalances".
    // It makes log entries about contacting the EmployeeAddress server, 
    // but the load balancing doesn't seem to work well at this point.
    @FeignClient(name="EmployeeAddress")
    public interface EmployeeAddressClient {
        @GetMapping(value="/employee/address/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeAddressItem> getEmployeeAddress(@PathVariable("empId") Integer empId);
        
        @PostMapping(value="/employee/address/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeAddressItem> postEmployeeAddress(EmployeeAddressItem employeeAddressItem);

        @PutMapping(value="/employee/address/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeAddressItem> putEmployeeAddress(EmployeeAddressItem employeeAddressItem);

        @DeleteMapping(value="/employee/address/delete/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeAddressItem> deleteEmployeeAddress(@PathVariable("empId") Integer empId);
    }}
