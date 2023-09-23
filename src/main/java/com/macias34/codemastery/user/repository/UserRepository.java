package com.macias34.codemastery.user.repository;

import com.macias34.codemastery.course.entity.CourseEntity;
import com.macias34.codemastery.course.model.CourseFilter;
import com.macias34.codemastery.user.entity.UserEntity;

import java.util.Optional;

import com.macias34.codemastery.user.model.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {
	Optional<UserEntity> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
