package employeeproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

import employeeproject.controller.ProjectController;
import employeeproject.model.Project;
import employeeproject.service.ProjectRepository;

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
    
    @Before
    public void setup() {
        project = new Project();
        project.setProjectId(1);
        project.setProjectName("name 1");
        project.setTechstack("techstack 1");
    }

    @Test
    public void testGet() throws Exception {
        given(this.repository.getOne(1)).willReturn(project);

        mvc.perform(
            get("/project/1")
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
//            .andDo(print())    
        .andExpect(status().isOk())  
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$.projectId", is(1)))    
        .andExpect(jsonPath("$.projectName", is("name 1")))
        .andExpect(jsonPath("$.techstack", is("techstack 1")))
        .andReturn();
                  
    }

    @Test
    public void testPost() throws Exception {
        given(this.repository.save(project)).willReturn(project);
        String s = objectMapper.writeValueAsString(project.asProjectItem());
        mvc.perform(
            post("/project/create")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(s)
            .accept(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())    
        .andExpect(status().isOk())  
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$.projectId", is(1)))    
        .andExpect(jsonPath("$.projectName", is("name 1")))
        .andExpect(jsonPath("$.techstack", is("techstack 1")))
        .andReturn();
                  
    }
    @Test
    public void testPut() throws Exception {
        given(this.repository.save(project)).willReturn(project);
        String s = objectMapper.writeValueAsString(project.asProjectItem());
        mvc.perform(
            put("/project/update")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(s)
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())    
        .andExpect(status().isOk())  
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$.projectId", is(1)))    
        .andExpect(jsonPath("$.projectName", is("name 1")))
        .andExpect(jsonPath("$.techstack", is("techstack 1")))
        .andReturn();
    }

    @Test
    public void testDelete() throws Exception {

        mvc.perform(delete("/project/delete/1"))
//            .andDo(print())    
        .andExpect(status().isOk())  
        .andReturn();
    }
}
