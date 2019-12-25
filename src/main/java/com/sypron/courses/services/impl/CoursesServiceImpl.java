package com.sypron.courses.services.impl;

import com.sypron.courses.dao.CourseDao;
import com.sypron.courses.dao.UserDao;
import com.sypron.courses.exceptions.IntegrationException;
import com.sypron.courses.exceptions.MissingOrBadParamsException;
import com.sypron.courses.exceptions.NotFoundException;
import com.sypron.courses.models.dto.CourseDto;
import com.sypron.courses.models.dto.request.CourseRequestDto;
import com.sypron.courses.models.entities.Course;
import com.sypron.courses.models.entities.User;
import com.sypron.courses.services.CoursesService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoursesServiceImpl implements CoursesService {

    @Autowired
    CourseDao courseDao;

    @Autowired
    UserDao userDao;

    private static final Logger logger = LoggerFactory.getLogger(CoursesServiceImpl.class);

    @Override
    public List<CourseDto> getAllCourses(Long userId) {
        logger.info("getting all courses");
        List<Course> coursesList = courseDao.findAll();
        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : coursesList) {
            courseDtos.add(course.mapToCourseDto(userId));
        }
        return courseDtos;
    }

    @Override
    public CourseDto createCourse(CourseRequestDto courseRequestDto) {
        logger.info("creating course [{}]" , courseRequestDto);
        Course course = new ModelMapper().map(courseRequestDto , Course.class);
        try {
            this.courseDao.save(course);
        } catch (Exception e) {
            logger.error("error occurred during saving course" , e);
            throw new IntegrationException("ER_01_001--error occurred during saving course");
        }
        return course.mapToCourseDto();
    }

    @Override
    public CourseDto updateCourse(CourseRequestDto courseRequestDto, Long courseId) {
        logger.info("updating course [{}]" , courseId);
        Course course = getCourseById(courseId);
        course.setTitle(courseRequestDto.getTitle());
        course.setDescription(courseRequestDto.getDescription());
        course.setPrice(courseRequestDto.getPrice());
        course.setPeriod(courseRequestDto.getPeriod());
        course.setInstructorName(courseRequestDto.getInstructorName());
        try {
            this.courseDao.save(course);
        } catch (Exception e) {
            logger.error("error occurred during saving course" , e);
            throw new IntegrationException("ER_01_001--error occurred during saving course");
        }
        return course.mapToCourseDto();
    }

    @Override
    public void deleteCourse(Long courseId) {
        logger.info("deleting course [{}]" , courseId);
        Course course = getCourseById(courseId);
        courseDao.delete(course);
    }

    @Override
    public void enrollInCourse(Long userId, Long courseId) {
        User user = getUserById(userId);
        Course course = getCourseById(courseId);
        course.getUsersList().add(user);
        userDao.save(user);
    }

    @Override
    public void exitFromCourse(Long userId, Long courseId) {
        Course course = getCourseById(courseId);
        for (int i = 0 ; i < course.getUsersList().size() ; i++) {
            if (course.getUsersList().get(i).getId().equals(userId)) {
                course.getUsersList().remove(i);
            }
        }
        courseDao.save(course);
    }

    @Override
    public List<CourseDto> getUserCourses(Long userId) {
        User user = getUserById(userId);
        List<Course> courseList = user.getCoursesList();
        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courseList) {
            courseDtos.add(course.mapToCourseDto(userId));
        }
        return courseDtos;
    }

    private Course getCourseById(Long courseId) {
        if (courseId == null) {
            logger.error("courseId is null !!");
            throw new MissingOrBadParamsException("ER_06_001--courseId is null");
        }

        Course course = courseDao.findById(courseId).orElse(null);
        if (course == null) {
            logger.error("can`t find course with id [" + courseId + "] ");
            throw new NotFoundException("ER_05_002--Course not found");
        }
        return course;
    }

    private User getUserById(Long userId) {
        if (userId == null) {
            logger.error("userId is null !!");
            throw new MissingOrBadParamsException("ER_06_001--userId is null");
        }

        User user = userDao.findById(userId).orElse(null);
        if (user == null) {
            logger.error("can`t find user with id [" + userId + "] ");
            throw new NotFoundException("ER_05_003--User not found");
        }
        return user;
    }
}
