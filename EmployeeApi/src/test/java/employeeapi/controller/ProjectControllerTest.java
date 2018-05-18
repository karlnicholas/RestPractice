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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import employeeapi.controller.ProjectController.ProjectClient;
import employeeapi.resource.ProjectResourceAssembler;
import employeeapi.resource.SparseProjectResourceAssembler;
import project.item.ProjectItem;
import project.item.SparseProjectItem;

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
@WebMvcTest(ProjectController.class)
@Import({SparseProjectResourceAssembler.class, ProjectResourceAssembler.class})
public class ProjectControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @MockBean
    // mock the FeignClient
    private ProjectClient projectClient;
    private ProjectItem projectItem;
    private SparseProjectItem sparseProjectItem;
    private Page<SparseProjectItem> sparseProjectItems;
    private String projectItemJSON;
    private PageRequest pageRequest;

    @Before
    public void setup() throws JsonProcessingException {
        projectItem = new ProjectItem(1, "Test Project", "Test Techstack");
        projectItemJSON = objectMapper.writeValueAsString(projectItem);
        sparseProjectItem = new SparseProjectItem(1, "Test Project");
        List<SparseProjectItem> listSparseProjectItems = new ArrayList<>();
        listSparseProjectItems.add(sparseProjectItem);
        pageRequest = PageRequest.of(0, 20, Sort.unsorted());
        sparseProjectItems = new PageImpl<>(listSparseProjectItems, pageRequest, 1);
    }

    private void testPackage(ResultActions r) throws Exception {
        r.andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
//      .andDo(print())    
        .andExpect(jsonPath("$.projectId", is(1)))
        .andExpect(jsonPath("$.projectName", is("Test Project")))    
        .andExpect(jsonPath("$.techstack", is("Test Techstack")))
        .andExpect(jsonPath("$._links.self.href", is("http://localhost/project/1")))
        .andExpect(jsonPath("$._links.create.href", is("http://localhost/project/create")))
        .andExpect(jsonPath("$._links.update.href", is("http://localhost/project/update")))
        .andExpect(jsonPath("$._links.delete.href", is("http://localhost/project/delete/1")));
    }

    @Test
    public void testGetProjects() throws Exception {
        when(projectClient.getProjects(pageRequest)).thenReturn(ResponseEntity.ok(sparseProjectItems));
        mvc.perform(get("/project/projects").accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$._embedded.sparseProjectResourceList[0].projectId", is(1)))
        .andExpect(jsonPath("$._embedded.sparseProjectResourceList[0].projectName", is("Test Project")))
        .andExpect(jsonPath("$._embedded.sparseProjectResourceList[0]._links.self.href", is("http://localhost/project/1")))
        .andExpect(jsonPath("$._links.self.href", is("http://localhost/project/projects?page=0&size=20")))
        .andExpect(jsonPath("$.page.size", is(20)))
        .andExpect(jsonPath("$.page.totalElements", is(1)))
        .andExpect(jsonPath("$.page.totalPages", is(1)))
        .andExpect(jsonPath("$.page.number", is(0)));
    }

    @Test
    public void testGetProject() throws Exception {
        when(projectClient.getProject(1)).thenReturn(ResponseEntity.ok(projectItem));
        testPackage(
            mvc.perform(get("/project/1").accept(MediaType.APPLICATION_JSON_VALUE))
        );
    }

    @Test
    public void testCreate() throws Exception {
        when(projectClient.postProject(projectItem)).thenReturn(ResponseEntity.ok(projectItem));
        testPackage(
            mvc.perform(post("/project/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(projectItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }

    @Test
    public void testUpdate() throws Exception {
        when(projectClient.putProject(projectItem)).thenReturn(ResponseEntity.ok(projectItem));
        testPackage(
            mvc.perform(put("/project/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(projectItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }

    @Test
    public void testDlete() throws Exception {
        when(projectClient.deleteProject(1)).thenReturn(ResponseEntity.ok(HttpStatus.OK.name()));
        mvc.perform(delete("/project/delete/1"))
//      .andDo(print())    
        .andExpect(status().isOk())
        .andExpect(content().string("OK"));    
    }    
}