package engine.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import engine.WebQuizEngine;
import engine.controllers.quiz.QuizTestData;
import lombok.SneakyThrows;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** https://www.tutorialspoint.com/spring_boot/spring_boot_rest_controller_unit_test.htm */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebQuizEngine.class)
@WebAppConfiguration
public abstract class AbstractTest {
  protected MockMvc mvc;
  @Autowired WebApplicationContext webApplicationContext;

  protected void setUp() throws Exception {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  protected String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

  protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, clazz);
  }

  private String getAuthorizationToken() throws Exception {
    String username = "test@gmail.com";
    String password = "password";

    String body = "{\"email\":\"" + username + "\", \"password\":\"" + password + "\"}";

    MvcResult result =
        mvc.perform(
                post("/api/login")
                    .contentType(APPLICATION_JSON_VALUE)
                    .content(QuizTestData.USER_REGISTER_JSON))
            .andExpect(status().isOk())
            .andReturn();

    String response = result.getResponse().getContentAsString();
    response = response.replace("{\"access_token\": \"", "");
    String token = response.replace("\"}", "");
    return token;
  }

  @SneakyThrows
  protected String getUserAuthorisation() {
    return "Bearer " + getAuthorizationToken();
  }
}
