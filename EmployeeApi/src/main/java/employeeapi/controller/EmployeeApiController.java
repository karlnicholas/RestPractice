package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeApiController {
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getApi() {
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(EmployeeAddressController.class).getApi()).withRel("address") );
        resource.add( linkTo(methodOn(EmployeeDetailController.class).getApi()).withRel("detail") );
        resource.add( linkTo(methodOn(EmployeeProjectController.class).getApi()).withRel("project") );
        resource.add( linkTo(methodOn(EmployeeProjectsController.class).getApi()).withRel("projects") );
        resource.add( linkTo(methodOn(EmployeeInfoController.class).getApi()).withRel("info") );
        return ResponseEntity.ok(resource);
    }

}
