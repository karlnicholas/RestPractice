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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import employeeapi.controller.EmployeeProjectController.EmployeeProjectClient;
import employeeapi.resource.EmployeeProjectResourceAssembler;
import employeeproject.item.EmployeeProjectItem;

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
@WebMvcTest(EmployeeProjectController.class)
@Import(EmployeeProjectResourceAssembler.class)
public class EmployeeProjectControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @MockBean
    // mock the FeignClient
    private EmployeeProjectClient employeeProjectClient;
    private EmployeeProjectItem employeeProjectItem;
    private List<EmployeeProjectItem> employeeProjectItems;
    private String employeeProjectItemJSON;

    @Before
    public void setup() throws JsonProcessingException {
        employeeProjectItem = new EmployeeProjectItem();
        employeeProjectItem.setEmpId(1);
        employeeProjectItem.setProjectId(1);
        employeeProjectItemJSON = objectMapper.writeValueAsString(employeeProjectItem);
        employeeProjectItems = new ArrayList<>();
        employeeProjectItems.add(employeeProjectItem);
    }

    private void testPackage(ResultActions r) throws Exception {
        r.andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
//      .andDo(print())    
        .andExpect(jsonPath("$.empId", is(1)))    
        .andExpect(jsonPath("$.projectId", is(1)))    
        .andExpect(jsonPath("$._links.delete.href", is("http://localhost/employee/project/delete/1/1")));
    }

    @Test
    public void testGet() throws Exception {
        when(employeeProjectClient.getEmployeeProjects(1)).thenReturn(ResponseEntity.ok(employeeProjectItems));
        mvc.perform(get("/employee/project/1").accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$._embedded.employeeProjectResourceList[0].empId", is(1)))    
        .andExpect(jsonPath("$._embedded.employeeProjectResourceList[0].projectId", is(1)))
        .andExpect(jsonPath("$._embedded.employeeProjectResourceList[0]._links.delete.href", is("http://localhost/employee/project/delete/1/1")))
        .andExpect(jsonPath("$._links.self.href", is("http://localhost/employee/project/1")))
        .andExpect(jsonPath("$._links.create.href", is("http://localhost/employee/project/create")));
    }

    @Test
    public void testCreate() throws Exception {
        when(employeeProjectClient.postEmployeeProject(employeeProjectItem)).thenReturn(ResponseEntity.ok(employeeProjectItem));
        testPackage(
            mvc.perform(post("/employee/project/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeProjectItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }

    @Test
    public void testUpdate() throws Exception {
        when(employeeProjectClient.putEmployeeProject(employeeProjectItem)).thenReturn(ResponseEntity.ok(employeeProjectItem));
        testPackage(
            mvc.perform(put("/employee/project/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(employeeProjectItemJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
            )
        );
    }

    @Test
    public void testDlete() throws Exception {
        when(employeeProjectClient.deleteEmployeeProject(1, 1)).thenReturn(ResponseEntity.ok(HttpStatus.OK.name()));
        mvc.perform(delete("/employee/project/delete/1/1"))
//      .andDo(print())    
        .andExpect(status().isOk())
        .andExpect(content().string("OK"));    
    }    
}