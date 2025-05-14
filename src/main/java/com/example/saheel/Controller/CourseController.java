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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saheel/course")
public class CourseController {

    private final CourseService courseService;

    // ( #12 of 50 endpoints ) Ayman
    @GetMapping("/get-stable-courses/{stableId}")
    public ResponseEntity<List<Course>> getStableCourses(@PathVariable Integer stableId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getStableCourses(stableId));
    }

    // ( #13 of 50 endpoints ) Ayman
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

    // ( #14 of 50 endpoints ) Ayman
    @PutMapping("/cancel-course/{stableId}/{courseId}")
    public ResponseEntity<ApiResponse> cancelCourse(@AuthenticationPrincipal User user, @PathVariable Integer stableId, @PathVariable Integer courseId) {
        courseService.cancelCourse(user.getId(), stableId, courseId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Course cancel successfully."));
    }

    // ( #13 of 50 endpoints ) Ayman
    @GetMapping("/get-available-courses")
    public ResponseEntity<List<Course>> getAvailableCourses(){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAvailableCourses());
    }

//     ( #14 of 50 endpoints ) Ayman
    @GetMapping("/get-top-rated-course")
    public ResponseEntity<ApiResponse> getTopRatedCourse(){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(courseService.getTopRatedCourse()));
    }

//     ( #15 of 50 endpoints ) Ayman
    @GetMapping("/get-courses-by-trainer/{trainerId}")
    public ResponseEntity<List<Course>> getCoursesByTrainer(@PathVariable Integer trainerId){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCoursesByTrainer(trainerId));
    }

    //    ( #16 of 50 endpoints ) Ayman
    @GetMapping("/get-courses-by-date")
    public ResponseEntity<List<Course>> getCoursesByDate(@RequestBody LocalDateTime dateTime){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCoursesByDate(dateTime));
    }

}