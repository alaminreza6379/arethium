package com.torloksz.arethium.repository;

import com.torloksz.arethium.entity.Modules;
import com.torloksz.arethium.entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModulesRepository extends JpaRepository<Modules,Long> {
    List<Modules> findByUsersId(Long users_id);


    @Transactional
    void deleteByUsers(Users users);
}
