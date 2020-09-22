package com.example.demo.repository;

import com.example.demo.domain.Education;
import com.example.demo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class EducationRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void should_return_education_list_when_id_exists() {
        User user;
        Education education;
        entityManager.persistAndFlush(user = User.builder()
                .name("Panda")
                .age(24L)
                .avatar("http://...")
                .description("A good guy.")
                .build());
        entityManager.persistAndFlush(education = Education.builder()
                .user(user)
                .year(2010L)
                .description("Eos, explicabo, nam")
                .title( "Secondary school specializing in artistic")
                .build());
        List<Education> educationList = educationRepository.findAllByUser(user);

        assertThat(educationList.size()).isEqualTo(1);
        assertThat(educationList.get(0)).isEqualTo(education);
    }
}
