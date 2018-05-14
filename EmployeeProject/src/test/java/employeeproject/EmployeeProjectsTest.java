package employeeproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import employeeproject.controller.EmployeeProjectsController;
import employeeproject.model.Project;
import employeeproject.service.ProjectRepository;
import project.item.ProjectItem;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeProjectsController.class)
public class EmployeeProjectsTest {
    
    @MockBean
    private ProjectRepository repository;
    
    @Autowired
    private MockMvc mvc;

    private List<ProjectItem> employeeProjects;
    
    @Before
    public void setup() {
        employeeProjects = new ArrayList<>();
        Project project = new Project();
        project.setProjectId(1);
        project.setProjectName("Test Project 1");
        project.setTechstack("test techstack 1");
        employeeProjects.add(project.asProjectItem());
    }

    @Test
    public void testGet() throws Exception {
        given(this.repository.findAllByEmployeeProjectEmpId(1)).willReturn(employeeProjects);

        mvc.perform(
            get("/employee/projects/1")
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
//            .andDo(print())    
        .andExpect(status().isOk())  
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$[0].projectId", is(1)))
        .andExpect(jsonPath("$[0].projectName", is("Test Project 1")))
        .andExpect(jsonPath("$[0].techstack", is("test techstack 1")))
        .andReturn();
    }
}
