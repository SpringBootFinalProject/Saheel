package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Course;
import com.example.saheel.Model.CourseEnrollment;
import com.example.saheel.Model.Customer;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Repository.CourseEnrollmentRepository;
import com.example.saheel.Repository.CustomerRepository;
import com.example.saheel.Repository.StableOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentService {
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final HelperService helperService;
    private StableOwnerRepository stableOwnerRepository;
    private final CustomerRepository customerRepository;

    public List<CourseEnrollment> getAllCourseEnrollmentByStableOwner(Integer stableOwnerId, Integer courseId) {
        // Get the owner object and check if it's in the database.
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwnerId);
        if (stableOwner == null) throw new ApiException("Owner not found.");

        // Get the course and check if it's in the database.
        Course course = helperService.getCourseOrThrow(courseId);

        // Check if the course belongs to the owner
        if (!stableOwner.getStables().contains(course.getStable()))
            throw new ApiException("The course does not belong to the owner.");

        // Return the enrollments
        return courseEnrollmentRepository.findCourseEnrollmentByCourse(course);
    }

    public void enrollToCourse(Integer customerId, Integer courseId) {
        // Get the owner object and check if it's in the database.
        Customer customer = getCustomerOrThrow(customerId);

        // Get the course and check if it's in the database.
        Course course = helperService.getCourseOrThrow(courseId);

        // Check if the final enrollment date.
        if (course.getFinalEnrollmentDate().isAfter(LocalDateTime.now()))
            throw new ApiException("The course enrollment date has passed.");

        // Check the course capacity.
        if (course.getNumberOfEnrolled() > course.getCapacity())
            throw new ApiException("The course has reached it's full capacity.");

        // Check if the customer has already enrolled in the course.
        helperService.checkIfCustomerEnrolled(course, customer);

        // Create the courseEnrollmentObject.
        CourseEnrollment courseEnrollment = new CourseEnrollment(null, course.getDate(), course.getPrice(), course.getDurationInMinute(), customer, course);

        // Save the object
        courseEnrollmentRepository.save(courseEnrollment);
    }

    public Customer getCustomerOrThrow(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) throw new ApiException("Customer not found");
        return customer;

    }
}
