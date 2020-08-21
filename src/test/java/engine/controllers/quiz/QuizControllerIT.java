package engine.controllers.quiz;

import engine.controllers.AbstractTest;
import engine.dto.from.quiz.add.AddQuizDto;
import engine.dto.to.quiz.FullQuizToUserDto;
import engine.util.quizzes.QuizzesMapping;
import engine.util.user.UserMapping;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static engine.controllers.quiz.QuizTestData.createBadSizeQuizDto_1;
import static engine.controllers.quiz.QuizTestData.createCorrectQuizDto_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class QuizControllerIT extends AbstractTest {
  @Before
  public void setUp() throws Exception {
    super.setUp();
    registerUser();
  }

  private void registerUser() throws Exception {
    mvc.perform(
            post(UserMapping.REGISTER_USER)
                .contentType(APPLICATION_JSON_VALUE)
                .content(QuizTestData.USER_REGISTER_JSON))
        .andExpect(status().is(CREATED.value()));
  }

    @Test
    public void add_quiz_correct_size_answers() throws Exception {
      AddQuizDto quizRequest = createCorrectQuizDto_1();
      String jsonQuiz = super.mapToJson(quizRequest);

      MvcResult mvcResult = postQuiz(jsonQuiz);
      int status = mvcResult.getResponse().getStatus();
      assertEquals(CREATED.value(), status);
    }
  @Test
  public void add_quiz_non_correct_size_answers() throws Exception {
    AddQuizDto quizRequest = createBadSizeQuizDto_1();
    String jsonQuiz = super.mapToJson(quizRequest);

    MvcResult mvcResult = postQuiz(jsonQuiz);
    int status = mvcResult.getResponse().getStatus();
    assertEquals(BAD_REQUEST.value(), status);
  }

  private MvcResult getAllQuizzes() throws Exception {
    return mvc.perform(
            get(QuizzesMapping.BASIC_QUIZZES_PATH)
                    .header(HttpHeaders.AUTHORIZATION, super.getUserAuthorisation()))
            .andReturn();
  }

  private FullQuizToUserDto getQuizFromMvcResult(MvcResult mvcResult) throws IOException {
    String content = mvcResult.getResponse().getContentAsString();
    return super.mapFromJson(content, FullQuizToUserDto.class);
  }

  private MvcResult postQuiz(String jsonQuiz) throws Exception {
    return mvc.perform(
            post(QuizzesMapping.BASIC_QUIZZES_PATH)
                .header(HttpHeaders.AUTHORIZATION, super.getUserAuthorisation())
                .contentType(APPLICATION_JSON_VALUE)
                .content(jsonQuiz))
        .andReturn();
  }

//    @Test
//    public void get_quiz() throws Exception {
//
//      QuizFromUserDto quizDto_1 = createCorrectQuizDto_1();
//      String jsonQuiz = mapToJson(quizDto_1);
//
//      MvcResult mvcResult = postQuiz(jsonQuiz);
//      int status = mvcResult.getResponse().getStatus();
//      assertEquals(CREATED.value(), status);
//
//      QuizToUserDto quizResponse = getQuizFromMvcResult(mvcResult);
//
//      long idQuiz = quizResponse.getId();
//      MvcResult mvcResult1 =
//          mvc.perform(
//                  get(QuizzesMapping.BASIC_QUIZZES_PATH + "/" + idQuiz)
//                      .header(HttpHeaders.AUTHORIZATION, super.getUserAuthorisation()))
//              .andReturn();
//
//      QuizToUserDto quizResponseTest = getQuizFromMvcResult(mvcResult1);
//
//      assertEquals(quizResponse, quizResponseTest);
//    }
//
  @Test
  public void get_all_quizzes_1() throws Exception {
    int testExpectedValue = 0;

    MvcResult mvcResult =
        mvc.perform(
                get(QuizzesMapping.BASIC_QUIZZES_PATH)
                    .header(HttpHeaders.AUTHORIZATION, super.getUserAuthorisation()))
            .andReturn();
    int status = mvcResult.getResponse().getStatus();
    assertEquals(OK.value(), status);
    String content = mvcResult.getResponse().getContentAsString();

    JSONObject jsonObject = new JSONObject(content);
    JSONArray arr = jsonObject.getJSONArray("content");
    assertEquals(arr.length(), testExpectedValue);
  }

  //  @Test
  //  public void get_all_quizzes_2() throws Exception {
  //    int testExpectedValue = 2;

  //
  //    List<QuizFromUserDto> quizzes = List.of(createQuizDto_1(), createQuizDto_2());
  //    quizzes.forEach(
  //        quiz -> {
  //          try {
  //            String json = super.mapToJson(quiz);
  //            postQuiz(json);
  //          } catch (Exception e) {
  //            e.printStackTrace();
  //          }
  //        });

  //    MvcResult mvcResult =
  //        mvc.perform(
  //                get(QuizzesMapping.BASIC_QUIZZES_PATH)
  //                    .header(HttpHeaders.AUTHORIZATION, super.getUserAuthorisation()))
  //            .andReturn();

  //    int status = mvcResult.getResponse().getStatus();
  //    assertEquals(OK.value(), status);
  //    String content = mvcResult.getResponse().getContentAsString();
  //
  //    JSONObject jsonObject = new JSONObject(content);
  //    JSONArray arr = jsonObject.getJSONArray("content");
  //    assertEquals(arr.length(), testExpectedValue);
  //
  //    ObjectMapper objectMapper = new ObjectMapper();
  //    List<QuizToUserDto> quizzesResultTest =
  //        objectMapper.readValue(
  //            jsonObject.getString("content"), new TypeReference<List<QuizToUserDto>>() {});
  //    for (int i = 0; i < quizzes.size(); i++) {
  //      QuizFromUserDto quizFromUserDto = quizzes.get(i);
  //      QuizToUserDto quizToUserDto = quizzesResultTest.get(i);
  //      assertEquals(quizFromUserDto.getText(), quizToUserDto.getText());
  //      assertEquals(quizFromUserDto.getTitle(), quizToUserDto.getTitle());
  //      assertEquals(quizFromUserDto.getOptions().toArray(), quizToUserDto.getOptions());
  //    }
  //  }
}
