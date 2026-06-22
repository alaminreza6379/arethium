package com.torloksz.arethium.repository;

import com.torloksz.arethium.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.modules WHERE u.email = :email")
    Optional<Users> findByEmailWithModules(@Param("email") String email);
    Optional<Users> findByEmail(String email);
}
