package employeeapi.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import employeeaddress.item.EmployeeAddressItem;
import employeeapi.controller.EmployeeAddressController;
import employeeapi.controller.EmployeeDetailController;
import employeeapi.controller.EmployeeProjectsController;
import employeedetail.item.EmployeeDetailItem;
import project.item.ProjectItem;

@Service
public class EmployeeInfoService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeInfoService.class);
    @Autowired
    private RestTemplate restTemplateAddress;
    @Autowired
    private RestTemplate restTemplateDetail;
    @Autowired
    private RestTemplate restTemplateProject;

    @Async
    public CompletableFuture<EmployeeAddressItem> getEmployeeAddress(Integer empId) throws InterruptedException{
        logger.debug("Async getting employeeAddress");
        ResponseEntity<EmployeeAddressItem> employeeAddressResponse = restTemplateAddress.getForEntity( 
                EmployeeAddressController.serviceUrl + "/employee/address/{empId}", 
                EmployeeAddressItem.class, 
                empId);
        if ( employeeAddressResponse.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException("Error retrieving EmployeeAddress: HttpStatus = " + employeeAddressResponse.getStatusCodeValue());
        }
        return CompletableFuture.completedFuture(employeeAddressResponse.getBody());
    }
    
    @Async
    public CompletableFuture<EmployeeDetailItem> getEmployeeDetail(Integer empId) throws InterruptedException{
        logger.debug("Async getting employeeDetail");
        ResponseEntity<EmployeeDetailItem> employeeDetailResponse = restTemplateDetail.getForEntity( 
                EmployeeDetailController.serviceUrl + "/employee/detail/{empId}", 
                EmployeeDetailItem.class, 
                empId);
        if ( employeeDetailResponse.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException("Error retrieving EmployeeDetail: HttpStatus = " + employeeDetailResponse.getStatusCodeValue());
        }
        return CompletableFuture.completedFuture(employeeDetailResponse.getBody());
    }

    @Async
    public CompletableFuture<List<ProjectItem>> getEmployeeProjects(Integer empId) throws InterruptedException{
        logger.debug("Async getting employeeProject");
        ResponseEntity<List<ProjectItem>> employeeProjectsResponse = restTemplateProject.exchange(
                EmployeeProjectsController.serviceUrl + "/employee/projects/{empId}",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<ProjectItem>>() {}, empId);
        if ( employeeProjectsResponse.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException("Error retrieving EmployeeProject: HttpStatus = " + employeeProjectsResponse.getStatusCodeValue());
        }
        return CompletableFuture.completedFuture(employeeProjectsResponse.getBody());
    }    
}
