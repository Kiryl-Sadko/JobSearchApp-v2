package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.AuthenticationRequest;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;
import com.epam.esm.service.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserService userService, UserRepository userRepository,
                                    JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder,
                                    Validator validator) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            String userName = authenticationRequest.getUserName();
            String password = authenticationRequest.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

            String token = jwtTokenProvider.createToken(
                    userName);

            Map<Object, Object> response = new HashMap<>();
            response.put("User", userName);
            response.put("Token", token);
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid login/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDto userDto) {
        Utils.validate(userDto, validator);
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        userDto.setRoleIdList(Collections.singletonList(2L));
        userService.save(userDto);
        String message = MessageFormat.format("User {0} has been successfully signup!", userDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
