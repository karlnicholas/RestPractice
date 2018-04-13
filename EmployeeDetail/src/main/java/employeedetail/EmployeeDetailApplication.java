package employeedetail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableEurekaClient
@EnableSpringDataWebSupport
public class EmployeeDetailApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeDetailApplication.class, args);
    }
}
