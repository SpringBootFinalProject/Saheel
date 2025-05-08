package com.example.saheel.Repository;

import com.example.saheel.Model.Course;
import com.example.saheel.Model.CourseReview;
import com.example.saheel.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReview, Integer> {
    CourseReview findCourseReviewById(Integer courseReviewId);

    List<CourseReview> findCourseReviewsByCourse(Course course);
    List<CourseReview> findCourseReviewsByCourseAndCustomer(Course course, Customer customer);
}
