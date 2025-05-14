
package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.CourseRepository;
import com.example.saheel.Repository.StableOwnerRepository;
import com.example.saheel.Repository.StableRepository;
import com.example.saheel.Repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final StableOwnerRepository stableOwnerRepository;
    private final StableRepository stableRepository;
    private final HelperService helperService;
    private final TrainerRepository trainerRepository;

    // ( #7 of 50 endpoints )
    public List<Course> getStableCourses(Integer stableId) {
        // Get the stable and check if it's in the database.
        Stable stable = getStableOrThrow(stableId);

        // Return the courses
        return courseRepository.findCoursesByStable(stable);
    }


    // ( #5 of 50 endpoints )
    public void addCourseByOwner(Integer stableOwnerId, Integer stableId, Integer trainerId, Course course) {
        // Get the stable owner and check if it's in the database.
        StableOwner stableOwner = getStableOwnerOrThrow(stableOwnerId);

        // Get the stable and check if it's in the database.
        Stable stable = getStableOrThrow(stableId);

        // Check if the stable belongs to the owner.
        checkIfStableBelongsToOwner(stable, stableOwner);

        // Get the trainer and check if it's in the database.
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null) throw new ApiException("Trainer not found.");
        if (!stable.getTrainers().contains(trainer))
            throw new ApiException("The trainer does not belongs to the stable.");
        // Assign the trainer to the course.
        course.setTrainer(trainer);

        if (!Boolean.TRUE.equals(stableOwner.getIsApproved())) {
            throw new ApiException("Your account is not approved. Please wait for admin approval.");
        }
        LocalDateTime start = course.getDate();
        LocalDateTime end = start.plusMinutes(course.getDurationInMinute());

        // Check if the trainer available.
        if (!courseRepository.findCourseByTrainerAndDateBetween(course.getTrainer(),
                start, end).isEmpty())
            throw new ApiException("Trainer not available.");

        // Add the stable to the course and save the object in the database.

        course.setStable(stable);
        courseRepository.save(course);
    }

    public void updateCourse(Integer stableOwnerId, Integer stableId, Integer courseId, Course course) {
        // Get the stable owner and check if it's in the database.
        StableOwner stableOwner = getStableOwnerOrThrow(stableOwnerId);

        // Get the stable and check if it's in the database.
        Stable stable = getStableOrThrow(stableId);

        // Check if the stable belongs to the owner.
        checkIfStableBelongsToOwner(stable, stableOwner);

        // Get the course and check if it's in the database.
        Course oldCourse = helperService.getCourseOrThrow(courseId);

        // Check if the course belongs to the stable.
        checkIfCourseBelongsToStable(stable, oldCourse);

        // Check if the trainer available.
        if (courseRepository.findCoursesByTrainer(course.getTrainer()).isEmpty())
            throw new ApiException("Trainer not available.");

        //Update the course.
        oldCourse.setName(course.getName());
        oldCourse.setCapacity(course.getCapacity());
        oldCourse.setDescription(course.getDescription());
        oldCourse.setDurationInMinute(course.getDurationInMinute());
        oldCourse.setDate(course.getDate());
        oldCourse.setNumberOfEnrolled(course.getNumberOfEnrolled());

        // Save
        courseRepository.save(oldCourse);
    }


    // ( #6 of 50 endpoints )
    public void cancelCourse(Integer stableOwnerId, Integer stableId, Integer courseId) {
        // Get the stable owner and check if it's in the database.
        StableOwner stableOwner = getStableOwnerOrThrow(stableOwnerId);

        // Get the stable and check if it's in the database.
        Stable stable = getStableOrThrow(stableId);

        // Check if the stable belongs to the owner.
        checkIfStableBelongsToOwner(stable, stableOwner);

        // Get the course and check if it's in the database.
        Course course = helperService.getCourseOrThrow(courseId);

        // Check if the course belongs to the stable.
        checkIfCourseBelongsToStable(stable, course);

        // Cancel the course
        course.setCourseCanceled(true);
        // Unlink the trainer
        course.setTrainer(null);

        // Change the status in the course enrollments object of this course.
        changeEnrollmentsCourseStatus(course.getCourseEnrollments());

        // Save
        courseRepository.save(course);
    }

    public List<Course> getAvailableCourses() {
        List<Course> courses = courseRepository.findAll();
        List<Course> availableCourses = new ArrayList<>();
        for (Course course : courses)
            if (course.getCourseEnrollments().size() < course.getCapacity() &&
                    course.getFinalEnrollmentDate().isAfter(LocalDateTime.now()))
                availableCourses.add(course);
        return availableCourses;
    }

    public String getTopRatedCourse() {
        List<Course> courses = courseRepository.findAll();
        // Check if there is courses on the system.
        if (courses.isEmpty()) return "There are no courses";

        // Get the top-rated trainer.
        Course topRatedCourse = findTopRatedCourse(courses);

        // Check if the all the trainers don't have ratings.
        if (topRatedCourse.getTotalNumberOfRatings() == 0) return "All the courses do not have a rating.";

        // Return the top-rated trainer.
        return "The top rated course is: " + topRatedCourse.getName() + " with a rating of: "
                + (topRatedCourse.getTotalRating() / topRatedCourse.getTotalNumberOfRatings()) + ".";
    }

    public Course findTopRatedCourse(List<Course> courses) {
        Course topRatedCourse = courses.get(0);
        boolean flag = false;
        for (Course course : courses) {
            // Handle division by 0.
            if (course.getTotalNumberOfRatings() == 0) continue;
            if (!flag) {
                topRatedCourse = course;
                flag = true;
            }
            if ((course.getTotalRating() / course.getTotalNumberOfRatings()) > (topRatedCourse.getTotalRating() / topRatedCourse.getTotalNumberOfRatings()))
                topRatedCourse = course;
        }
        return topRatedCourse;

    }

    public List<Course> getCoursesByTrainer(Integer trainerId) {
        // Get the trainer and check if it's in the database.
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null) throw new ApiException("Trainer not found.");

        // Return the courses.
        return courseRepository.findCoursesByTrainer(trainer);
    }

    public List<Course> getCoursesByDate(LocalDateTime dateTime) {
        return courseRepository.getCoursesByDateBetween(dateTime, dateTime.plusDays(1));
    }

    public void changeEnrollmentsCourseStatus(List<CourseEnrollment> courseEnrollments) {
        for (CourseEnrollment courseEnrollment : courseEnrollments) courseEnrollment.setCourseCanceled(true);
    }

    public StableOwner getStableOwnerOrThrow(Integer stableOwnerId) {
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwnerId);
        if (stableOwner == null) throw new ApiException("Stable owner not found.");
        return stableOwner;
    }

    public Stable getStableOrThrow(Integer stableId) {
        Stable stable = stableRepository.findStableById(stableId);
        if (stable == null) throw new ApiException("Stable not found.");
        return stable;
    }

    public void checkIfStableBelongsToOwner(Stable stable, StableOwner stableOwner) {
        if (!stableOwner.getStables().contains(stable)) throw new ApiException("Stable does not belong to the owner.");
    }

    public void checkIfCourseBelongsToStable(Stable stable, Course course) {
        if (!course.getStable().equals(stable)) throw new ApiException("Course does not belong to the stable.");
    }
}