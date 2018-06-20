package employeeapi.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("test")
public class TestWebConfig {
    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
            return builder.build();
    }

}
