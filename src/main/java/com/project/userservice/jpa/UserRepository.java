package com.project.userservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUserId(String userId);

    UserEntity findByEmail(String username);
}
