package employeeapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceSupport> getApi(HttpServletRequest request) {
        if ( request != null )
            logger.info("Request from remote host: " + request.getRemoteHost());
        ResourceSupport resource = new ResourceSupport();
        resource.add( linkTo(methodOn(EmployeeApiController.class).getApi()).withRel("info") );
        resource.add( linkTo(methodOn(EmployeeApiController.class).getApi()).withRel("employee") );
        resource.add( linkTo(methodOn(ProjectController.class).getApi()).withRel("project") );
        return ResponseEntity.ok(resource);
    }

}
