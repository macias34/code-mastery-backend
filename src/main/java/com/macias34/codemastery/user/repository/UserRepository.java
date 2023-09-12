package com.macias34.codemastery.user.repository;

import com.macias34.codemastery.user.entity.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	Optional<UserEntity> findByUsername(String username);

	Boolean existsByUsername(String username);
}
