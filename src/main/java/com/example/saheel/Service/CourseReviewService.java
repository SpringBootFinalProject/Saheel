package com.example.saheel.Service;

import com.example.saheel.Model.Course;
import com.example.saheel.Model.CourseReview;
import com.example.saheel.Model.User;
import com.example.saheel.Repository.CourseRepository;
import com.example.saheel.Repository.CourseReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseReviewService {
    private final CourseReviewRepository courseReviewRepository;
    private final HelperService helperService;

    public List<CourseReview> getAllCourseReviewsByAdmin(String adminUsername, Integer courseId) {
        // Check if the admin exists in the database.
        helperService.getAdminOrThrow(adminUsername);

        // Get the course and check if it's in the database.
        Course course = helperService.getCourseOrThrow(courseId);

        // Return the courses
        return courseReviewRepository.findCourseReviewsByCourse(course);
    }

    public void ReviewCourseByCustomer(Integer customerId, Integer courseId){
        // Get the customer and check if it's in the database.

        // Get the course and check if it's in the database.

        // Check if the customer enrolled in the course

        // Create the review

        // Save the review.
    }

}
