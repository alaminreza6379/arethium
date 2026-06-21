package com.torloksz.arethium.repository;

import com.torloksz.arethium.entity.Modules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModulesRepository extends JpaRepository<Modules,Long> {
    Optional<Modules> findModulesByUsers_Email(String usersEmail);

}
