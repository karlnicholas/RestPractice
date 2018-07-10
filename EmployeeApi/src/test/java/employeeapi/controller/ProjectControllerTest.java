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

import project.item.ProjectItem;

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


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriPort = 80)
@ActiveProfiles("test")
public class ProjectControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer server;

    private ProjectItem projectItem;
    private String projectItemJSON;

    @Before
    public void setup() throws JsonProcessingException {
        projectItem = new ProjectItem(1, "Test Project", "Test Techstack");
        projectItemJSON = objectMapper.writeValueAsString(projectItem);
        server = MockRestServiceServer.bindTo(restTemplate).build();
    }

    private ResultActions testPackage(ResultActions r) throws Exception {
        r.andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
//      .andDo(print())    
        .andExpect(jsonPath("$.projectId", is(1)))
        .andExpect(jsonPath("$.projectName", is("Test Project")))    
        .andExpect(jsonPath("$.techstack", is("Test Techstack")))
        .andExpect(jsonPath("$._links.self.href", is("http://localhost/project/1")))
        .andExpect(jsonPath("$._links.delete.href", is("http://localhost/project/delete/1")));
        return r;
    }

    @Test
    public void testGetProject() throws Exception {
        server.expect(requestTo(ProjectController.serviceUrl + "/project/1")).andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(projectItemJSON));
        testPackage(
            mvc.perform(get("/project/1").accept(MediaType.APPLICATION_JSON_VALUE))
        ).andDo(document("get-project"));
    }

    @Test
    public void testCreate() throws Exception {
        server.expect(requestTo(ProjectController.serviceUrl + "/project/create")).andExpect(method(HttpMethod.POST))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(projectItemJSON));
        testPackage(
            mvc.perform(post("/project/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(projectItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        ).andDo(document("create-project"));
    }

    @Test
    public void testUpdate() throws Exception {
        server.expect(requestTo(ProjectController.serviceUrl + "/project/update")).andExpect(method(HttpMethod.PUT))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(projectItemJSON));
        testPackage(
            mvc.perform(put("/project/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(projectItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        ).andDo(document("update-project"));
    }

    @Test
    public void testDlete() throws Exception {
        server.expect(requestTo(ProjectController.serviceUrl + "/project/delete/1")).andExpect(method(HttpMethod.DELETE))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(HttpStatus.OK.name()));
        mvc.perform(delete("/project/delete/1"))
//      .andDo(print())    
        .andExpect(status().isOk())
        .andExpect(content().string("OK"))
        .andDo(document("delete-project"));    
    }
        
}