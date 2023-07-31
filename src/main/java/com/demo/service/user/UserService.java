package com.demo.service.user;

import com.demo.entity.user.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User register(String firstName, String lastName, String username, String email, String password);

    User findUserByUsername(String username);
}
