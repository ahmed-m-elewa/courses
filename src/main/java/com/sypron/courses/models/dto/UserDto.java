package com.sypron.courses.models.dto;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class UserDto {
    private Long id;
    @NotNull(message = "ER_03_001--userName can't be null")
    @NotEmpty(message = "ER_03_001--userName can't be empty")
    private String userName;
    @NotNull(message = "ER_03_001--firstName can't be null")
    @NotEmpty(message = "ER_03_001--firstName can't be empty")
    private String firstName;
    @NotNull(message = "ER_03_001--lastName can't be null")
    @NotEmpty(message = "ER_03_001--lastName can't be empty")
    private String lastName;
    @NotNull(message = "ER_03_001--email can't be null")
    @NotEmpty(message = "ER_03_001--email can't be empty")
    @Email(message = "ER_03_002--invalid Email")
    private String email;
    @NotNull(message = "ER_03_001--password can't be null")
    @NotEmpty(message = "ER_03_001--password can't be empty")
    @Length(min = 7 , message = "ER_03_003--password must be 7 characters at least!")
    private String password;
    private RoleDto role;
}