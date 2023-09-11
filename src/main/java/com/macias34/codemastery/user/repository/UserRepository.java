package com.macias34.codemastery.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macias34.codemastery.user.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
