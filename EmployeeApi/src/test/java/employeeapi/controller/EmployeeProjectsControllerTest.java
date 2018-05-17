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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;

import employeeapi.controller.EmployeeProjectsController.EmployeeProjectsClient;
import employeeapi.resource.ProjectResourceAssembler;
import project.item.ProjectItem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeProjectsController.class)
@Import(ProjectResourceAssembler.class)
public class EmployeeProjectsControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    // mock the FeignClient
    private EmployeeProjectsClient employeeProjectsClient;
    private ProjectItem projectItem;
    private List<ProjectItem> projectItems;

    @Before
    public void setup() throws JsonProcessingException {
        projectItem = new ProjectItem(1, "Test Project", "Test Techstack");
        projectItems = new ArrayList<>();
        projectItems.add(projectItem);
    }

    @Test
    public void testGet() throws Exception {
        when(employeeProjectsClient.getEmployeeProjectsFull(1)).thenReturn(ResponseEntity.ok(projectItems));
        mvc.perform(get("/employee/projects/1").accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$._embedded.projectResourceList[0].projectId", is(1)))
        .andExpect(jsonPath("$._embedded.projectResourceList[0].projectName", is("Test Project")))
        .andExpect(jsonPath("$._embedded.projectResourceList[0].techstack", is("Test Techstack")))
        .andExpect(jsonPath("$._embedded.projectResourceList[0]._links.self.href", is("http://localhost/project/1")))
        .andExpect(jsonPath("$._embedded.projectResourceList[0]._links.delete.href", is("http://localhost/project/delete/1")))
        .andExpect(jsonPath("$._links.self.href", is("http://localhost/employee/projects/1")));
    }

}