package employeeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EmployeeProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeProjectApplication.class, args);
	}
}
