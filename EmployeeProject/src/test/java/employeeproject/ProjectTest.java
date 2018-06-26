package employeeproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import employeeproject.controller.ProjectController;
import employeeproject.model.Project;
import employeeproject.service.ProjectRepository;
import project.item.SparseProjectItem;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
public class ProjectTest {
    @MockBean
    private ProjectRepository repository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Project project;
    private String projectItemJSON;
    private SparseProjectItem sparseProjectItem;
    private Page<SparseProjectItem> sparseProjectItems;
    private PageRequest pageRequest;
    
    @Before
    public void setup() throws JsonProcessingException {
        project = new Project();
        project.setProjectId(1);
        project.setProjectName("name 1");
        project.setTechstack("techstack 1");
        projectItemJSON = objectMapper.writeValueAsString(project.asProjectItem());
        sparseProjectItem = new SparseProjectItem(1, "Test Name");
        List<SparseProjectItem> listSparseProjectItems = new ArrayList<>();
        listSparseProjectItems.add(sparseProjectItem);
        pageRequest = PageRequest.of(0, 20, Sort.unsorted());
        sparseProjectItems = new PageImpl<>(listSparseProjectItems, pageRequest, 1);        
    }

    private void testResult(ResultActions r) throws Exception {
        r.andExpect(status().isOk())  
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
//      .andDo(print())    
        .andExpect(jsonPath("$.projectId", is(1)))    
        .andExpect(jsonPath("$.projectName", is("name 1")))
        .andExpect(jsonPath("$.techstack", is("techstack 1")))
        .andReturn();
    }

    @Test
    public void testGetProjects() throws Exception {
        given(this.repository.findAllBy(pageRequest)).willReturn(sparseProjectItems);
        mvc.perform(
            get("/project/projects")
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        .andExpect(status().isOk())  
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
//      .andDo(print())    
        .andExpect(jsonPath("$.content[0].projectId", is(1)))    
        .andExpect(jsonPath("$.content[0].projectName", is("Test Name")))
        .andExpect(jsonPath("$.size", is(20)))
        .andExpect(jsonPath("$.number", is(0)))
        .andExpect(jsonPath("$.totalElements", is(1)));
    }

    @Test
    public void testGetProject() throws Exception {
        given(this.repository.getOne(1)).willReturn(project);
        testResult(
            mvc.perform(
                get("/project/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }

    @Test
    public void testPost() throws Exception {
        given(this.repository.save(project)).willReturn(project);
        testResult(
            mvc.perform(
                post("/project/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(projectItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }
    @Test
    public void testPut() throws Exception {
        given(this.repository.save(project)).willReturn(project);
        testResult(
            mvc.perform(
                put("/project/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(projectItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }

    @Test
    public void testDelete() throws Exception {
        mvc.perform(delete("/project/delete/1"))
//            .andDo(print())    
        .andExpect(status().isOk())  
        .andReturn();
    }
}
