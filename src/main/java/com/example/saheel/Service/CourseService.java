package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Course;
import com.example.saheel.Model.Stable;
import com.example.saheel.Repository.CourseRepository;
import com.example.saheel.Repository.StableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    private final StableRepository stableRepository;

    //get Course by ID - Abeer
    public Course getCourseById(Integer course_Id){
        Course course = courseRepository.findCourseById(course_Id);
        if (course == null){
            throw new ApiException("Error : Course is not found");
        }
        return course;
    }

    // add Course - Abeer
    public void addCourse(Integer stable_Id , Course course){
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException("Error : stable is not fond");
        }
        courseRepository.save(course);
    }
    //update Course - Abeer
    public void updateCourse(Integer stable_Id ,Integer course_Id,Course course) {
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException("Error : stable is not fond");
        }

        Course oldCourse = courseRepository.findCourseById(course_Id);
        if (oldCourse == null) {
            throw new ApiException("Error: Course not found");
        }

        oldCourse.setName(course.getName());
        oldCourse.setDescription(course.getDescription());
        oldCourse.setCapacity(course.getCapacity());
        oldCourse.setNumberOfEnrolled(course.getNumberOfEnrolled());
        oldCourse.setDate(course.getDate());

        courseRepository.save(course);
    }

    //delete Course - Abeer
    public void deleteCourse(Integer course_Id) {
        Course course = courseRepository.findCourseById(course_Id);
        if (course == null) {
            throw new ApiException("Error: Course not found");
        }
        courseRepository.delete(course);
    }

}
