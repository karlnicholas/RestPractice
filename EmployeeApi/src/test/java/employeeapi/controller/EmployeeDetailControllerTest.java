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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import employeeapi.controller.EmployeeDetailController.EmployeeDetailClient;
import employeeapi.resource.EmployeeDetailResourceAssembler;
import employeedetail.item.EmployeeDetailItem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeDetailController.class)
@Import(EmployeeDetailResourceAssembler.class)
public class EmployeeDetailControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @MockBean
    // mock the FeignClient
    private EmployeeDetailClient employeeDetailClient;
    private EmployeeDetailItem employeeDetailItem;
    private String employeeDetailItemJSON;

    @Before
    public void setup() throws JsonProcessingException {
        employeeDetailItem = new EmployeeDetailItem();
        employeeDetailItem.setEmpId(1);
        employeeDetailItem.setName("Karl");
        employeeDetailItem.setSalary(new BigDecimal("100000.00"));
        employeeDetailItem.setRole("Technical Analyst");
        employeeDetailItem.setRoleDescription("Analyze Technicals");
        employeeDetailItemJSON = objectMapper.writeValueAsString(employeeDetailItem);
    }

    private void testPackage(ResultActions r) throws Exception {
        r.andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
//      .andDo(print())    
        .andExpect(jsonPath("$.empId", is(1)))    
        .andExpect(jsonPath("$.name", is("Karl")))
        .andExpect(jsonPath("$.salary", is("100000.00")))
        .andExpect(jsonPath("$.role", is("Technical Analyst")))
        .andExpect(jsonPath("$.roleDescription", is("Analyze Technicals")))
        .andExpect(jsonPath("$._links.self.href", is("http://localhost/employee/detail/1")))
        .andExpect(jsonPath("$._links.delete.href", is("http://localhost/employee/detail/delete/1")))
        .andExpect(jsonPath("$._links.update.href", is("http://localhost/employee/detail/update")))
        .andExpect(jsonPath("$._links.create.href", is("http://localhost/employee/detail/create")));
    }

    @Test
    public void testGet() throws Exception {
        when(employeeDetailClient.getEmployeeDetail(1)).thenReturn(ResponseEntity.ok(employeeDetailItem));
        testPackage(
            mvc.perform(get("/employee/detail/1").accept(MediaType.APPLICATION_JSON_VALUE))
        );
    }

    @Test
    public void testCreate() throws Exception {
        when(employeeDetailClient.postEmployeeDetail(employeeDetailItem)).thenReturn(ResponseEntity.ok(employeeDetailItem));
        testPackage(
            mvc.perform(post("/employee/detail/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeDetailItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }

    @Test
    public void testUpdate() throws Exception {
        when(employeeDetailClient.putEmployeeDetail(employeeDetailItem)).thenReturn(ResponseEntity.ok(employeeDetailItem));
        testPackage(
            mvc.perform(put("/employee/detail/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeDetailItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }

    @Test
    public void testDlete() throws Exception {
        when(employeeDetailClient.deleteEmployeeDetail(1)).thenReturn(ResponseEntity.ok(HttpStatus.OK.toString()));
        mvc.perform(delete("/employee/detail/delete/1"))
//      .andDo(print())    
        .andExpect(status().isOk())
        .andReturn();
    }    
}