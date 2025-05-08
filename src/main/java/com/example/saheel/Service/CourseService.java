
package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Course;
import com.example.saheel.Model.Stable;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Repository.CourseRepository;
import com.example.saheel.Repository.StableOwnerRepository;
import com.example.saheel.Repository.StableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final StableOwnerRepository stableOwnerRepository;
    private final StableRepository stableRepository;
    private final HelperService helperService;

    //#5
    public List<Course> getStableCourses(Integer stableOwnerId, Integer stableId) {
        // Get the stable owner and check if it's in the database.
        StableOwner stableOwner = getStableOwnerOrThrow(stableOwnerId);

        // Get the stable and check if it's in the database.
        Stable stable = getStableOrThrow(stableId);

        // Check if the stable belongs to the owner.
        checkIfStableBelongsToOwner(stable, stableOwner);

        // Return the courses
        return courseRepository.findCoursesByStable(stable);
    }

    //#6
    public void addCourseByOwner(Integer stableOwnerId, Integer stableId, Course course) {
        // Get the stable owner and check if it's in the database.
        StableOwner stableOwner = getStableOwnerOrThrow(stableOwnerId);

        // Get the stable and check if it's in the database.
        Stable stable = getStableOrThrow(stableId);

        // Check if the stable belongs to the owner.
        checkIfStableBelongsToOwner(stable, stableOwner);
        // Check if the trainer available.

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


        //Update the course.
        oldCourse.setName(course.getName());
        oldCourse.setCapacity(course.getCapacity());
        oldCourse.setDescription(course.getDescription());
        oldCourse.setDurationInMinute(course.getDurationInMinute());
        oldCourse.setDate(course.getDate());

        // Save
        courseRepository.save(oldCourse);
    }

    //#7
    // Cancel, add attribute to both course and enrollment
    public void deleteCourse(Integer stableOwnerId, Integer stableId, Integer courseId) {
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

        //Delete
        courseRepository.delete(course);
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
