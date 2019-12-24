package com.sypron.courses.models.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseDto {
    private Long id;
    private String title;
    private String description;
    private Long price;
    private String instructorName;
    private Integer period;
}
