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

import employeeaddress.item.EmployeeAddressItem;
import employeeapi.controller.EmployeeAddressController.EmployeeAddressClient;
import employeeapi.resource.EmployeeAddressResourceAssembler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeAddressController.class)
@Import(EmployeeAddressResourceAssembler.class)
public class EmployeeAddressControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @MockBean
    // mock the FeignClient
    private EmployeeAddressClient employeeAddressClient;
    private EmployeeAddressItem employeeAddressItem;
    private String employeeAddressItemJSON;

    @Before
    public void setup() throws JsonProcessingException {
        employeeAddressItem = new EmployeeAddressItem();
        employeeAddressItem.setEmpId(1);
        employeeAddressItem.setAddress1("Address 1");
        employeeAddressItem.setAddress2("Address 2");
        employeeAddressItem.setAddress3("Address 3");
        employeeAddressItem.setAddress4("Address 4");
        employeeAddressItem.setState("AZ");
        employeeAddressItem.setCountry("US");
        employeeAddressItemJSON = objectMapper.writeValueAsString(employeeAddressItem);
    }

    private void testPackage(ResultActions r) throws Exception {
        r.andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
//      .andDo(print())    
        .andExpect(jsonPath("$.empId", is(1)))    
        .andExpect(jsonPath("$.address1", is("Address 1")))
        .andExpect(jsonPath("$.address2", is("Address 2")))
        .andExpect(jsonPath("$.address3", is("Address 3")))
        .andExpect(jsonPath("$.address4", is("Address 4")))
        .andExpect(jsonPath("$.state", is("AZ")))
        .andExpect(jsonPath("$.country", is("US")))
        .andExpect(jsonPath("$._links.self.href", is("http://localhost/employee/address/1")))
        .andExpect(jsonPath("$._links.delete.href", is("http://localhost/employee/address/delete/1")))
        .andExpect(jsonPath("$._links.update.href", is("http://localhost/employee/address/update")))
        .andExpect(jsonPath("$._links.create.href", is("http://localhost/employee/address/create")));
    }

    @Test
    public void testGet() throws Exception {
        when(employeeAddressClient.getEmployeeAddress(1)).thenReturn(ResponseEntity.ok(employeeAddressItem));
        testPackage(
            mvc.perform(get("/employee/address/1").accept(MediaType.APPLICATION_JSON_VALUE))
        );
    }

    @Test
    public void testCreate() throws Exception {
        when(employeeAddressClient.postEmployeeAddress(employeeAddressItem)).thenReturn(ResponseEntity.ok(employeeAddressItem));
        testPackage(
            mvc.perform(post("/employee/address/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeAddressItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }

    @Test
    public void testUpdate() throws Exception {
        when(employeeAddressClient.putEmployeeAddress(employeeAddressItem)).thenReturn(ResponseEntity.ok(employeeAddressItem));
        testPackage(
            mvc.perform(put("/employee/address/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeAddressItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }

    @Test
    public void testDlete() throws Exception {
        when(employeeAddressClient.deleteEmployeeAddress(1)).thenReturn(ResponseEntity.ok(HttpStatus.OK.name()));
        mvc.perform(delete("/employee/address/delete/1"))
//      .andDo(print())    
        .andExpect(status().isOk())
        .andExpect(content().string("OK"));    
    }    
}