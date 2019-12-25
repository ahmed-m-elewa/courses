package com.sypron.courses.services.impl;

import com.sypron.courses.dao.UserDao;
import com.sypron.courses.exceptions.IntegrationException;
import com.sypron.courses.exceptions.NotFoundException;
import com.sypron.courses.exceptions.UserAlreadyExistsException;
import com.sypron.courses.models.dto.UserCredentialsDto;
import com.sypron.courses.models.dto.request.UserRequestDto;
import com.sypron.courses.models.dto.response.LoginResponseDto;
import com.sypron.courses.models.entities.Role;
import com.sypron.courses.models.entities.User;
import com.sypron.courses.security.model.HashedPassword;
import com.sypron.courses.security.util.PasswordHashUtil;
import com.sypron.courses.services.AuthService;
import com.sypron.courses.utils.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserDao userDao;

    @Autowired
    TokenGenerator tokenGenerator;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public LoginResponseDto authenticate(UserCredentialsDto userCredentials) {
        logger.info("check user credentials correctness [{}]" , userCredentials);
        User user = null;
        try {
            user = userDao.findByEmail(userCredentials.getEmail());
        } catch (Exception e) {
            logger.error("error occurred during retrieving user from DB" , e);
            throw new IntegrationException("ER_01_001--error occurred during retrieving user from DB");
        }
        if (user == null) {
            logger.error("User not found");
            throw new NotFoundException("ER_05_001--User not found");
        }
        if (!PasswordHashUtil.checkPassword(userCredentials.getPassword() , user.getPassword())) {
            logger.error("incorrect password");
            throw new BadCredentialsException("ER_05_001--incorrect password");
        }
        LoginResponseDto response = new LoginResponseDto();
        response.setUser(user.mapToUserDto());
        response.setToken(tokenGenerator.getToken(user.getRole().getName() , user.getId().toString()));
        return response;
    }

    @Override
    public LoginResponseDto registerNewUser(UserRequestDto userRequest) {
        logger.info("checking username existence [{}]" , userRequest.getUserName());
        if (userDao.existsByUserName(userRequest.getUserName())) {
            logger.error("this userName is already exist");
            throw new UserAlreadyExistsException("ER_04_001--this userName already exists");
        }
        logger.info("checking email existence [{}]" , userRequest.getEmail());
        if (userDao.existsByEmail(userRequest.getEmail())) {
            logger.error("this email is already exist");
            throw new UserAlreadyExistsException("ER_04_002--this email already exists");
        }
        logger.info("creating user ...");
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        HashedPassword hashedPassword = PasswordHashUtil.generatePasswordHash(userRequest.getPassword());
        user.setPassword(hashedPassword.getHash());
        Role role = new Role();
        role.setId(2L);
        user.setRole(role);
        userDao.save(user);
        LoginResponseDto response = new LoginResponseDto();
        response.setUser(user.mapToUserDto());
        response.setToken(tokenGenerator.getToken(user.getRole().getName() , user.getId().toString()));
        return response;

    }
}
