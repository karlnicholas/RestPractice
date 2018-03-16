package employeezuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

@EnableZuulServer
@SpringBootApplication
public class EmployeeZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeZuulApplication.class, args);
	}
}
