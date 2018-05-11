package employeeaddress;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import employeeaddress.controller.EmployeeAddressController;
import employeeaddress.model.EmployeeAddress;
import employeeaddress.service.EmployeeAddressRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeAddressController.class)
public class EmployeeAddressTest {
    
    @MockBean
    private EmployeeAddressRepository repository;
    
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private EmployeeAddress employeeAddress;
    
    @Before
    public void setup() {
        employeeAddress = new EmployeeAddress();
        employeeAddress.setEmpId(1);
        employeeAddress.setAddress1("Address 1");
        employeeAddress.setAddress2("Address 2");
        employeeAddress.setAddress3("Address 3");
        employeeAddress.setAddress4("Address 4");
        employeeAddress.setState("AZ");
        employeeAddress.setCountry("US");
    }

    @Test
    public void testGet() throws Exception {
        given(this.repository.getOne(1)).willReturn(employeeAddress);

        mvc.perform(
                get("/employee/address/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
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
            .andReturn();
                  
    }

    @Test
    public void testPost() throws Exception {
        given(this.repository.save(employeeAddress)).willReturn(employeeAddress);
        String s = objectMapper.writeValueAsString(employeeAddress);
        mvc.perform(
                post("/employee/address/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(s)
                .accept(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())    
            .andExpect(status().isCreated())  
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
            .andExpect(jsonPath("$.empId", is(1)))    
            .andExpect(jsonPath("$.address1", is("Address 1")))
            .andExpect(jsonPath("$.address2", is("Address 2")))
            .andExpect(jsonPath("$.address3", is("Address 3")))
            .andExpect(jsonPath("$.address4", is("Address 4")))
            .andExpect(jsonPath("$.state", is("AZ")))
            .andExpect(jsonPath("$.country", is("US")))
            .andReturn();
                  
    }
}
