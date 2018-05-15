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
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import employeeproject.controller.EmployeeProjectController;
import employeeproject.model.EmployeeProject;
import employeeproject.model.EmployeeProjectId;
import employeeproject.service.EmployeeProjectRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeProjectController.class)
public class EmployeeProjectTest {
    @MockBean
    private EmployeeProjectRepository repository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private EmployeeProject employeeProject;
    private List<EmployeeProject> employeeProjects;
    private String employeeProjectItemJSON;
    
    @Before
    public void setup() throws JsonProcessingException {
        employeeProject = new EmployeeProject();
        employeeProject.setEmployeeProjectId(new EmployeeProjectId(1, 1));
        employeeProjects = new ArrayList<>();
        employeeProjects.add(employeeProject);
        employeeProjectItemJSON = objectMapper.writeValueAsString(employeeProject.asEmployeeProjectItem());
    }

    private void testResult(ResultActions r) throws Exception {
        r.andExpect(status().isOk())  
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$.empId", is(1)))    
        .andExpect(jsonPath("$.projectId", is(1)));    
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGet() throws Exception {
        given(this.repository.findAll(any(Example.class))).willReturn(employeeProjects);
        mvc.perform(
            get("/employee/project/1")
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
//            .andDo(print())    
        .andExpect(status().isOk())  
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$[0].empId", is(1)))    
        .andExpect(jsonPath("$[0].projectId", is(1)))
        .andReturn();
                  
    }

    @Test
    public void testPost() throws Exception {
        given(this.repository.save(employeeProject)).willReturn(employeeProject);
        testResult(
            mvc.perform(
                post("/employee/project/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeProjectItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }
    @Test
    public void testPut() throws Exception {
        given(this.repository.save(employeeProject)).willReturn(employeeProject);
        testResult(
            mvc.perform(
                put("/employee/project/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeProjectItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }
    
    @Test
    public void testDelete() throws Exception {
        mvc.perform(delete("/employee/project/delete/1/1"))
//            .andDo(print())    
        .andExpect(status().isOk())  
        .andReturn();
    }
}
