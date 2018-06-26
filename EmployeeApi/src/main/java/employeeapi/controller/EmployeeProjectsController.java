package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

import employeeapi.resource.ProjectResource;
import employeeapi.resource.ProjectResourceAssembler;
import project.item.ProjectItem;

@RestController
@RequestMapping("/employee/projects")
public class EmployeeProjectsController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProjectResourceAssembler assembler;

    public static final String serviceUrl = "http://EmployeeProjects"; // EmployeeProjects is the name of the microservice we're calling
    
    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getApi() {
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(EmployeeProjectsController.class).getEmployeeProjects(null)).withRel("projects") );
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value="/{empId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resources<ProjectResource>> getEmployeeProjects(@PathVariable Integer empId ) {
        Link linkSelf = linkTo(methodOn(EmployeeProjectsController.class).getEmployeeProjects(empId)).withSelfRel();

        ResponseEntity<List<ProjectItem>> idsResponse = restTemplate.exchange(serviceUrl + "/employee/projects/{empId}",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<ProjectItem>>() {}, empId);

        return new ResponseEntity<Resources<ProjectResource>>(
            new Resources<ProjectResource>(
                Lists.transform(idsResponse.getBody(),
                        item -> assembler.toResource(item)),
                linkSelf), HttpStatus.OK);
    }
}
