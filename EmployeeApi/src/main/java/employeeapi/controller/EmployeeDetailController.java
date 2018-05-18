package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
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

import employeeapi.resource.EmployeeDetailResource;
import employeeapi.resource.EmployeeDetailResourceAssembler;
import employeeapi.resource.SparseEmployeeDetailResource;
import employeeapi.resource.SparseEmployeeDetailResourceAssembler;
import employeedetail.item.EmployeeDetailItem;
import employeedetail.item.SparseEmployeeDetailItem;

@RestController
@RequestMapping("/employee/detail")
public class EmployeeDetailController {
    @Autowired
    private EmployeeDetailClient employeeDetailClient;
    @Autowired
    private EmployeeDetailResourceAssembler assembler;
    @Autowired
    private SparseEmployeeDetailResourceAssembler sparseAssembler;
    
    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getApi() {
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(EmployeeDetailController.class).getEmployees(null, null)).withRel("employees") );
        resource.add( linkTo(methodOn(EmployeeDetailController.class).getEmployeeDetail(null)).withRel("detail") );
        resource.add( linkTo(methodOn(EmployeeDetailController.class).postEmployeeDetail(null)).withRel("create"));
        resource.add( linkTo(methodOn(EmployeeDetailController.class).putEmployeeDetail(null)).withRel("update"));
        resource.add( linkTo(methodOn(EmployeeDetailController.class).deleteEmployeeDetail(null)).withRel("delete"));
        return ResponseEntity.ok(resource);
    }
    
    @GetMapping(value="/employees", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResources<SparseEmployeeDetailResource>> getEmployees(
            Pageable pageable, 
            PagedResourcesAssembler<SparseEmployeeDetailItem> pagedResourcesAssembler        
    ) {
        ResponseEntity<Page<SparseEmployeeDetailItem>> idsResponse = employeeDetailClient.findAllBy(pageable);                
        PagedResources<SparseEmployeeDetailResource> pagedResources = pagedResourcesAssembler.toResource(idsResponse.getBody(), sparseAssembler);        
        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
    }
    
    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailResource> getEmployeeDetail(@PathVariable Integer empId) {
        return ResponseEntity.ok( assembler.toResource(employeeDetailClient.getEmployeeDetail(empId).getBody()) );
    }
    
    @PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailResource> postEmployeeDetail(@RequestBody EmployeeDetailItem employeeDetailItem) {
        return ResponseEntity.ok( assembler.toResource(employeeDetailClient.postEmployeeDetail(employeeDetailItem).getBody()) );
    }

    @PutMapping(value="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetailResource> putEmployeeDetail(@RequestBody EmployeeDetailItem employeeDetailItem) {
        return ResponseEntity.ok( assembler.toResource(employeeDetailClient.putEmployeeDetail(employeeDetailItem).getBody()) );
    }

    @DeleteMapping(value="/delete/{empId}", produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteEmployeeDetail(@PathVariable Integer empId) {
        return ResponseEntity.ok( employeeDetailClient.deleteEmployeeDetail(empId).getBody() );
    }
    
    @FeignClient(name="EmployeeDetail")
    public interface EmployeeDetailClient {
        @GetMapping(value="/employee/detail/employees", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Page<SparseEmployeeDetailItem>> findAllBy(Pageable pageable);

        @GetMapping(value="/employee/detail/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeDetailItem> getEmployeeDetail(@PathVariable("empId") Integer empId);
        
        @PostMapping(value="/employee/detail/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeDetailItem> postEmployeeDetail(@RequestBody EmployeeDetailItem employeeDetailItem);

        @PutMapping(value="/employee/detail/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<EmployeeDetailItem> putEmployeeDetail(@RequestBody EmployeeDetailItem employeeDetailItem);

        @DeleteMapping(value="/employee/detail/delete/{empId}", produces=MediaType.TEXT_PLAIN_VALUE)
        public ResponseEntity<String> deleteEmployeeDetail(@PathVariable("empId") Integer empId);
    }
}
