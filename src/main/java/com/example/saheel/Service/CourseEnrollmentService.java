package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentService {
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final HelperService helperService;
    private final StableOwnerRepository stableOwnerRepository;
    private final CustomerRepository customerRepository;
    private final CourseRepository courseRepository;
    private final InvoiceRepository invoiceRepository;
    private final StableRepository stableRepository;


    // ( #2 of 50 endpoints )
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

    // ( #3 of 50 endpoints )
    public void enrollToCourse(Integer customerId, Integer courseId) {
        // Get the owner object and check if it's in the database.
        Customer customer = getCustomerOrThrow(customerId);

        // Get the course and check if it's in the database.
        Course course = helperService.getCourseOrThrow(courseId);

        if (course.getTotalRating() == null) {
            course.setTotalRating(0.0);
        }
        if (course.getTotalNumberOfRatings() == null) {
            course.setTotalNumberOfRatings(0.0);
        }
        // Check if the final enrollment date.
        if (!course.getFinalEnrollmentDate().isAfter(LocalDateTime.now()))
            throw new ApiException("The course enrollment date has passed.");

        // Check the course capacity.
        if (course.getNumberOfEnrolled() > course.getCapacity())
            throw new ApiException("The course has reached it's full capacity.");

        // Check if the customer has already enrolled in the course.
        CourseEnrollment oldCourseEnrollment = courseEnrollmentRepository.findCourseEnrollmentByCourseAndCustomer(course, customer);

        // If the courseEnrollment is null than the customer has not attended this course.
        if (oldCourseEnrollment != null) throw new ApiException("Customer has attended this course.");

        // Create the courseEnrollmentObject.
        CourseEnrollment courseEnrollment = new CourseEnrollment(null, course.getDate(), course.getPrice(),
                course.getDurationInMinute(), course.getDate().minusDays(1), false, false, false, customer, course, null);



        // Create the invoice.
        createInvoice(customer, courseEnrollment, courseEnrollment.getCourse().getPrice());

        // Save the object
        courseEnrollmentRepository.save(courseEnrollment);
    }

//
    public void cancelEnrollment(Integer customerId, Integer courseEnrollmentId) {
        //Get the customer
        Customer customer = getCustomerOrThrow(customerId);

        // Get the course enrollment
        CourseEnrollment courseEnrollment = getCourseEnrollmentOrThrow(courseEnrollmentId);

        // Get the course
        Course course = getCourseOrThrow(courseEnrollment.getCourse().getId());

        // check if the course belongs to the customer.
        if (!courseEnrollment.getCustomer().equals(customer))
            throw new ApiException("The course enrollment does not belongs to the customer.");


        if(courseEnrollment.getInvoice().getStatus().equals("paid")){
            throw new ApiException("Paid enrollments can not be canceled");
        }
        // Check if the cancellation date passed.
        if (courseEnrollment.getLastCancellationDate().isAfter(LocalDateTime.now()))
            throw new ApiException("The cancellation date has passed.");

        // Cancel the enrollment.
        courseEnrollment.setEnrollmentCanceled(true);

        // Save
        courseEnrollmentRepository.save(courseEnrollment);
    }

    public List<CourseEnrollment> getCanceledEnrollments(Integer stableOwnerId, Integer stableId) {
        // Get stable owner and check if it's in the database.
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwnerId);
        if (stableOwner == null) throw new ApiException("Stable owner not found.");

        // Get stable and check if it's in the database.
        Stable stable = stableRepository.findStableById(stableId);
        if (stable == null) throw new ApiException("Stable not found.");

        // Check if the stable belongs to the owner.
        if (!stableOwner.getStables().contains(stable))
            throw new ApiException("The stable does not belongs to the owner.");

        List<CourseEnrollment> courseEnrollments = courseEnrollmentRepository.findAll();
        List<CourseEnrollment> canceledEnrollments = new ArrayList<>();

        for (CourseEnrollment courseEnrollment : courseEnrollments)
            if (courseEnrollment.getCourse().getStable().equals(stable) && courseEnrollment.getEnrollmentCanceled())
                canceledEnrollments.add(courseEnrollment);
        return canceledEnrollments;
    }

    public Customer getCustomerOrThrow(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) throw new ApiException("Customer not found");
        return customer;

    }

    public Course getCourseOrThrow(Integer courseId) {
        Course course = courseRepository.findCourseById(courseId);
        if (course == null) throw new ApiException("Course not found");
        return course;
    }

    public CourseEnrollment getCourseEnrollmentOrThrow(Integer courseEnrollmentId) {
        CourseEnrollment courseEnrollment = courseEnrollmentRepository.findCourseEnrollmentById(courseEnrollmentId);
        if (courseEnrollment == null) throw new ApiException("Course Enrollment not found.");
        return courseEnrollment;
    }

    public void createInvoice(Customer customer, CourseEnrollment courseEnrollment, double price) {
        EnrollmentInvoice invoice = new EnrollmentInvoice(null, "No Payment From The Customer", "pending", price, LocalDateTime.now(), courseEnrollment, customer);
        invoiceRepository.save(invoice);
    }
}