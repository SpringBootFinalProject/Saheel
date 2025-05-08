package com.example.saheel.Repository;

import com.example.saheel.Model.Course;

import com.example.saheel.Model.Stable;
import com.example.saheel.Model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findCourseById(Integer courseId);

    List<Course> findCoursesByStable(Stable stable);

    List<Course> findCoursesByTrainer(Trainer trainer);
}
