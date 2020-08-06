package engine.security.userdetails.service;

import engine.entity.user.User;
import engine.repository.user.UserRepository;
import engine.security.userdetails.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findAllByEmail(s);
        userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found: " + s));
        return new UserPrincipal(userOptional.get());
    }
}
