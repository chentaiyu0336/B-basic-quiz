package com.example.demo.service;

import com.example.demo.domain.Education;
import com.example.demo.domain.User;
import com.example.demo.exception.UserNotExistException;
import com.example.demo.repository.EducationRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EducationRepository educationRepository;

    private User user;

    @BeforeEach
    public void setup() {
        userService = new UserService(userRepository, educationRepository);
        user = User.builder()
                .id(1L)
                .name("Panda")
                .age(24L)
                .avatar("http://...")
                .description("A good guy.")
                .build();
    }

    @Nested
    class UserExist {
        @Nested
        class GetUser {
            @Test
            public void should_return_user_when_id_exist() {
                when(userRepository.findById(1L)).thenReturn(Optional.of(user));
                User foundUser = userService.getUserById(1L);
                assertThat(foundUser).isEqualTo(user);
            }
        }

        @Nested
        class AddUser {
            private User newUser;
            @BeforeEach
            public void beforeEach() {
                newUser = User.builder()
                        .name("Panda2")
                        .age(24L)
                        .avatar("http://...")
                        .description("A good guy.")
                        .build();
            }
            @Test
            public void should_add_user_then_return_id() {
                when(userRepository.save(newUser)).thenReturn(newUser);
                Long id = userService.addUser(newUser);
                assertThat(id).isEqualTo(newUser.getId());
            }
        }


        @Nested
        class GetEducation {
            private List<Education> educationList = new ArrayList<>();
            private Education education1;
            private Education education2;

            @BeforeEach
            public void beforeEach() {
                education1 = Education.builder()
                        .description("Aspernatur, mollitia, quos")
                        .year(2009L)
                        .title("First level graduation in Graphic Design")
                        .user(user)
                        .build();
                education2 = Education.builder()
                        .description("Eos, explicabo, nam")
                        .year(2010L)
                        .title( "Secondary school specializing in artistic")
                        .user(user)
                        .build();
                educationList.add(education1);
                educationList.add(education2);
            }

            @Test
            public void should_return_education_list_when_user_exist() {
                when(userRepository.findById(1L)).thenReturn(Optional.of(user));
                User foundUser = userService.getUserById(1L);
                when(educationRepository.findAllByUser(foundUser)).thenReturn(educationList);
                List<Education> eduList=userService.getEducationList(1L);
                assertThat(eduList).isEqualTo(educationList);
            }
        }
    }

    @Nested
    class UserNotExist {
        @Test
        public void should_throw_error_when_id_not_exist() {
            when(userRepository.findById(222L)).thenReturn(Optional.empty());
            UserNotExistException throwException = assertThrows(UserNotExistException.class, () -> {
                userService.getUserById(222L);
            });
            assertThat(throwException.getMessage()).containsSequence("user not found");
        }
    }



}
