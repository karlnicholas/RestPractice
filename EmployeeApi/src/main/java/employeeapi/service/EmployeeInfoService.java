package employeeapi.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import employeeaddress.item.EmployeeAddressItem;
import employeeapi.controller.EmployeeAddressController.EmployeeAddressClient;
import employeeapi.controller.EmployeeDetailController.EmployeeDetailClient;
import employeeapi.controller.EmployeeProjectsController.EmployeeProjectsClient;
import employeedetail.item.EmployeeDetailItem;
import project.item.ProjectItem;

@Service
public class EmployeeInfoService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeInfoService.class);
    @Autowired
    private EmployeeAddressClient employeeAddressClient;
    @Autowired
    private EmployeeDetailClient employeeDetailClient;
    @Autowired
    private EmployeeProjectsClient employeeProjectsClient;

    @Async
    public CompletableFuture<EmployeeAddressItem> getEmployeeAddress(Integer empId) throws InterruptedException{
        logger.debug("Async getting employeeAddress");
        ResponseEntity<EmployeeAddressItem> employeeAddressResponse = employeeAddressClient.getEmployeeAddress(empId);
        if ( employeeAddressResponse.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error retrieving EmployeeAddress: HttpStatus = " + employeeAddressResponse.getStatusCodeValue());
        }
        return CompletableFuture.completedFuture(employeeAddressResponse.getBody());
    }
    
    @Async
    public CompletableFuture<EmployeeDetailItem> getEmployeeDetail(Integer empId) throws InterruptedException{
        logger.debug("Async getting employeeDetail");
        ResponseEntity<EmployeeDetailItem> employeeDetailResponse = employeeDetailClient.getEmployeeDetail(empId);
        if ( employeeDetailResponse.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error retrieving EmployeeDetail: HttpStatus = " + employeeDetailResponse.getStatusCodeValue());
        }
        return CompletableFuture.completedFuture(employeeDetailResponse.getBody());
    }

    @Async
    public CompletableFuture<List<ProjectItem>> getEmployeeProjects(Integer empId) throws InterruptedException{
        logger.debug("Async getting employeeProject");
        ResponseEntity<List<ProjectItem>> employeeProjectsResponse = employeeProjectsClient.getEmployeeProjectsFull(empId);
        if ( employeeProjectsResponse.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException("Error retrieving EmployeeProject: HttpStatus = " + employeeProjectsResponse.getStatusCodeValue());
        }
        return CompletableFuture.completedFuture(employeeProjectsResponse.getBody());
    }
}
