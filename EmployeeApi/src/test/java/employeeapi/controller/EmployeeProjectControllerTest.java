package employeeapi.controller;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
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

import employeeproject.item.EmployeeProjectItem;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriPort = 80)
@ActiveProfiles("test")
public class EmployeeProjectControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer server;

    private EmployeeProjectItem employeeProjectItem;
    private List<EmployeeProjectItem> employeeProjectItems;
    private String employeeProjectItemJSON;
    private String employeeProjectItemsJSON;

    @Before
    public void setup() throws JsonProcessingException {
        employeeProjectItem = new EmployeeProjectItem();
        employeeProjectItem.setEmpId(1);
        employeeProjectItem.setProjectId(1);
        employeeProjectItemJSON = objectMapper.writeValueAsString(employeeProjectItem);
        employeeProjectItems = new ArrayList<>();
        employeeProjectItems.add(employeeProjectItem);
        employeeProjectItemsJSON = objectMapper.writeValueAsString(employeeProjectItems);
        server = MockRestServiceServer.bindTo(restTemplate).build();
    }

    private ResultActions testPackage(ResultActions r) throws Exception {
        r.andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
//      .andDo(print())    
        .andExpect(jsonPath("$.empId", is(1)))    
        .andExpect(jsonPath("$.projectId", is(1)))    
        .andExpect(jsonPath("$._links.delete.href", is("http://localhost/employee/project/delete/1/1")));
        return r;
    }

    @Test
    public void testGet() throws Exception {
        server.expect(requestTo(EmployeeProjectController.serviceUrl + "/employee/project/1")).andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(employeeProjectItemsJSON));
        mvc.perform(get("/employee/project/1").accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$._embedded.employeeProjectResourceList[0].empId", is(1)))    
        .andExpect(jsonPath("$._embedded.employeeProjectResourceList[0].projectId", is(1)))
        .andExpect(jsonPath("$._embedded.employeeProjectResourceList[0]._links.delete.href", is("http://localhost/employee/project/delete/1/1")))
        .andExpect(jsonPath("$._links.self.href", is("http://localhost/employee/project/1")))
        .andDo(document("get-employee-project"));
    }

    @Test
    public void testCreate() throws Exception {
        server.expect(requestTo(EmployeeProjectController.serviceUrl + "/employee/project/create")).andExpect(method(HttpMethod.POST))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(employeeProjectItemJSON));
        testPackage(
            mvc.perform(post("/employee/project/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeProjectItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        ).andDo(document("create-employee-project"));
    }

    @Test
    public void testUpdate() throws Exception {
        server.expect(requestTo(EmployeeProjectController.serviceUrl + "/employee/project/update")).andExpect(method(HttpMethod.PUT))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(employeeProjectItemJSON));
        testPackage(
            mvc.perform(put("/employee/project/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeProjectItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        ).andDo(document("update-employee-project"));
    }

    @Test
    public void testDlete() throws Exception {
        server.expect(requestTo(EmployeeProjectController.serviceUrl + "/employee/project/delete/1/1")).andExpect(method(HttpMethod.DELETE))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(HttpStatus.OK.name()));
        mvc.perform(delete("/employee/project/delete/1/1"))
//      .andDo(print())    
        .andExpect(status().isOk())
        .andExpect(content().string("OK"))
        .andDo(document("delete-employee-project"));
    }
        
}