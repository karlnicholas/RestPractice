package employeedetail;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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

import employeedetail.controller.EmployeeDetailController;
import employeedetail.item.SparseEmployeeDetailItem;
import employeedetail.model.EmployeeDetail;
import employeedetail.service.EmployeeDetailRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeDetailController.class)
public class EmployeeDetailTest {
    @MockBean
    private EmployeeDetailRepository repository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private EmployeeDetail employeeDetail;
    private String employeeDetailItemJSON;
    private SparseEmployeeDetailItem sparseEmployeeDetailItem;
    private Page<SparseEmployeeDetailItem> sparseEmployeeDetailItems;
    private PageRequest pageRequest;
    
    @Before
    public void setup() throws JsonProcessingException {
        employeeDetail = new EmployeeDetail();
        employeeDetail.setEmpId(1);
        employeeDetail.setName("Karl");
        employeeDetail.setRole("Technical Analyst");
        employeeDetail.setRoleDescription("Analyze, Design, Code, and Deploy Enterprise Applications");
        employeeDetail.setSalary(new BigDecimal("100000.00"));
        employeeDetailItemJSON = objectMapper.writeValueAsString(employeeDetail.asEmployeeDetailItem());

        sparseEmployeeDetailItem = new SparseEmployeeDetailItem(1, "Test Name");
        List<SparseEmployeeDetailItem> listSparseEmployeeDetailItems = new ArrayList<>();
        listSparseEmployeeDetailItems.add(sparseEmployeeDetailItem);
        pageRequest = PageRequest.of(0, 20, Sort.unsorted());
        sparseEmployeeDetailItems = new PageImpl<>(listSparseEmployeeDetailItems, pageRequest, 1);        
    }

    private void testResult(ResultActions r) throws Exception {
        r.andExpect(status().isOk())  
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
//      .andDo(print())    
        .andExpect(jsonPath("$.empId", is(1)))    
        .andExpect(jsonPath("$.name", is("Karl")))
        .andExpect(jsonPath("$.role", is("Technical Analyst")))
        .andExpect(jsonPath("$.roleDescription", is("Analyze, Design, Code, and Deploy Enterprise Applications")))
        .andExpect(jsonPath("$.salary", is(100000.00)));
    }
          
    @Test
    public void testGetEmployees() throws Exception {
        given(this.repository.findAllBy(pageRequest)).willReturn(sparseEmployeeDetailItems);
        mvc.perform(
            get("/employee/detail/employees")
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
        .andExpect(status().isOk())  
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
//      .andDo(print())    
        .andExpect(jsonPath("$.content[0].empId", is(1)))    
        .andExpect(jsonPath("$.content[0].name", is("Test Name")))
        .andExpect(jsonPath("$.pageable.sort.sorted", is(false)))
        .andExpect(jsonPath("$.pageable.sort.unsorted", is(true)))
        .andExpect(jsonPath("$.pageable.offset", is(0)))
        .andExpect(jsonPath("$.pageable.pageSize", is(20)))
        .andExpect(jsonPath("$.pageable.unpaged", is(false)))
        .andExpect(jsonPath("$.pageable.paged", is(true)))
        .andExpect(jsonPath("$.pageable.paged", is(true)))
        .andExpect(jsonPath("$.totalElements", is(1)))
        .andExpect(jsonPath("$.last", is(true)))
        .andExpect(jsonPath("$.totalPages", is(1)))
        .andExpect(jsonPath("$.size", is(20)))
        .andExpect(jsonPath("$.number", is(0)))
        .andExpect(jsonPath("$.sort.sorted", is(false)))
        .andExpect(jsonPath("$.sort.unsorted", is(true)))
        .andExpect(jsonPath("$.numberOfElements", is(1)))
        .andExpect(jsonPath("$.first", is(true)));
    }

    @Test
    public void testGetEmployee() throws Exception {
        given(this.repository.getOne(1)).willReturn(employeeDetail);
        testResult(
            mvc.perform(
                    get("/employee/detail/1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                )
        );
    }

    @Test
    public void testPost() throws Exception {
        given(this.repository.save(employeeDetail)).willReturn(employeeDetail);
        testResult(
            mvc.perform(
                    post("/employee/detail/create")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(employeeDetailItemJSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                )
        );
    }
    @Test
    public void testPut() throws Exception {
        given(this.repository.save(employeeDetail)).willReturn(employeeDetail);
        testResult(
            mvc.perform(
                    put("/employee/detail/update")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(employeeDetailItemJSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                )
        );
    }

    @Test
    public void testDelete() throws Exception {

        mvc.perform(delete("/employee/detail/delete/1"))
//            .andDo(print())    
            .andExpect(status().isOk());
    }
}
