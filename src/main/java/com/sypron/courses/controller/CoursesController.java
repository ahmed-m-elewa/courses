package com.sypron.courses.controller;

import com.sypron.courses.models.dto.CourseDto;
import com.sypron.courses.models.dto.request.CourseRequestDto;
import com.sypron.courses.services.CoursesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/courses")
public class CoursesController {

    @Autowired
    CoursesService coursesService;

    private static final Logger logger = LoggerFactory.getLogger(CoursesController.class);

    @GetMapping()
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        logger.info("REST Controller to get all courses");
        String userIdentity = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(coursesService.getAllCourses(Long.parseLong(userIdentity)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseRequestDto courseRequestDto) {
        logger.info("REST Controller to create course [{}]", courseRequestDto);
        return new ResponseEntity<>(coursesService.createCourse(courseRequestDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseRequestDto courseRequestDto , @PathVariable(name = "id") Long id) {
        logger.info("REST Controller to update course [{}] by id [{}]", courseRequestDto , id);
        return new ResponseEntity<>(coursesService.updateCourse(courseRequestDto , id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable(name = "id") Long id) {
        logger.info("REST Controller to delete course [{}]" , id);
        coursesService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}/{courseId}")
    public ResponseEntity<Void> enrollInCourse(@PathVariable(name = "userId") Long userId,
                                             @PathVariable(name = "courseId") Long courseId) {
        logger.info("REST Controller by user id [{}] to enroll in course [{}]" , userId , courseId);
        coursesService.enrollInCourse(userId , courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{courseId}")
    public ResponseEntity<Void> exitFromCourse(@PathVariable(name = "userId") Long userId,
                                             @PathVariable(name = "courseId") Long courseId) {
        logger.info("REST Controller by user id [{}] to exit from course [{}]" , userId , courseId);
        coursesService.exitFromCourse(userId , courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CourseDto>> getUserCourses(@PathVariable(name = "userId") Long userId) {
        logger.info("REST Controller to get user [{}] courses" , userId);
        return new ResponseEntity<>(coursesService.getUserCourses(userId), HttpStatus.OK);
    }

}
