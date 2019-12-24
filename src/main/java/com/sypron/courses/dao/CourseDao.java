package com.sypron.courses.dao;

import com.sypron.courses.models.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends JpaRepository<Course , Long> {
}
