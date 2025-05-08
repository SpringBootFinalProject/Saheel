package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Course;
import com.example.saheel.Model.CourseEnrollment;
import com.example.saheel.Model.Customer;
import com.example.saheel.Model.User;
import com.example.saheel.Repository.CourseEnrollmentRepository;
import com.example.saheel.Repository.CourseRepository;
import com.example.saheel.Repository.CustomerRepository;
import com.example.saheel.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelperService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CustomerRepository customerRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;

    public User getAdminOrThrow(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) throw new ApiException("Admin not found.");
        return user;
    }

    public Course getCourseOrThrow(Integer courseId) {
        Course course = courseRepository.findCourseById(courseId);
        if (course == null) throw new ApiException("Course not found.");
        return course;
    }

    public User getCustomerOrThrow(Integer customerId) {
        User user = userRepository.findUserById(customerId);
        if (user == null) throw new ApiException("Customer not found.");
        return user;
    }

    public CourseEnrollment checkIfCustomerEnrolled(Course course, Customer customer) {
        CourseEnrollment courseEnrollment = courseEnrollmentRepository.findCourseEnrollmentByCourseAndCustomer(course, customer);

        // If the courseEnrollment is null than the customer has not attended this course.
        if (courseEnrollment == null) throw new ApiException("Customer has not attended this course.");
        return courseEnrollment;
    }


}
