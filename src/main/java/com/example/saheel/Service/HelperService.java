package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Course;
import com.example.saheel.Model.User;
import com.example.saheel.Repository.CourseRepository;
import com.example.saheel.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelperService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

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
}
