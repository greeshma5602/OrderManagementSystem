package com.example.user_service.Repository;


import com.example.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
     Optional<User> findByEmail(String Email);

    @Override
    Optional<User> findById(Long userid);
   Optional<User> findByName(String name);


}
