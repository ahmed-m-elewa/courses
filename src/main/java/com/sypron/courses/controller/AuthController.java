package com.sypron.courses.controller;

import com.sypron.courses.models.dto.UserCredentialsDto;
import com.sypron.courses.models.dto.request.UserRequestDto;
import com.sypron.courses.models.dto.response.LoginResponseDto;
import com.sypron.courses.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login (@RequestBody UserCredentialsDto userCredentials) {
        logger.info("REST Controller to login with credentials [{}]" , userCredentials);
        return new ResponseEntity<>(authService.authenticate(userCredentials) , HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> registerNewUser (@RequestBody @Valid UserRequestDto user) {
        logger.info("REST Controller to register new user [{}]" , user);
        return new ResponseEntity<>(authService.registerNewUser(user) , HttpStatus.OK);
    }

}
