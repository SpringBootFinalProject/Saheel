package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.CourseEnrollment;
import com.example.saheel.Model.User;
import com.example.saheel.Service.CourseEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saheel/courseEnrollment-enrollment")
public class CourseEnrollmentController {
    private final CourseEnrollmentService courseEnrollmentService;

    @GetMapping("/get--course-enrollments/{courseId}")
    public ResponseEntity<List<CourseEnrollment>> getAllCourseEnrollmentByStableOwner(@AuthenticationPrincipal User user, @PathVariable Integer courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseEnrollmentService.getAllCourseEnrollmentByStableOwner(user.getId(), courseId));
    }

    @PostMapping("/add-courseEnrollment-by-stable-owner/{courseId}")
    public ResponseEntity<ApiResponse> enrollToCourse(@AuthenticationPrincipal User user, @PathVariable Integer courseId) {
        courseEnrollmentService.enrollToCourse(user.getId(), courseId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Customer Enrolled successfully."));
    }
}
