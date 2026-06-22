package com.torloksz.arethium.repository;

import com.torloksz.arethium.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment,Long> {
    Optional<Assessment> findAssessmentsByModulesId(Long modulesId);

    Assessment getAssessmentByModulesId(Long modulesId);
}
