package employeeapi.controller;

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
import employeeapi.service.EmployeeInfoService;
import employeedetail.item.EmployeeDetailItem;
import employeedetail.item.SparseEmployeeDetailItem;
import employeeinfo.item.EmployeeInfoItem;
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

    @GetMapping(value="/employees", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SparseEmployeeDetailItem>> getEmployees(
            Pageable pageable        
    ) {
        ResponseEntity<CustomPageImpl<SparseEmployeeDetailItem>> idsResponse = restTemplate.exchange(
                EmployeeDetailController.serviceUrl + "/employee/detail/employees",
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<SparseEmployeeDetailItem>>() {}, pageable);

        CustomPageImpl<SparseEmployeeDetailItem> items = idsResponse.getBody();

        List<SparseEmployeeDetailItem> list = new ArrayList<>(items.getContent());

        Page<SparseEmployeeDetailItem> page = new PageImpl<SparseEmployeeDetailItem>(list, pageable, 0);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    
    @GetMapping(value="/projects", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SparseProjectItem>> getProjects(
        Pageable pageable        
    ) {
        ResponseEntity<CustomPageImpl<SparseProjectItem>> idsResponse = restTemplate.exchange(
                ProjectController.serviceUrl + "/project/projects",
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<SparseProjectItem>>() {}, pageable);

        CustomPageImpl<SparseProjectItem> items = idsResponse.getBody();

        List<SparseProjectItem> list = new ArrayList<>(items.getContent());

        Page<SparseProjectItem> page = new PageImpl<SparseProjectItem>(list, pageable, 0);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity<EmployeeInfoItem> getEmployeeInfo(@PathVariable("empId") Integer empId) {
        logger.debug("empId = " + empId);
        try {
            CompletableFuture<EmployeeAddressItem> employeeAddressFuture 
                = employeeInfoService.getEmployeeAddress(empId);
            CompletableFuture<EmployeeDetailItem> employeeDetailFuture 
                = employeeInfoService.getEmployeeDetail(empId);
            CompletableFuture<List<ProjectItem>> employeeProjectFuture 
                = employeeInfoService.getEmployeeProjects(empId);
            
            EmployeeAddressItem a = employeeAddressFuture.get();  
            EmployeeDetailItem d = employeeDetailFuture.get();
            List<ProjectItem> p = employeeProjectFuture.get();          
            EmployeeInfoItem employeeInfoItem = new EmployeeInfoItem(a, d, p);
            
            return ResponseEntity.ok(employeeInfoItem);
            
        } catch (InterruptedException | ExecutionException e) {
            logger.error( e.getMessage() );
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
        }
    }
    
}
