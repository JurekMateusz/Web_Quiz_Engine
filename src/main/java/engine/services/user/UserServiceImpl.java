package engine.services.user;

import engine.dto.converter.Converter;
import engine.dto.converter.ConverterFactory;
import engine.dto.from.user.AuthUserDto;
import engine.dto.to.auth.AuthenticationResponse;
import engine.entity.user.User;
import engine.exceptions.user.RegisterFailException;
import engine.repository.user.UserRepository;
import engine.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtProvider jwtProvider;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      JwtProvider jwtProvider) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.jwtProvider = jwtProvider;
  }

  @Override
  public void register(AuthUserDto userDto) {
    User user = convertDto(userDto);
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new RegisterFailException();
    }
    String encryptedPassword = PASSWORD_ENCODER.encode(user.getPassword());
    user.setPassword(encryptedPassword);
    userRepository.save(user);
  }

  private User convertDto(AuthUserDto userDto) {
    Converter<AuthUserDto, User> converter = ConverterFactory.getConverter(userDto.getClass());
    return converter.convert(userDto);
  }

  @Override
  public User getUserById(long id) {
    Optional<User> userOptional = userRepository.findById(id);
    return userOptional.orElse(null);
  }

  @Override
  public AuthenticationResponse login(AuthUserDto userDto) {
    Authentication authenticate =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authenticate);
    String token = jwtProvider.generateToken(authenticate);
    return new AuthenticationResponse(token, userDto.getEmail());
  }
}
