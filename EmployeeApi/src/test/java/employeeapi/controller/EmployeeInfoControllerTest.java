package employeeapi.controller;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import employeeapi.resource.SparseEmployeeDetailResourceAssembler;
import employeeapi.service.EmployeeInfoService;
import employeedetail.item.EmployeeDetailItem;
import employeedetail.item.SparseEmployeeDetailItem;
import employeeproject.item.EmployeeProjectItem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
    private EmployeeProjectClient employeeProjectClient;
    
    private EmployeeAddressItem employeeAddressItem;
    private EmployeeDetailItem employeeDetailItem;
//    private SparseEmployeeDetailItem sparseEmployeeDetailItem;
    private Page<SparseEmployeeDetailItem> sparseEmployeeDetailItems;
    private EmployeeProjectItem employeeProjectItem;
    private List<EmployeeProjectItem> employeeProjectItems;

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
        sparseEmployeeDetailItems = Page.empty();
//        sparseEmployeeDetailItems.add(sparseEmployeeDetailItem);
        //
        employeeProjectItems = new ArrayList<>();
        employeeProjectItem = new EmployeeProjectItem();
        employeeProjectItem.setEmpId(1);
        employeeProjectItem.setProjectId(1);
        employeeProjectItems.add(employeeProjectItem);
        
    }

    @Test
    public void testEmployee() throws Exception {
        when(employeeAddressClient.getEmployeeAddress(1)).thenReturn(ResponseEntity.ok(employeeAddressItem));
        when(employeeDetailClient.getEmployeeDetail(1)).thenReturn(ResponseEntity.ok(employeeDetailItem));
        when(employeeProjectClient.getEmployeeProjects(1)).thenReturn(ResponseEntity.ok(employeeProjectItems));

        mvc.perform(get("/employee/info/1").accept(MediaType.APPLICATION_JSON_VALUE))
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

    @Test
    public void testEmployeeList() throws Exception {
        when(employeeDetailClient.findAllBy(PageRequest.of(0, 20))).thenReturn(ResponseEntity.ok(sparseEmployeeDetailItems));
        mvc.perform(get("/employee/info?page=0&size=20").accept(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())    
            .andExpect(status().isOk())
    
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
            .andReturn();
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