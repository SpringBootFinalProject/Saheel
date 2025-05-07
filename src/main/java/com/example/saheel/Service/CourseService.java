package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Breeder;
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
            throw new ApiException(" Error : Course is not found");
        }
        return course;
    }

    // add Course - Abeer
    public void addCourse(Integer stable_Id , Course course){
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException(" Error : stable is not fond");
        }
        courseRepository.save(course);
    }

}
