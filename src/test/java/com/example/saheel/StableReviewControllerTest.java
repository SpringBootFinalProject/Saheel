//package com.example.saheel;
//
//import com.example.saheel.Controller.StableReviewController;
//import com.example.saheel.Model.StableReview;
//import com.example.saheel.Service.StableReviewService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(value = StableReviewController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
//public class StableReviewControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private StableReviewService stableReviewService;
//
//    private StableReview review;
//
//    @BeforeEach
//    void setUp() {
//        review = new StableReview();
//        review.setRating(5);
//        review.setDescription("Excellent");
//    }
//
//    @Test
//    @WithMockUser(username = "testUser", roles = {"OWNER"})
//    public void testGetReviewsByStable() throws Exception {
//        List<StableReview> reviews = Collections.singletonList(review);
//        Mockito.when(stableReviewService.getReviewsByStable(1)).thenReturn(reviews);
//
//        mockMvc.perform(get("/api/v1/saheel/stableReview/stables/1/reviews"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].rating").value(5))
//                .andExpect(jsonPath("$[0].comment").value("Excellent"));
//    }
//}
