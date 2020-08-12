package engine.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void add_quiz() throws Exception {
        String jsonResp = "{\"id\":1,\"title\":\"The Java Logo\",\"text\":\"What is depicted on the Java logo?\"" +
                ",\"options\":[\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]}";
        String jsonReq = "{\"title\":\"The Java Logo\",\"text\":\"What is depicted on the Java logo?\",\"options\"" +
                ":[\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"],\"answer\":2}";
        this.mockMvc.perform(post("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON).content(jsonReq))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResp));
    }
}
//    @Test
//    public void post_answer_1() throws Exception {
//        this.mockMvc.perform(post("/api/quiz?answer=2"))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .json("{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}"));
//    }
//
//    @Test
//    public void post_answer_2() throws Exception {
//        this.mockMvc.perform(post("/api/quiz?answer=1"))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .json("{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}"));
//    }
//
//    @Test
//    public void get_quiz() throws Exception {
//        this.mockMvc.perform(get("/api/quiz"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\"title\": \"The Java Logo\",\"text\": \"What is depicted " +
//                        "on the Java logo?\",\"options\": [\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]}\n"));
//    }
//}
