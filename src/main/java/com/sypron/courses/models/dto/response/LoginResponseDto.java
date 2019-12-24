package com.sypron.courses.models.dto.response;

import com.sypron.courses.models.dto.UserDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginResponseDto {
    private UserDto user;
    private String token;
}
