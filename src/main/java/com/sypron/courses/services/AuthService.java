package com.sypron.courses.services;

import com.sypron.courses.models.dto.UserCredentialsDto;
import com.sypron.courses.models.dto.UserDto;
import com.sypron.courses.models.dto.request.UserRequestDto;
import com.sypron.courses.models.dto.response.LoginResponseDto;

public interface AuthService {
    public LoginResponseDto authenticate (UserCredentialsDto userCredentials);

    public LoginResponseDto registerNewUser (UserRequestDto userDto);
}
