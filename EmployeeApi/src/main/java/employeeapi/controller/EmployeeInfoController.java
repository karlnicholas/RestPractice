package employeeapi.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeaddress.item.EmployeeAddressItem;
import employeeapi.controller.EmployeeDetailController.EmployeeDetailClient;
import employeeapi.resource.EmployeeInfoResource;
import employeeapi.resource.EmployeeInfoResourceAssembler;
import employeeapi.resource.SparseEmployeeDetailResource;
import employeeapi.resource.SparseEmployeeDetailResourceAssembler;
import employeeapi.service.EmployeeInfoService;
import employeedetail.item.EmployeeDetailItem;
import employeedetail.item.SparseEmployeeDetailItem;
import project.item.ProjectItem;

@RestController
@RequestMapping("/employee/info")
public class EmployeeInfoController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeInfoController.class);
    @Autowired
    private EmployeeInfoService employeeInfoService;
    @Autowired
    private EmployeeInfoResourceAssembler assembler;
    @Autowired
    private SparseEmployeeDetailResourceAssembler sparseAssembler;
    @Autowired
    private EmployeeDetailClient employeeDetailClient;
    
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResources<SparseEmployeeDetailResource>> findAllBy(
        Pageable pageable, 
        PagedResourcesAssembler<SparseEmployeeDetailItem> pagedResourcesAssembler        
    ) {
        ResponseEntity<Page<SparseEmployeeDetailItem>> idsResponse = employeeDetailClient.findAllBy(pageable);                
        PagedResources<SparseEmployeeDetailResource> pagedResources = pagedResourcesAssembler.toResource(idsResponse.getBody(), sparseAssembler);        
        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
    }

    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeInfoResource> getEmployeeInfo(@PathVariable("empId") Integer empId) {
        logger.debug("empId = " + empId);
        try {
            CompletableFuture<EmployeeAddressItem> employeeAddressFuture 
                = employeeInfoService.getEmployeeAddress(empId);
            CompletableFuture<EmployeeDetailItem> employeeDetailFuture 
                = employeeInfoService.getEmployeeDetail(empId);
            CompletableFuture<List<ProjectItem>> employeeProjectFuture 
                = employeeInfoService.getEmployeeProjects(empId);
            
            EmployeeInfoResource employeeInfo = new EmployeeInfoResource(
                employeeAddressFuture.get(),  
                employeeDetailFuture.get(), 
                employeeProjectFuture.get()
            );
            
            return ResponseEntity.ok(assembler.toResource(employeeInfo));
            
        } catch (InterruptedException | ExecutionException e) {
            logger.error( e.getMessage() );
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
        }
    }
}
