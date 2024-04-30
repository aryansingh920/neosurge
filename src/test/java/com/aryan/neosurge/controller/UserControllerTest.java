package com.aryan.neosurge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.aryan.neosurge.model.User;
import com.aryan.neosurge.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void registerUser_WithValidUser_ReturnsSuccessMessage() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(userService.isUserExists(user.getUsername())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("User registered successfully"));
    }

    @Test
    void registerUser_WithExistingUser_ReturnsConflictMessage() throws Exception {
        User user = new User();
        user.setUsername("existingUser");
        user.setPassword("testPassword");

        when(userService.isUserExists(user.getUsername())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string("User already exists"));
    }

}
