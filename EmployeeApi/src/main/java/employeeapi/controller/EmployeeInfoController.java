package employeeapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import employeeaddress.item.EmployeeAddressItem;
import employeeapi.controller.EmployeeAddressController.EmployeeAddressClient;
import employeeapi.controller.EmployeeDetailController.EmployeeDetailClient;
import employeeapi.controller.EmployeeProjectController.EmployeeProjectClient;
import employeedetail.item.EmployeeDetailItem;
import employeeinfo.item.EmployeeInfoItem;
import employeeproject.item.EmployeeProjectItem;

@RestController
@RequestMapping("/employee/info")
@ExposesResourceFor(EmployeeInfoItem.class)
public class EmployeeInfoController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeInfoController.class);
    @Autowired
    private EmployeeAddressClient employeeAddressClient;
    @Autowired
    private EmployeeDetailClient employeeDetailClient;
    @Autowired
    private EmployeeProjectClient employeeProjectClient;
    @Autowired
    private EntityLinks entityLinks;
    
    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<EmployeeInfoItem>> getEmployeeInfo(@PathVariable("empId") Integer empId) {
        logger.debug("EmployeeAddressController::getEmployeeAddress empId = " + empId);
        //TODO: Make these calls asynchronously
        ResponseEntity<EmployeeAddressItem> employeeAddressResponse = employeeAddressClient.getEmployeeAddress(empId);
        if ( employeeAddressResponse.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error retrieving EmployeeAddress: HttpStatus = " + employeeAddressResponse.getStatusCodeValue());
        }
        ResponseEntity<EmployeeDetailItem> employeeDetailResponse = employeeDetailClient.getEmployeeDetail(empId); 
        if ( employeeDetailResponse.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error retrieving EmployeeDetail: HttpStatus = " + employeeDetailResponse.getStatusCodeValue());
        }
        ResponseEntity<EmployeeProjectItem> employeeProjectResponse = employeeProjectClient.getEmployeeProject(empId); 
        if ( employeeProjectResponse.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error retrieving EmployeeProject: HttpStatus = " + employeeProjectResponse.getStatusCodeValue());
        }
        Resource<EmployeeInfoItem> resource = new Resource<>(
            new EmployeeInfoItem(
                employeeAddressResponse.getBody(), 
                employeeDetailResponse.getBody(), 
                employeeProjectResponse.getBody() 
            )
        );
        resource.add(this.entityLinks.linkToSingleResource(EmployeeAddressItem.class, empId));
        return ResponseEntity.ok(resource);
    }
}
