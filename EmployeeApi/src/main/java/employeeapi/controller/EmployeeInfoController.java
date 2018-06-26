package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import employeeaddress.item.EmployeeAddressItem;
import employeeapi.resource.EmployeeInfoResource;
import employeeapi.resource.EmployeeInfoResourceAssembler;
import employeeapi.resource.SparseEmployeeDetailResource;
import employeeapi.resource.SparseEmployeeDetailResourceAssembler;
import employeeapi.resource.SparseProjectResource;
import employeeapi.resource.SparseProjectResourceAssembler;
import employeeapi.service.EmployeeInfoService;
import employeedetail.item.EmployeeDetailItem;
import employeedetail.item.SparseEmployeeDetailItem;
import employeeutil.CustomPageImpl;
import project.item.ProjectItem;
import project.item.SparseProjectItem;

@RestController
@RequestMapping("/info")
public class EmployeeInfoController {    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeInfoController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EmployeeInfoService employeeInfoService;
    @Autowired
    private EmployeeInfoResourceAssembler assembler;
    @Autowired
    private SparseEmployeeDetailResourceAssembler sparseEmployeeAssembler;
    @Autowired
    private SparseProjectResourceAssembler sparseProjectAssembler;

    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getApi() {
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(EmployeeInfoController.class).getEmployees(null, null)).withRel("employees") );
        resource.add( linkTo(methodOn(EmployeeInfoController.class).getProjects(null, null)).withRel("projects") );
//        resource.add( linkTo(methodOn(EmployeeInfoController.class).getEmployeeInfo(null)).withRel("empId"));
        return ResponseEntity.ok(resource);
    }
    
    @GetMapping(value="/employees", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResources<SparseEmployeeDetailResource>> getEmployees(
            Pageable pageable, 
            PagedResourcesAssembler<SparseEmployeeDetailItem> pagedResourcesAssembler        
    ) {
        ResponseEntity<CustomPageImpl<SparseEmployeeDetailItem>> idsResponse = restTemplate.exchange(
                EmployeeDetailController.serviceUrl + "/employee/detail/employees",
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<SparseEmployeeDetailItem>>() {}, pageable);

        CustomPageImpl<SparseEmployeeDetailItem> items = idsResponse.getBody();

        List<SparseEmployeeDetailItem> list = new ArrayList<>(items.getContent());

        Page<SparseEmployeeDetailItem> page = new PageImpl<SparseEmployeeDetailItem>(list, pageable, 0);

        PagedResources<SparseEmployeeDetailResource> pagedResources 
           = pagedResourcesAssembler.toResource(page, sparseEmployeeAssembler);        

        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
    }
    
    @GetMapping(value="/projects", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResources<SparseProjectResource>> getProjects(
        Pageable pageable, 
        PagedResourcesAssembler<SparseProjectItem> pagedResourcesAssembler        
    ) {
        ResponseEntity<CustomPageImpl<SparseProjectItem>> idsResponse = restTemplate.exchange(
                EmployeeProjectController.serviceUrl + "/projects",
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<SparseProjectItem>>() {}, pageable);

        CustomPageImpl<SparseProjectItem> items = idsResponse.getBody();

        List<SparseProjectItem> list = new ArrayList<>(items.getContent());

        Page<SparseProjectItem> page = new PageImpl<SparseProjectItem>(list, pageable, 0);

        PagedResources<SparseProjectResource> pagedResources 
           = pagedResourcesAssembler.toResource(page, sparseProjectAssembler);        

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
