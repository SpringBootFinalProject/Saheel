package com.example.saheel.Controller;

import com.example.saheel.Model.Course;
import com.example.saheel.Service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aip/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // Get course by ID - Abeer
    @GetMapping("/get/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    // Add new course - Abeer
    @PostMapping("/add/{stable_Id}")
    public ResponseEntity<String> addCourse(@PathVariable Integer stable_Id, @RequestBody Course course) {
        courseService.addCourse(stable_Id, course);
        return ResponseEntity.ok("Course added successfully");
    }

    // Update course - Abeer
    @PutMapping("/update/{stable_Id}/{course_Id}")
    public ResponseEntity<String> updateCourse(@PathVariable Integer stable_Id, @PathVariable Integer course_Id, @RequestBody Course course) {
        courseService.updateCourse(stable_Id, course_Id, course);
        return ResponseEntity.ok("Course updated successfully");
    }

    // Delete course - Abeer
    @DeleteMapping("/delete/{course_Id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer course_Id) {
        courseService.deleteCourse(course_Id);
        return ResponseEntity.ok("Course deleted successfully");
    }

}
