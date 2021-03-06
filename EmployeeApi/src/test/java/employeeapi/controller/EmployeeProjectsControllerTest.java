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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.item.ProjectItem;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class EmployeeProjectsControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    private ProjectItem projectItem;
    private List<ProjectItem> projectItems;
    private String projectItemsJSON;
    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer server;

    @Before
    public void setup() throws JsonProcessingException {
        projectItem = new ProjectItem(1, "Test Project", "Test Techstack");
        projectItems = new ArrayList<>();
        projectItems.add(projectItem);
        projectItemsJSON = objectMapper.writeValueAsString(projectItems);
        server = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void testGet() throws Exception {
        server.expect(requestTo(EmployeeProjectsController.serviceUrl + "/employee/projects/1")).andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(projectItemsJSON));
        mvc.perform(get("/employee/projects/1").accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$.[0].projectId", is(1)))
        .andExpect(jsonPath("$.[0].projectName", is("Test Project")))
        .andExpect(jsonPath("$.[0].techstack", is("Test Techstack")))
        .andDo(document("get-employee-projects"));
    }    

}