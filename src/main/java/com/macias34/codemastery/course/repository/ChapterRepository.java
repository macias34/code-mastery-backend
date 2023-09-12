package com.macias34.codemastery.course.repository;

import com.macias34.codemastery.course.entity.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<ChapterEntity,Integer> {
}
