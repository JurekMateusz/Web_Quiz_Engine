package engine.controllers.user;

import engine.entity.user.User;
import engine.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  @ResponseStatus(code = HttpStatus.OK)
  public void registerUser(@RequestBody @Valid User user) {
    userService.register(user);
  }
}
