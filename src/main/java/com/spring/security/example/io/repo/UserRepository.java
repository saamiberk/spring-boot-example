package com.spring.security.example.io.repo;

import com.spring.security.example.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
}
