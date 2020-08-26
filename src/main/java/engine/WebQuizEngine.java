package engine;

import engine.security.config.WebConfiguration;
import engine.swagger.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("engine.entity")
@EnableJpaRepositories(basePackages = "engine.repository")
@Import({SwaggerConfiguration.class, WebConfiguration.class})
public class WebQuizEngine extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(WebQuizEngine.class, args);
    }
}
