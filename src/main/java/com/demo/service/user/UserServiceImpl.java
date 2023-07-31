package com.demo.service.user;

import com.demo.entity.user.User;
import com.demo.exception.UserEmailAlreadyTakenException;
import com.demo.exception.UserNotFoundException;
import com.demo.modal.UserPrincipal;
import com.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.demo.role.Role.USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(username));
        return new UserPrincipal(user);
    }

    @Override
    public User register(String firstName, String lastName, String username, String email, String password) {
        Optional<User> existUser = userRepository.findUserByEmail(email);
        if (existUser.isPresent()){
            throw new UserEmailAlreadyTakenException(email);
        }
        User user = new User();
        String encodedPassword = encodePassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setEmail(email);
        user.setRole(USER.name());
        userRepository.save(user);
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .stream().findFirst()
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
}
