package engine.services.user;

import engine.dto.converter.Converter;
import engine.dto.converter.ConverterFactory;
import engine.dto.from.user.RegisterUserDto;
import engine.entity.user.User;
import engine.exceptions.user.RegisterFailException;
import engine.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void register(RegisterUserDto userDto) {
    User user = convertDto(userDto);
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new RegisterFailException();
    }
    String encryptedPassword = PASSWORD_ENCODER.encode(user.getPassword());
    user.setPassword(encryptedPassword);
    userRepository.save(user);
  }

  private User convertDto(RegisterUserDto userDto){
    Converter<RegisterUserDto,User> converter = ConverterFactory.getConverter(userDto.getClass());
    return  converter.convert(userDto);
  }

  @Override
  public User getUserById(long id) {
    Optional<User> userOptional = userRepository.findById(id);
    return userOptional.orElse(null);
  }
}
