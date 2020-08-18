package engine.controllers.user;

import engine.controllers.AbstractTest;
import engine.entity.user.User;
import engine.util.user.UserMapping;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerIT extends AbstractTest {
  @Before
  public void setUp() throws Exception {
    super.setUp();
  }

  @Test
  public void register_user_1() throws Exception {
    User user =
        User.builder().email(UseTestData.GOOD_EMAIL).password(UseTestData.GOOD_PASSWORD).build();
    String userJson = super.mapToJson(user);

    mvc.perform(
            post(UserMapping.REGISTER_USER).contentType(APPLICATION_JSON_VALUE).content(userJson))
        .andExpect(status().is(CREATED.value()));
  }

  @Test
  public void register_user_2() throws Exception {
    User user =
        User.builder().email(UseTestData.BAD_EMAIL).password(UseTestData.BAD_PASSWORD).build();
    String userJson = super.mapToJson(user);

    mvc.perform(
            post(UserMapping.REGISTER_USER).contentType(APPLICATION_JSON_VALUE).content(userJson))
        .andExpect(status().is(BAD_REQUEST.value()));
  }
}
