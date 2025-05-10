package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.Course;
import com.example.saheel.Model.User;
import com.example.saheel.Service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saheel/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/get-stable-courses/{stableId}")
    public ResponseEntity<List<Course>> getStableCourses(@PathVariable Integer stableId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getStableCourses(stableId));
    }

    @PostMapping("/add-course-by-stable-owner/{stableId}/{trainerId}")
    public ResponseEntity<ApiResponse> addCourseByOwner(@AuthenticationPrincipal User user, @PathVariable Integer stableId, @PathVariable Integer trainerId, @RequestBody Course course) {
        courseService.addCourseByOwner(user.getId(), stableId, trainerId, course);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Course added successfully."));
    }

    @PutMapping("/update-course/{stableId}/{courseId}")
    public ResponseEntity<ApiResponse> updateCourse(@AuthenticationPrincipal User user, @RequestBody Course course,
                                                    @PathVariable Integer stableId, @PathVariable Integer courseId) {
        courseService.updateCourse(user.getId(), stableId, courseId, course);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Course updated successfully."));
    }

    @DeleteMapping("/delete-course/{stableId}/{courseId}")
    public ResponseEntity<ApiResponse> removeCourse(@AuthenticationPrincipal User user, @PathVariable Integer stableId, @PathVariable Integer courseId) {
        courseService.cancelCourse(user.getId(), stableId, courseId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Course deleted successfully."));
    }

    @GetMapping("/get-available-courses")
    public ResponseEntity<List<Course>> getAvailableCourses(){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAvailableCourses());
    }
}