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
import employeeapi.resource.EmployeeAddressResourceAssembler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeAddressController.class)
@Import(EmployeeAddressResourceAssembler.class)
public class EmployeeAddressControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @MockBean
    // mock the FeignClient
    private EmployeeAddressClient employeeAddressClient;
    
    private EmployeeAddressItem employeeAddressItem;

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
        when(employeeAddressClient.getEmployeeAddress(1)).thenReturn(ResponseEntity.ok(employeeAddressItem));
        when(employeeAddressClient.deleteEmployeeAddress(1)).thenReturn(ResponseEntity.ok(employeeAddressItem));
        when(employeeAddressClient.postEmployeeAddress(employeeAddressItem)).thenReturn(ResponseEntity.ok(employeeAddressItem));
        when(employeeAddressClient.putEmployeeAddress(employeeAddressItem)).thenReturn(ResponseEntity.ok(employeeAddressItem));
    }

    @Test
    public void testEmployee() throws Exception {
//        Arrays.asList(
//                new Employee(1L,"Frodo", "Baggins", "ring bearer"),
//                new Employee(2L,"Bilbo", "Baggins", "burglar")));

        mvc.perform(get("/employee/address/1").accept(MediaType.APPLICATION_JSON_VALUE))
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
        ResponseEntity<EmployeeAddressResource> getEmployeeAddress = employeeAddressController.getEmployeeAddress(1);
        assertNotNull(getEmployeeAddress);
        assertEquals("Status OK", HttpStatus.OK, getEmployeeAddress.getStatusCode() );
    }
    @Test
    // Test Hateoas links
    public void testGetLinks() {
        Link link = linkTo(EmployeeAddressController.class).slash(1).withSelfRel();
        assertThat(link.getRel(), is(Link.REL_SELF));
        assertThat(link.getHref(), endsWith("/employee/address/1"));        
    }
    @Test
    public void testPost() {
        assertNotNull( employeeAddressController.getEmployeeAddress(1) );
    }
    @Test
    public void testPut() {
        assertNotNull( employeeAddressController.getEmployeeAddress(1) );
    }
    @Test
    public void testDelete() {
        assertNotNull( employeeAddressController.getEmployeeAddress(1) );
    }
*/    
}