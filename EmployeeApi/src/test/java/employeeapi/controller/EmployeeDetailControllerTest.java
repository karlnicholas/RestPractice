package employeeapi.controller;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import employeedetail.item.EmployeeDetailItem;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeeDetailControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer server;

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
        server = MockRestServiceServer.createServer(restTemplate);
    }

    private void testPackage(ResultActions r) throws Exception {
        r.andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
//      .andDo(print())    
        .andExpect(jsonPath("$.empId", is(1)))    
        .andExpect(jsonPath("$.name", is("Karl")))
        .andExpect(jsonPath("$.salary", is(100000.00)))
        .andExpect(jsonPath("$.role", is("Technical Analyst")))
        .andExpect(jsonPath("$.roleDescription", is("Analyze Technicals")))
        .andExpect(jsonPath("$._links.self.href", is("http://localhost/employee/detail/1")))
        .andExpect(jsonPath("$._links.delete.href", is("http://localhost/employee/detail/delete/1")))
        .andExpect(jsonPath("$._links.update.href", is("http://localhost/employee/detail/update")))
        .andExpect(jsonPath("$._links.create.href", is("http://localhost/employee/detail/create")));
    }

    @Test
    public void testGetDetail() throws Exception {
        server.expect(requestTo(EmployeeDetailController.serviceUrl + "/employee/detail/1")).andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(employeeDetailItemJSON));
        testPackage(
            mvc.perform(get("/employee/detail/1").accept(MediaType.APPLICATION_JSON_VALUE))
        );
    }

    @Test
    public void testCreate() throws Exception {
        server.expect(requestTo(EmployeeDetailController.serviceUrl + "/employee/detail/create")).andExpect(method(HttpMethod.POST))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(employeeDetailItemJSON));
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
        server.expect(requestTo(EmployeeDetailController.serviceUrl + "/employee/detail/update")).andExpect(method(HttpMethod.PUT))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(employeeDetailItemJSON));
        testPackage(
            mvc.perform(put("/employee/detail/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeDetailItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }

    @Test
    public void testDelete() throws Exception {
        server.expect(requestTo(EmployeeDetailController.serviceUrl + "/employee/detail/delete/1")).andExpect(method(HttpMethod.DELETE))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(HttpStatus.OK.name()));
        mvc.perform(delete("/employee/detail/delete/1"))
//      .andDo(print())    
        .andExpect(status().isOk())
        .andExpect(content().string("OK"));    
    }

}