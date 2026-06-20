package com.torloksz.arethium.repository;

import com.torloksz.arethium.entity.Goals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoalsRepository extends JpaRepository<Goals,Long> {
    Optional<Goals> findByUsers_Email(String usersEmail);
}
