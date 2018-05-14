package employeedetail;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

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

import employeedetail.controller.EmployeeDetailController;
import employeedetail.model.EmployeeDetail;
import employeedetail.service.EmployeeDetailRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeDetailController.class)
public class EmployeeDetailTest {
    
    @MockBean
    private EmployeeDetailRepository repository;
    
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private EmployeeDetail employeeDetail;
    
    @Before
    public void setup() {
        employeeDetail = new EmployeeDetail();
        employeeDetail.setEmpId(1);
        employeeDetail.setName("Karl");
        employeeDetail.setRole("Technical Analyst");
        employeeDetail.setRoleDescription("Analyze, Design, Code, and Deploy Enterprise Applications");
        employeeDetail.setSalary(new BigDecimal("100000.00"));
    }

    @Test
    public void testGet() throws Exception {
        given(this.repository.getOne(1)).willReturn(employeeDetail);

        mvc.perform(
                get("/employee/detail/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
//            .andDo(print())    
            .andExpect(status().isOk())  
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
            .andExpect(jsonPath("$.empId", is(1)))    
            .andExpect(jsonPath("$.name", is("Karl")))
            .andExpect(jsonPath("$.role", is("Technical Analyst")))
            .andExpect(jsonPath("$.roleDescription", is("Analyze, Design, Code, and Deploy Enterprise Applications")))
            .andExpect(jsonPath("$.salary", is("100000.00")))
            .andReturn();
                  
    }

    @Test
    public void testPost() throws Exception {
        given(this.repository.save(employeeDetail)).willReturn(employeeDetail);
        String s = objectMapper.writeValueAsString(employeeDetail.asEmployeeDetailItem());
        mvc.perform(
                post("/employee/detail/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(s)
                .accept(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())    
            .andExpect(status().isOk())  
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
            .andExpect(jsonPath("$.empId", is(1)))    
            .andExpect(jsonPath("$.name", is("Karl")))
            .andExpect(jsonPath("$.role", is("Technical Analyst")))
            .andExpect(jsonPath("$.roleDescription", is("Analyze, Design, Code, and Deploy Enterprise Applications")))
            .andExpect(jsonPath("$.salary", is("100000.00")))
            .andReturn();
                  
    }
    @Test
    public void testPut() throws Exception {
        given(this.repository.save(employeeDetail)).willReturn(employeeDetail);
        String s = objectMapper.writeValueAsString(employeeDetail.asEmployeeDetailItem());
        mvc.perform(
                put("/employee/detail/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(s)
                .accept(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())    
            .andExpect(status().isOk())  
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
            .andExpect(jsonPath("$.empId", is(1)))    
            .andExpect(jsonPath("$.name", is("Karl")))
            .andExpect(jsonPath("$.role", is("Technical Analyst")))
            .andExpect(jsonPath("$.roleDescription", is("Analyze, Design, Code, and Deploy Enterprise Applications")))
            .andExpect(jsonPath("$.salary", is("100000.00")))
            .andReturn();
                  
    }

    @Test
    public void testDelete() throws Exception {

        mvc.perform(delete("/employee/detail/delete/1"))
//            .andDo(print())    
            .andExpect(status().isOk())  
            .andReturn();
    }
}
