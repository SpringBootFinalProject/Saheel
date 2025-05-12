package com.example.saheel.Repository;

import com.example.saheel.Model.Course;

import com.example.saheel.Model.Stable;
import com.example.saheel.Model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findCourseById(Integer courseId);

    List<Course> findCoursesByStable(Stable stable);

    @Query("select c from Course c where c.trainer = ?1 and c.courseCanceled = false")
    List<Course> findCoursesByTrainer(Trainer trainer);


    List<Course> findCourseByTrainerAndDateBetween(Trainer trainer, LocalDateTime dateAfter, LocalDateTime dateBefore);

    List<Course> getCoursesByDateBetween(LocalDateTime dateAfter, LocalDateTime dateBefore);
}
