package com.example.saheel;

import com.example.saheel.Controller.StableController;
import com.example.saheel.Model.Stable;
import com.example.saheel.Service.StableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StableController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class StableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StableService stableService;

    private Stable stable;

    @BeforeEach
    void setUp() {
        stable = new Stable();
        stable.setId(1);
        stable.setName("Royal Stable");
    }

    @Test
    public void testGetAllStable() throws Exception {
        List<Stable> stableList = Arrays.asList(stable);
        Mockito.when(stableService.getOwnerHorses()).thenReturn(stableList);

        mockMvc.perform(get("/api/v1/saheel/stable/get-all-stable")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Royal Stable"));
    }
}
