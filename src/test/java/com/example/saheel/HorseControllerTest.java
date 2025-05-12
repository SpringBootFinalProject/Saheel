package com.example.saheel;

import com.example.saheel.Controller.HorseController;
import com.example.saheel.Model.Horse;
import com.example.saheel.Model.User;
import com.example.saheel.Service.HorseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = HorseController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class HorseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    HorseService horseService;

    private User user;
    private Horse horse;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("owner1");

        horse = new Horse();
        horse.setId(1);
        horse.setName("Thunder");
    }

//    @Test
//    @WithMockUser(username = "owner1", roles = {"HORSEOWNER"})
//    public void testGetOwnerHorses() throws Exception {
//        List<Horse> horses = Collections.singletonList(horse);
//
//        Mockito.when(horseService.getOwnerHorses(1)).thenReturn(horses);
//
//        mockMvc.perform(get("/api/v1/saheel/horse/get-owner-horses")
//                        .requestAttr("user", user)).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("Thunder"));
//
//    }
}
