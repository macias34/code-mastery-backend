package com.macias34.codemastery.course.repository;

import com.macias34.codemastery.course.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<LessonEntity,Integer> {
}
