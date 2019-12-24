package com.sypron.courses.models.entities;

import com.sypron.courses.models.dto.CourseDto;
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
@Table(name = "COURSE")
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "PRICE", nullable = false)
    private Long price;

    @Column(name = "INSTRUCTOR_NAME", nullable = false)
    private String instructorName;

    @Column(name = "PERIOD", nullable = false)
    private Integer period;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_COURSES", joinColumns = { @JoinColumn(name = "COURSE_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "USER_ID") })
    private List<User> usersList = new ArrayList<User>();


    public CourseDto mapToCourseDto() {
        CourseDto courseDto = new ModelMapper().map(this , CourseDto.class);
        courseDto.setEnrolled(false);
        return courseDto;
    }
    public CourseDto mapToCourseDto(Long userId) {
        CourseDto courseDto = new ModelMapper().map(this , CourseDto.class);
        for (User user :
                getUsersList()) {
            if (user.getId() == userId) {
                courseDto.setEnrolled(true);
            }
        }
        return courseDto;
    }
}
