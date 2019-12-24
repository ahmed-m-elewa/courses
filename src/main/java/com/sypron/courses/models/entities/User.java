package com.sypron.courses.models.entities;

import com.sypron.courses.dao.UserDao;
import com.sypron.courses.models.dto.UserDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Table(name = "USER")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_COURSES", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "COURSE_ID") })
    private List<Course> coursesList = new ArrayList<Course>();

    public UserDto mapToUserDto() {
        return new ModelMapper().map(this, UserDto.class);
    }

}
