package engine.controllers.user;

import engine.dto.from.user.RegisterUserDto;
import engine.services.user.UserServiceImpl;
import engine.util.user.UserMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
  private final UserServiceImpl userService;

  @Autowired
  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @PostMapping(UserMapping.REGISTER_USER)
  @ResponseStatus(code = HttpStatus.CREATED)
  public void registerUser(@RequestBody @Valid RegisterUserDto userDto) {
    userService.register(userDto);
  }
}
