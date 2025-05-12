package com.example.saheel;


import com.example.saheel.Controller.CourseController;
import com.example.saheel.Model.Course;
import com.example.saheel.Service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CourseController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class CourseControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean

    private CourseService courseService;

    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1);
        course.setName("Jumping Basics");
        course.setCapacity(10);
        course.setDescription("Learn how to jump obstacles");
    }

    @Test
    public void testGetStableCourses() throws Exception {
        Integer stableId = 1;
        Mockito.when(courseService.getStableCourses(stableId)).thenReturn(List.of(course));

        mockMvc.perform(get("/api/v1/saheel/course/get-stable-courses/{stableId}", stableId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jumping Basics"));
    }

    @Test
    public void testGetAvailableCourses() throws Exception {

        Mockito.when(courseService.getAvailableCourses()).thenReturn(List.of(course));

        mockMvc.perform(get("/api/v1/saheel/course/get-available-courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jumping Basics"));
    }

}
