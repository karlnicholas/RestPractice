package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import employeeapi.resource.EmployeeDetailResource;
import employeeapi.resource.EmployeeDetailResourceAssembler;
import employeedetail.item.EmployeeDetailItem;

@RestController
@RequestMapping("/employee/detail")
public class EmployeeDetailController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EmployeeDetailResourceAssembler assembler;
    
    protected static final String serviceUrl = "http://EmployeeDetail"; // EmployeeAddress is the name of the microservice we're calling

    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getApi() {
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(EmployeeDetailController.class).getEmployeeDetail(null)).withRel("detail") );
        resource.add( linkTo(methodOn(EmployeeDetailController.class).postEmployeeDetail(null)).withRel("create"));
        resource.add( linkTo(methodOn(EmployeeDetailController.class).putEmployeeDetail(null)).withRel("update"));
        resource.add( linkTo(methodOn(EmployeeDetailController.class).deleteEmployeeDetail(null)).withRel("delete"));
        return ResponseEntity.ok(resource);
    }
    
    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailResource> getEmployeeDetail(@PathVariable Integer empId) {
        EmployeeDetailItem employeeDetailItem = restTemplate.getForObject( 
                serviceUrl + "/employee/detail/{empId}", 
                EmployeeDetailItem.class, 
                empId);
        return ResponseEntity.ok( assembler.toResource(employeeDetailItem) );
    }
    
    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailResource> postEmployeeDetail(@RequestBody EmployeeDetailItem employeeDetailItem) {
        RequestEntity<EmployeeDetailItem> request = RequestEntity
            .post(URI.create(serviceUrl + "/employee/detail/create"))
            .accept(MediaType.APPLICATION_JSON)
            .body(employeeDetailItem);
        return ResponseEntity.ok( 
            assembler.toResource(restTemplate.exchange(request, EmployeeDetailItem.class).getBody()) 
        );
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailResource> putEmployeeDetail(@RequestBody EmployeeDetailItem employeeDetailItem) {
        RequestEntity<EmployeeDetailItem> request = RequestEntity
            .put(URI.create(serviceUrl + "/employee/detail/update"))
            .accept(MediaType.APPLICATION_JSON)
            .body(employeeDetailItem);
        return ResponseEntity.ok( 
            assembler.toResource(restTemplate.exchange(request, EmployeeDetailItem.class).getBody()) 
        );
    }

    @DeleteMapping(value="/delete/{empId}", produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteEmployeeDetail(@PathVariable Integer empId) {
        restTemplate.delete(serviceUrl + "/employee/detail/delete/{empId}", empId);
        return ResponseEntity.ok( HttpStatus.OK.name() );
    }
    
}
