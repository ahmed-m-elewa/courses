package com.sypron.courses.services;

import com.sypron.courses.models.dto.CourseDto;
import com.sypron.courses.models.dto.request.CourseRequestDto;

import java.util.List;

public interface CoursesService {

    public List<CourseDto> getAllCourses(Long userId);

    public CourseDto createCourse (CourseRequestDto courseRequestDto);

    public CourseDto updateCourse (CourseRequestDto courseRequestDto , Long courseId);

    public void deleteCourse (Long courseId);

    public void enrollInCourse (Long userId , Long courseId);

    public void exitFromCourse (Long userId , Long courseId);

    public List<CourseDto> getUserCourses (Long userId);
}
