package employeeapi.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import employeeaddress.item.EmployeeAddressItem;
import employeeapi.controller.EmployeeAddressController.EmployeeAddressClient;
import employeeapi.controller.EmployeeDetailController.EmployeeDetailClient;
import employeeapi.controller.EmployeeProjectsController.EmployeeProjectsClient;
import employeeapi.resource.EmployeeInfoResourceAssembler;
import employeeapi.resource.SparseEmployeeDetailResourceAssembler;
import employeeapi.service.EmployeeInfoService;
import employeedetail.item.EmployeeDetailItem;
import employeedetail.item.SparseEmployeeDetailItem;
import project.item.ProjectItem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeInfoController.class)
@Import({
    EmployeeInfoResourceAssembler.class, 
    EmployeeInfoService.class, 
    SparseEmployeeDetailResourceAssembler.class,
})
public class EmployeeInfoControllerTest {

    @Autowired
    private MockMvc mvc;
    
    // mock the FeignClients
    @MockBean
    private EmployeeAddressClient employeeAddressClient;
    @MockBean
    private EmployeeDetailClient employeeDetailClient;
    @MockBean
    private EmployeeProjectsClient employeeProjectsClient;
    
    
    private EmployeeAddressItem employeeAddressItem;
    private EmployeeDetailItem employeeDetailItem;
    private ProjectItem projectItem;
    private List<ProjectItem> projectItems;
     

    @Before
    public void setup() {
        employeeAddressItem = new EmployeeAddressItem();
        employeeAddressItem.setEmpId(1);
        employeeAddressItem.setAddress1("Address 1");
        employeeAddressItem.setAddress2("Address 2");
        employeeAddressItem.setAddress3("Address 3");
        employeeAddressItem.setAddress4("Address 4");
        employeeAddressItem.setState("AZ");
        employeeAddressItem.setCountry("US");
        //
        employeeDetailItem = new EmployeeDetailItem();
        employeeDetailItem.setEmpId(1);
        employeeDetailItem.setName("Karl Nicholas");
        employeeDetailItem.setRole("4");
        employeeDetailItem.setRoleDescription("Technical Analyst");
        employeeDetailItem.setSalary(new BigDecimal("100000.00"));
//        sparseEmployeeDetailItem = new SparseEmployeeDetailItem(1, "Karl");
//        sparseEmployeeDetailItems = Page.empty();
//        sparseEmployeeDetailItems.add(sparseEmployeeDetailItem);
        //
        projectItems = new ArrayList<>();
        projectItem = new ProjectItem(1, "Test Project Name", "Test Project Techstack");
        projectItems.add(projectItem);
        
    }

    @Test
    public void testEmployee() throws Exception {
        when(employeeAddressClient.getEmployeeAddress(1)).thenReturn(ResponseEntity.ok(employeeAddressItem));
        when(employeeDetailClient.getEmployeeDetail(1)).thenReturn(ResponseEntity.ok(employeeDetailItem));
        when(employeeProjectsClient.getEmployeeProjectsFull(1)).thenReturn(ResponseEntity.ok(projectItems));

        mvc.perform(get("/employee/info/1").accept(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())    
            .andExpect(status().isOk())    
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
            .andExpect(jsonPath("$.empId", is(1)))  
            .andExpect(jsonPath("$.address1", is("Address 1")))
            .andExpect(jsonPath("$.address2", is("Address 2")))
            .andExpect(jsonPath("$.address3", is("Address 3")))
            .andExpect(jsonPath("$.address4", is("Address 4")))
            .andExpect(jsonPath("$.state", is("AZ")))
            .andExpect(jsonPath("$.country", is("US")))
            .andExpect(jsonPath("$.name", is("Karl Nicholas")))
            .andExpect(jsonPath("$.role", is("4")))
            .andExpect(jsonPath("$.salary", is(100000.00)))
            .andExpect(jsonPath("$.roleDescription", is("Technical Analyst")))
            .andExpect(jsonPath("$.projects[0].projectId", is(1)))
            .andExpect(jsonPath("$.projects[0].projectName", is("Test Project Name")))
            .andExpect(jsonPath("$.projects[0].techstack", is("Test Project Techstack")))
            .andExpect(jsonPath("$._links.self.href", is("http://localhost/employee/info/1")))
            .andReturn();
    }

}