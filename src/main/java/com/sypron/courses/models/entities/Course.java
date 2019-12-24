package com.sypron.courses.models.entities;

import com.sypron.courses.models.dto.CourseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

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

    public CourseDto mapToCourseDto() {
        return new ModelMapper().map(this , CourseDto.class);
    }
}
