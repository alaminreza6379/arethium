package com.torloksz.arethium.repository;

import com.torloksz.arethium.entity.Interview;
import com.torloksz.arethium.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewRepository
        extends JpaRepository<Interview, Long> {

    Optional<Interview> findByUsers(Users users);
}