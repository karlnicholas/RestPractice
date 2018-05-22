package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeaddress.item.EmployeeAddressItem;
import employeeapi.resource.EmployeeInfoResource;
import employeeapi.resource.EmployeeInfoResourceAssembler;
import employeeapi.service.EmployeeInfoService;
import employeedetail.item.EmployeeDetailItem;
import project.item.ProjectItem;

@RestController
@RequestMapping("/employee/info")
public class EmployeeInfoController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeInfoController.class);
    @Autowired
    private EmployeeInfoService employeeInfoService;
    @Autowired
    private EmployeeInfoResourceAssembler assembler;
    
    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getApi() {
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(EmployeeInfoController.class).getEmployeeInfo(null)).withRel("empId"));
        return ResponseEntity.ok(resource);
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
/*           
            https://stackoverflow.com/questions/45490316/completablefuture-join-vs-get 
            CompletableFuture.allOf(
                    employeeAddressFuture,
                    employeeDetailFuture, 
                    employeeProjectFuture
                    ).join();
*/
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
