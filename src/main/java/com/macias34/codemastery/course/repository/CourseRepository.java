package com.macias34.codemastery.course.repository;

import com.macias34.codemastery.course.entity.CourseEntity;
import com.macias34.codemastery.course.model.CourseFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,Integer> {
    @Query("SELECT c FROM CourseEntity c INNER JOIN c.categories cat WHERE (:#{#courseFilter.name} is null OR c.name ilike %:#{#courseFilter.name}%)" +
            "AND (:#{#courseFilter.minPrice} is null OR c.price >= :#{#courseFilter.minPrice})" +
            "AND (:#{#courseFilter.maxPrice} is null OR c.price <= :#{#courseFilter.maxPrice})" +
            "AND (:#{#courseFilter.minParticipantsCount} is null OR c.participantsCount >= :#{#courseFilter.minParticipantsCount})" +
            "AND (:#{#courseFilter.categoryId} = 0 OR cat.id = :#{#courseFilter.categoryId})")
    Page<CourseEntity> searchCourseEntitiesByFilters(@Param("courseFilter")CourseFilter courseFilter, Pageable paging);
}
