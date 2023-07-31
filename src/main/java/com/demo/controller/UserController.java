package com.demo.controller;

import com.demo.entity.user.User;
import com.demo.modal.UserPrincipal;
import com.demo.service.user.UserService;
import com.demo.utility.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    private ResponseEntity<User> register(@Valid @RequestBody User user){
        User createdUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(),
                    user.getEmail(), user.getPassword());
        return new ResponseEntity<>(createdUser, CREATED);
    }

    @PostMapping("/login")
    private ResponseEntity<User> login(@RequestParam("username") String username,
                                       @RequestParam("password") String password){
        authenticate(username, password);
        User loginUser = userService.findUserByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJWTHeader(userPrincipal);
        return ResponseEntity.ok().headers(jwtHeader).body(loginUser);
    }

    private HttpHeaders getJWTHeader(UserPrincipal userPrincipal){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Jwt-Token", jwtTokenProvider.generateToken(userPrincipal));
        return httpHeaders;
    }

    private void authenticate(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
