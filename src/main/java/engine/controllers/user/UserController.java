package engine.controllers.user;

import engine.dto.from.token.RefreshTokenRequest;
import engine.dto.from.user.AuthUserDto;
import engine.dto.to.auth.AuthenticationResponse;
import engine.services.token.RefreshTokenService;
import engine.services.user.UserServiceImpl;
import engine.util.user.UserMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class UserController {
    private final UserServiceImpl userService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public UserController(UserServiceImpl userService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping(UserMapping.REGISTER_USER)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid AuthUserDto userDto) {
        userService.register(userDto);
    }

    @PostMapping(UserMapping.LOGIN_USER)
    public AuthenticationResponse login(@RequestBody AuthUserDto userDto) {
        return userService.login(userDto);
    }

    @PostMapping(UserMapping.REFRESH_TOKEN)
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return userService.refreshToken(refreshTokenRequest);
    }

    @PostMapping(UserMapping.LOGOUT_USER)
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body(UserMapping.LOGOUT_INFO);
    }

}
