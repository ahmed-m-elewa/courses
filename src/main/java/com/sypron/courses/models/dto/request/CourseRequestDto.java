package com.sypron.courses.models.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseRequestDto {
    private String title;
    private String description;
    private Long price;
    private String instructorName;
    private Integer period;
}
