package com.torloksz.arethium.repository;

import com.torloksz.arethium.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions,Long> {
    Optional<Questions> findQuestionsByAssessmentId(Long assessmentId);
}
