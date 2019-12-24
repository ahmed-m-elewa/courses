package com.sypron.courses.services;

import com.sypron.courses.models.dto.CourseDto;

import java.util.List;

public interface CoursesService {

    public List<CourseDto> getAllCourses();

    public CourseDto createCourse (CourseDto courseDto);

    public CourseDto updateCourse (CourseDto courseDto , Long courseId);

    public void deleteCourse (Long courseId);

    public void enrollInCourse (Long userId , Long courseId);

    public void exitFromCourse (Long userId , Long courseId);

    public List<CourseDto> getUserCourses (Long userId);
}
