package employeeapi.controller;

import org.springframework.cloud.openfeign.FeignClient;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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

@RestController
@ExposesResourceFor(EmployeeAddressResource.class)
@RequestMapping("/employee/address")
public class EmployeeAddressController {
    private final EmployeeAddressClient employeeAddressClient;
    
    public EmployeeAddressController(
        EmployeeAddressClient employeeAddressClient
    ) {
        this.employeeAddressClient = employeeAddressClient;
    }

    @GetMapping(value="/", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getApi() {
        return ResponseEntity.ok("Nothing Yet");
    }
    
    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressResource> getEmployeeAddress(@PathVariable("empId") Integer empId) {
        ResponseEntity<EmployeeAddressItem> employeeAddressItemResponse = employeeAddressClient.getEmployeeAddress(empId);
        EmployeeAddressResourceAssembler assembler = new EmployeeAddressResourceAssembler();
        EmployeeAddressResource resource = assembler.toResource(employeeAddressItemResponse.getBody());
        
//        Resource<EmployeeAddressResource> resource = new Resource<>(employeeAddressItemResponse.getBody());
//        entityLinks.linkToSingleResource(EmployeeAddressItem.class, empId);
        // Add all the CRUD methods for this employee in hypermedia links
        // definitely need some way to generalize this
//        resource.add(linkTo(methodOn(EmployeeAddressController.class).getEmployeeAddress(empId)).withSelfRel());        
        resource.add(linkTo(methodOn(EmployeeAddressController.class).deleteEmployeeAddress(empId)).withRel("delete"));        
        resource.add(linkTo(methodOn(EmployeeAddressController.class).putEmployeeAddress(employeeAddressItemResponse.getBody())).withRel("update"));
        resource.add(linkTo(methodOn(EmployeeAddressController.class).postEmployeeAddress(employeeAddressItemResponse.getBody())).withRel("create"));        
        return ResponseEntity.ok(resource);
    }
    
    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> postEmployeeAddress(EmployeeAddressItem employeeAddressItem) {
        return employeeAddressClient.postEmployeeAddress(employeeAddressItem);
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> putEmployeeAddress(EmployeeAddressItem employeeAddressItem) {
        return employeeAddressClient.putEmployeeAddress(employeeAddressItem);
    }

    @DeleteMapping(value="/delete/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAddressItem> deleteEmployeeAddress(@PathVariable("empId") Integer empId) {
        return employeeAddressClient.deleteEmployeeAddress(empId);
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
