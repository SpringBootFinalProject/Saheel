package com.example.saheel.Repository;

import com.example.saheel.Model.Course;
import com.example.saheel.Model.CourseEnrollment;
import com.example.saheel.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Integer> {
    CourseEnrollment findCourseEnrollmentById(Integer courseEnrollment);

    CourseEnrollment findCourseEnrollmentByCourseAndCustomer(Course course, Customer customer);

    List<CourseEnrollment> findCourseEnrollmentByCourse(Course course);


}
