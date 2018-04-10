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
import employeeapi.controller.EmployeeProjectController.EmployeeProjectClient;
import employeedetail.item.EmployeeDetailItem;
import employeeproject.item.EmployeeProjectItem;

@Service
public class EmployeeInfoService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeInfoService.class);
    @Autowired
    private EmployeeAddressClient employeeAddressClient;
    @Autowired
    private EmployeeDetailClient employeeDetailClient;
    @Autowired
    private EmployeeProjectClient employeeProjectClient;

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
    public CompletableFuture<List<EmployeeProjectItem>> getEmployeeProject(Integer empId) throws InterruptedException{
        logger.debug("Async getting employeeProject");
        ResponseEntity<List<EmployeeProjectItem>> employeeProjectResponse = employeeProjectClient.getEmployeeProjects(empId);
        if ( employeeProjectResponse.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error retrieving EmployeeProject: HttpStatus = " + employeeProjectResponse.getStatusCodeValue());
        }
        return CompletableFuture.completedFuture(employeeProjectResponse.getBody());
    }
}
