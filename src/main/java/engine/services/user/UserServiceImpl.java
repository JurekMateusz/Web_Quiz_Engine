package engine.services.user;

import com.sdp.common.assemblers.AssemblerFactory;
import engine.dto.converter.AssemblerWebQuizFactory;
import engine.dto.from.token.RefreshTokenRequest;
import engine.dto.from.user.AuthUserDto;
import engine.dto.to.auth.AuthenticationResponse;
import engine.entity.user.User;
import engine.exceptions.WebQuizException;
import engine.exceptions.user.RegisterFailException;
import engine.repository.user.UserRepository;
import engine.security.jwt.JwtProvider;
import engine.services.token.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Component
public class UserServiceImpl implements UserService {
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
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

    private User convertDto(AuthUserDto auth) {
        AssemblerFactory<AuthUserDto, User> converter = AssemblerWebQuizFactory.getConverter(auth.getClass());
        return converter.assemble(auth);
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
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(userDto.getEmail()).build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        if (!refreshTokenService.isValid(refreshTokenRequest.getRefreshToken())) {
            throw new WebQuizException("Invalid refresh Token");
        }
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getEmail());

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getEmail())
                .build();
    }
}
