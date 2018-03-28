package employeeapi.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import employeeaddress.item.EmployeeAddressItem;
import employeeapi.EmployeeApiApplication;
import employeeapi.controller.EmployeeAddressController.EmployeeAddressClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { EmployeeApiApplication.class })
public class EmployeeAddressControllerTest {
    @MockBean
    // mock the FeignClient
    private EmployeeAddressClient employeeAddressClient;
    
    @Autowired
    // unit test the controller class
    private EmployeeAddressController employeeAddressController;

    @Before
    public void setup() {
        EmployeeAddressItem employeeAddressItem = new EmployeeAddressItem();
        employeeAddressItem.setAddress1("Address 1");
        employeeAddressItem.setAddress2("Address 2");
        employeeAddressItem.setAddress3("Address 3");
        employeeAddressItem.setAddress4("Address 4");
        when(employeeAddressClient.getEmployeeAddress(1)).thenReturn(ResponseEntity.ok(employeeAddressItem));
    }

    @Test
    public void testGet() {
        assertNotNull( employeeAddressController.getEmployeeAddress(1) );
    }
}