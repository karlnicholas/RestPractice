package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getNav(HttpServletRequest request) {
        logger.info("Request from remote host: " + request.getRemoteHost());
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(EmployeeAddressController.class).getApi()).withRel("address") );
        resource.add( linkTo(methodOn(EmployeeDetailController.class).getApi()).withRel("detail") );
        resource.add( linkTo(methodOn(EmployeeProjectController.class).getApi()).withRel("project") );
        resource.add( new Link(linkTo(EmployeeInfoController.class).toString() + "{?page,size}").withSelfRel() );
        return ResponseEntity.ok(resource);
    }

}
