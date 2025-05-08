package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseReviewService {
    private final CourseReviewRepository courseReviewRepository;
    private final HelperService helperService;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CustomerRepository customerRepository;
    private final StableOwnerRepository stableOwnerRepository;

    public List<CourseReview> getAllCourseReviews(Integer courseId) {
        // Get the course and check if it's in the database.
        Course course = helperService.getCourseOrThrow(courseId);

        // Return the reviews
        return courseReviewRepository.findCourseReviewsByCourse(course);
    }

    public void ReviewCourseByCustomer(Integer customerId, Integer courseId, CourseReview courseReview) {
        // Get the customer and check if it's in the database.
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) throw new ApiException("Customer not found.");

        // Get the course and check if it's in the database.
        Course course = helperService.getCourseOrThrow(courseId);

        // Check if the customer enrolled in the course.
        helperService.checkIfCustomerEnrolled(course, customer);

        // Check if the customer reviewed this course.
        if (!courseReviewRepository.findCourseReviewsByCourseAndCustomer(course, customer).isEmpty())
            throw new ApiException("Customer can not make more than one review");


        // Set the customer
        courseReview.setCustomer(customer);

        // Set the course
        courseReview.setCourse(course);

        // Save the review.
        courseReviewRepository.save(courseReview);
    }

    public void updateReview(Integer customerId, Integer courseReviewId, CourseReview courseReview) {
        // Get the customer and check if it's in the database.
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) throw new ApiException("Customer not found.");

        // Get the courseReview and check if it's in the database.
        CourseReview oldCourseReview = courseReviewRepository.findCourseReviewById(courseReviewId);

        // Get the course and check if it's in the database.
        Course course = helperService.getCourseOrThrow(courseReview.getCourse().getId());

        // Get the CourseEnrollment Object using the course and the customer.
        CourseEnrollment courseEnrollment = courseEnrollmentRepository.findCourseEnrollmentByCourseAndCustomer(course, customer);

        // Check if the customer enrolled in the course * if the courseEnrollment is null than the customer has not attended this course.
        if (courseEnrollment == null) throw new ApiException("Customer has not attended this course.");

        // Update
        oldCourseReview.setRating(courseReview.getRating());
        oldCourseReview.setDescription(courseReview.getDescription());

        // Save the updated review.
        courseReviewRepository.save(oldCourseReview);
    }

}