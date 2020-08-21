package engine;

import engine.repository.quiz.entity.QuizAnswerQuestionRepository;
import engine.repository.quiz.entity.QuizQuestionRepository;
import engine.repository.quiz.entity.QuizRepository;
import engine.repository.quiz.info.CompleteQuizInfoRepository;
import engine.repository.user.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("engine.entity")
@EnableJpaRepositories(basePackages = "engine.repository")
public class WebQuizEngine extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(WebQuizEngine.class, args);
  }
}
