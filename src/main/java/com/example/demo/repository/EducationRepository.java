package com.example.demo.repository;

import com.example.demo.domain.Education;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education,Long> {
    List<Education> findAllByUser(User user);
}
