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

import employeeaddress.item.EmployeeAddressItem;
import employeedetail.item.EmployeeDetailItem;
import employeedetail.item.SparseEmployeeDetailItem;
import employeeutil.CustomPageImpl;
import project.item.ProjectItem;
import project.item.SparseProjectItem;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriPort = 80)
@ActiveProfiles("test")
public class EmployeeInfoControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer server;
    
    private EmployeeAddressItem employeeAddressItem;
    private String employeeAddressItemJSON;
    private EmployeeDetailItem employeeDetailItem;
    private String employeeDetailItemJSON;
    private ProjectItem projectItem;
    private List<ProjectItem> projectItems;
    private String projectItemsJSON;

    private SparseProjectItem sparseProjectItem;
    private CustomPageImpl<SparseProjectItem> sparseProjectItems;
    private String sparseProjectItemsJSON;

    private SparseEmployeeDetailItem sparseEmployeeDetailItem;
    private CustomPageImpl<SparseEmployeeDetailItem> sparseEmployeeDetailItems;
    private String sparseEmployeeDetailItemsJSON;

    @Before
    public void setup() throws JsonProcessingException {

        sparseProjectItem = new SparseProjectItem(1, "Test Project");
        List<SparseProjectItem> listSparseProjectItems = new ArrayList<>();
        listSparseProjectItems.add(sparseProjectItem);
        sparseProjectItems = new CustomPageImpl<>();
        sparseProjectItems.setContent(listSparseProjectItems);
        sparseProjectItems.setNumber(0);
        sparseProjectItems.setSize(20);
        sparseProjectItems.setTotalElements(1);
        sparseProjectItemsJSON = objectMapper.writeValueAsString(sparseProjectItems);

        sparseEmployeeDetailItem = new SparseEmployeeDetailItem(1, "Karl");
        List<SparseEmployeeDetailItem> listSparseEmployeeDetailItems = new ArrayList<>();
        listSparseEmployeeDetailItems.add(sparseEmployeeDetailItem);
        sparseEmployeeDetailItems = new CustomPageImpl<>();
        sparseEmployeeDetailItems.setContent(listSparseEmployeeDetailItems);
        sparseEmployeeDetailItems.setNumber(0);
        sparseEmployeeDetailItems.setSize(20);
        sparseEmployeeDetailItems.setTotalElements(1L);
        
        sparseEmployeeDetailItemsJSON = objectMapper.writeValueAsString(sparseEmployeeDetailItems);

        employeeAddressItem = new EmployeeAddressItem();
        employeeAddressItem.setEmpId(1);
        employeeAddressItem.setAddress1("Address 1");
        employeeAddressItem.setAddress2("Address 2");
        employeeAddressItem.setAddress3("Address 3");
        employeeAddressItem.setAddress4("Address 4");
        employeeAddressItem.setState("AZ");
        employeeAddressItem.setCountry("US");
        employeeAddressItemJSON = objectMapper.writeValueAsString(employeeAddressItem);
        //
        employeeDetailItem = new EmployeeDetailItem();
        employeeDetailItem.setEmpId(1);
        employeeDetailItem.setName("Karl Nicholas");
        employeeDetailItem.setRole("4");
        employeeDetailItem.setRoleDescription("Technical Analyst");
        employeeDetailItem.setSalary(new BigDecimal("100000.00"));
        employeeDetailItemJSON = objectMapper.writeValueAsString(employeeDetailItem);
//        sparseEmployeeDetailItem = new SparseEmployeeDetailItem(1, "Karl");
//        sparseEmployeeDetailItems = Page.empty();
//        sparseEmployeeDetailItems.add(sparseEmployeeDetailItem);
        //
        projectItems = new ArrayList<>();
        projectItem = new ProjectItem(1, "Test Project Name", "Test Project Techstack");
        projectItems.add(projectItem);
        projectItemsJSON = objectMapper.writeValueAsString(projectItems);
        
        server = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true).build();
    }

    @Test
    public void testGet() throws Exception {
        server.expect(requestTo(EmployeeDetailController.serviceUrl + "/employee/detail/employees")).andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(sparseEmployeeDetailItemsJSON));

        mvc.perform(get("/info/employees").accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$._embedded.sparseEmployeeDetailResourceList[0].empId", is(1)))
        .andExpect(jsonPath("$._embedded.sparseEmployeeDetailResourceList[0].name", is("Karl")))
        .andExpect(jsonPath("$._embedded.sparseEmployeeDetailResourceList[0]._links.self.href", is("http://localhost/employee/detail/1")))
        .andExpect(jsonPath("$._links.self.href", is("http://localhost/info/employees?page=0&size=20")))
        .andExpect(jsonPath("$.page.size", is(20)))
        .andExpect(jsonPath("$.page.totalElements", is(1)))
        .andExpect(jsonPath("$.page.totalPages", is(1)))
        .andExpect(jsonPath("$.page.number", is(0)))
        .andDo(document("get-employee-detail-employees"));
    }

    @Test
    public void testGetProjects() throws Exception {
        server.expect(requestTo(ProjectController.serviceUrl + "/project/projects")).andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(sparseProjectItemsJSON));

        mvc.perform(get("/info/projects").accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
        .andExpect(jsonPath("$._embedded.sparseProjectResourceList[0].projectId", is(1)))
        .andExpect(jsonPath("$._embedded.sparseProjectResourceList[0].projectName", is("Test Project")))
        .andExpect(jsonPath("$._embedded.sparseProjectResourceList[0]._links.self.href", is("http://localhost/project/1")))
        .andExpect(jsonPath("$._links.self.href", is("http://localhost/info/projects?page=0&size=20")))
        .andExpect(jsonPath("$.page.size", is(20)))
        .andExpect(jsonPath("$.page.totalElements", is(1)))
        .andExpect(jsonPath("$.page.totalPages", is(1)))
        .andExpect(jsonPath("$.page.number", is(0)))
        .andDo(document("get-project-projects"));
    }

    @Test
    public void testEmployee() throws Exception {
       server.expect(requestTo(EmployeeAddressController.serviceUrl + "/employee/address/1")).andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(employeeAddressItemJSON));
        
        server.expect(requestTo(EmployeeDetailController.serviceUrl + "/employee/detail/1")).andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(employeeDetailItemJSON));
       
        server.expect(requestTo(EmployeeProjectsController.serviceUrl + "/employee/projects/1")).andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(projectItemsJSON));

        mvc.perform(get("/info/1").accept(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())    
            .andExpect(status().isOk())    
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
            .andExpect(jsonPath("$.empId", is(1)))  
            .andExpect(jsonPath("$.address1", is("Address 1")))
            .andExpect(jsonPath("$.address2", is("Address 2")))
            .andExpect(jsonPath("$.address3", is("Address 3")))
            .andExpect(jsonPath("$.address4", is("Address 4")))
            .andExpect(jsonPath("$.state", is("AZ")))
            .andExpect(jsonPath("$.country", is("US")))
            .andExpect(jsonPath("$.name", is("Karl Nicholas")))
            .andExpect(jsonPath("$.role", is("4")))
            .andExpect(jsonPath("$.salary", is(100000.00)))
            .andExpect(jsonPath("$.roleDescription", is("Technical Analyst")))
            .andExpect(jsonPath("$.projects[0].projectId", is(1)))
            .andExpect(jsonPath("$.projects[0].projectName", is("Test Project Name")))
            .andExpect(jsonPath("$.projects[0].techstack", is("Test Project Techstack")))
            .andExpect(jsonPath("$._links.self.href", is("http://localhost/info/1")))
            .andDo(document("get-employee-projects"))
            .andReturn();
    }

}