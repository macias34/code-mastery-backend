package com.macias34.codemastery.user.repository;

import com.macias34.codemastery.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
