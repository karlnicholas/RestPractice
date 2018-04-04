package employeeapi.controller;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import employeeaddress.item.EmployeeAddressItem;
import employeeapi.controller.EmployeeAddressController.EmployeeAddressClient;
import employeeapi.controller.EmployeeDetailController.EmployeeDetailClient;
import employeeapi.controller.EmployeeProjectController.EmployeeProjectClient;
import employeeapi.resource.EmployeeInfoResourceAssembler;
import employeedetail.item.EmployeeDetailItem;
import employeeproject.item.EmployeeProjectItem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeInfoController.class)
@Import(EmployeeInfoResourceAssembler.class)
public class EmployeeInfoControllerTest {

    @Autowired
    private MockMvc mvc;
    
    // mock the FeignClients
    @MockBean
    private EmployeeAddressClient employeeAddressClient;
    @MockBean
    private EmployeeDetailClient employeeDetailClient;
    @MockBean
    private EmployeeProjectClient employeeProjectClient;
    
    private EmployeeAddressItem employeeAddressItem;
    private EmployeeDetailItem employeeDetailItem;
    private EmployeeProjectItem employeeProjectItem;

    @Before
    public void setup() {
        employeeAddressItem = new EmployeeAddressItem();
        employeeAddressItem.setEmpId(767691);
        employeeAddressItem.setAddress1("Address 1");
        employeeAddressItem.setAddress2("Address 2");
        employeeAddressItem.setAddress3("Address 3");
        employeeAddressItem.setAddress4("Address 4");
        employeeAddressItem.setState("AZ");
        employeeAddressItem.setCountry("US");
        when(employeeAddressClient.getEmployeeAddress(767691)).thenReturn(ResponseEntity.ok(employeeAddressItem));
        when(employeeAddressClient.deleteEmployeeAddress(767691)).thenReturn(ResponseEntity.ok(employeeAddressItem));
        when(employeeAddressClient.postEmployeeAddress(employeeAddressItem)).thenReturn(ResponseEntity.ok(employeeAddressItem));
        when(employeeAddressClient.putEmployeeAddress(employeeAddressItem)).thenReturn(ResponseEntity.ok(employeeAddressItem));
        //
        employeeDetailItem = new EmployeeDetailItem();
        employeeDetailItem.setEmpId(767691);
        employeeDetailItem.setName("Karl Nicholas");
        employeeDetailItem.setRole("4");
        employeeDetailItem.setRoleDescription("Technical Analyst");
        employeeDetailItem.setSalary(new BigDecimal("100000.00"));
        when(employeeDetailClient.getEmployeeDetail(767691)).thenReturn(ResponseEntity.ok(employeeDetailItem));
        when(employeeDetailClient.deleteEmployeeDetail(767691)).thenReturn(ResponseEntity.ok(employeeDetailItem));
        when(employeeDetailClient.postEmployeeDetail(employeeDetailItem)).thenReturn(ResponseEntity.ok(employeeDetailItem));
        when(employeeDetailClient.putEmployeeDetail(employeeDetailItem)).thenReturn(ResponseEntity.ok(employeeDetailItem));
        //
        employeeProjectItem = new EmployeeProjectItem();
        employeeProjectItem.setEmpId(767691);
        employeeProjectItem.setProjectId("ALCON");
        employeeProjectItem.setProjectName("Nemo");
        employeeProjectItem.setTechstack("Java EE");
        when(employeeProjectClient.getEmployeeProject(767691)).thenReturn(ResponseEntity.ok(employeeProjectItem));
        when(employeeProjectClient.deleteEmployeeProject(767691)).thenReturn(ResponseEntity.ok(employeeProjectItem));
        when(employeeProjectClient.postEmployeeProject(employeeProjectItem)).thenReturn(ResponseEntity.ok(employeeProjectItem));
        when(employeeProjectClient.putEmployeeProject(employeeProjectItem)).thenReturn(ResponseEntity.ok(employeeProjectItem));
        
    }

    @Test
    public void testEmployee() throws Exception {
//        Arrays.asList(
//                new Employee(1L,"Frodo", "Baggins", "ring bearer"),
//                new Employee(2L,"Bilbo", "Baggins", "burglar")));

        mvc.perform(get("/employee/info/767691").accept(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())    
            .andExpect(status().isOk())
    
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
            .andReturn();
    /*
            .andExpect(jsonPath("$._embedded.employees[0].id", is(1)))
    
            .andExpect(jsonPath("$._embedded.employees[0].firstName", is("Frodo")))
    
            .andExpect(jsonPath("$._embedded.employees[0].lastName", is("Baggins")))
    
            .andExpect(jsonPath("$._embedded.employees[0].role", is("ring bearer")))
    
            .andExpect(jsonPath("$._embedded.employees[0]._links.self.href", is("http://localhost/employees/1")))
    
            .andExpect(jsonPath("$._embedded.employees[0]._links.employees.href", is("http://localhost/employees")))
    
            .andExpect(jsonPath("$._embedded.employees[1].id", is(2)))
    
            .andExpect(jsonPath("$._embedded.employees[1].firstName", is("Bilbo")))
    
            .andExpect(jsonPath("$._embedded.employees[1].lastName", is("Baggins")))
    
            .andExpect(jsonPath("$._embedded.employees[1].role", is("burglar")))
    
            .andExpect(jsonPath("$._embedded.employees[1]._links.self.href", is("http://localhost/employees/2")))
    
            .andExpect(jsonPath("$._embedded.employees[1]._links.employees.href", is("http://localhost/employees")))
    
            .andExpect(jsonPath("$._links.self.href", is("http://localhost/employees")))
    
            .andReturn();
*/                    
    }
/*    
    @Test
    public void testGet() {
        ResponseEntity<EmployeeInfoResource> getEmployeeInfo = employeeInfoController.getEmployeeInfo(1);
        assertNotNull(getEmployeeInfo);
        assertEquals("Status OK", HttpStatus.OK, getEmployeeInfo.getStatusCode() );
    }
    @Test
    // Test Hateoas links
    public void testGetLinks() {
        Link link = linkTo(EmployeeInfoController.class).slash(1).withSelfRel();
        assertThat(link.getRel(), is(Link.REL_SELF));
        assertThat(link.getHref(), endsWith("/employee/address/1"));        
    }
    @Test
    public void testPost() {
        assertNotNull( employeeInfoController.getEmployeeInfo(1) );
    }
    @Test
    public void testPut() {
        assertNotNull( employeeInfoController.getEmployeeInfo(1) );
    }
    @Test
    public void testDelete() {
        assertNotNull( employeeInfoController.getEmployeeInfo(1) );
    }
*/    
}