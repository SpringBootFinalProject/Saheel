package com.example.saheel;

import com.example.saheel.Controller.CourseReviewController;
import com.example.saheel.Model.CourseReview;
import com.example.saheel.Model.User;
import com.example.saheel.Service.CourseReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = CourseReviewController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class CourseReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseReviewService courseReviewService;

    private CourseReview courseReview;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("customer1");

        courseReview = new CourseReview();
        courseReview.setId(1);
        courseReview.setRating(5);
    }

    @Test
    public void testGetCourseReviews() throws Exception {
        List<CourseReview> reviewList = Collections.singletonList(courseReview);
        Mockito.when(courseReviewService.getAllCourseReviews(1)).thenReturn(reviewList);

        mockMvc.perform(get("/api/v1/saheel/course-review/get-stable-course-reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rating").value(5));
    }

    @Test
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    public void testReviewCourseByCustomer() throws Exception {
        mockMvc.perform(post("/api/v1/saheel/course-review/review-course-by-customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(courseReview)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Review added successfully."));
    }

    @Test
    @WithMockUser(username = "customer1", roles = {"CUSTOMER"})
    public void testUpdateCourseReview() throws Exception {
        mockMvc.perform(put("/api/v1/saheel/course-review/update-course-review/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(courseReview)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Review updated successfully."));
    }
}
