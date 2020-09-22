package com.example.demo.controller;

import com.example.demo.domain.Education;
import com.example.demo.domain.User;
import com.example.demo.exception.UserNotExistException;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureJsonTesters
public class UserControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<User> userJson;
    @Autowired
    private JacksonTester<Education> educationJson;

    private User user;
    private Education education;

    @BeforeEach
    public void beforeEach() {
        user = User.builder()
                .id(1L)
                .name("Panda")
                .age(24L)
                .avatar("http://...")
                .description("A good guy.")
                .build();
        education = Education.builder()
                .user(user)
                .year(2005L)
                .title("Secondary school specializing in artistic")
                .description("Eos, explicabo, nam, tenetur")
                .build();
    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(userService);
    }

    @Nested
    class GetUser {

        @Nested
        class WhenUserIdExists {

            @Test
            public void should_return_user_by_id_with_jsonPath() throws Exception {
                when(userService.getUserById(1L)).thenReturn(user);

                mockMvc.perform(get("/users/{id}", 1L))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.name", is("Panda")));

                verify(userService).getUserById(1L);
            }

            @Test
            public void should_return_user_by_id_with_jacksontester() throws Exception {
                when(userService.getUserById(1L)).thenReturn(user);

                MockHttpServletResponse response = mockMvc.perform(get("/users/{id}", 1L))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

                assertThat(response.getContentAsString()).isEqualTo(
                        userJson.write(user).getJson());

                verify(userService).getUserById(1L);
            }
        }

        @Nested
        class WhenUserIdNotExisted {

            @Test
            public void should_return_NOT_FOUND() throws Exception {
                when(userService.getUserById(1L)).thenThrow(new UserNotExistException("user not found"));

                mockMvc.perform(get("/users/{id}", 1L))
                        .andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.message", containsString("user not found")));

                verify(userService).getUserById(1L);
            }
        }
    }

    @Nested
    class AddUser {

        private User newUser;

        @BeforeEach
        public void beforeEach() {
            newUser = User.builder()
                    .name("KAMIL")
                    .age(24L)
                    .avatar("http://...")
                    .description("Lorem ipsum dolor sit")
                    .build();
        }

        @Nested
        class WhenRequestIsValid {

            @Test
            public void should_create_new_user_and_return_its_id() throws Exception {
                when(userService.addUser(newUser)).thenReturn(2L);

                MockHttpServletRequestBuilder requestBuilder = post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson.write(newUser).getJson());

                MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
                verify(userService).addUser(newUser);
            }
        }
    }

    @Nested
    class CreateEduction {
        private Education newEducation;

        @BeforeEach
        public void beforeEach() {
            newEducation = Education.builder()
                    .user(user)
                    .year(2009L)
                    .title("First level graduation in Graphic Design")
                    .description("Aspernatur, mollitia, quos maxime")
                    .build();
        }

        @Nested
        class WhenRequestIsValid {

            @Test
            public void should_create_new_education() throws Exception {

                MockHttpServletRequestBuilder requestBuilder = post("/users/{id}/educations", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(educationJson.write(newEducation).getJson());

                MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
            }
        }
    }

    @Nested
    class GetEduction {

        @Nested
        class WhenEducationExists {
            private List<Education> educationList = new ArrayList<>();

            @BeforeEach
            public void beforeEach() {
                educationList.add(education);
            }

            @Test
            public void should_return_educations_by_id_with_jsonPath() throws Exception {
                when(userService.getEducationList(1L)).thenReturn(educationList);

                mockMvc.perform(get("/users/{id}/educations", 1L))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(1)));

                verify(userService).getEducationList(1L);
            }
        }
    }
}
