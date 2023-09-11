package com.macias34.codemastery.course.entity;

import com.macias34.codemastery.order.entity.OrderEntity;
import com.macias34.codemastery.user.entity.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private String instructorName;
    private int participantsCount;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "course_category",
            joinColumns = { @JoinColumn(name = "courseId") },
            inverseJoinColumns = { @JoinColumn(name = "categoryId") }
    )
    private Set<CategoryEntity> categories = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
    private Set<UserEntity> users = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private List<ChapterEntity> chapters;

    @OneToMany(mappedBy = "course")
    private List<OrderEntity> orders;

    public CourseEntity(String name, double price, String instructorName, int participantsCount, Timestamp createdAt, Timestamp updatedAt, String description) {
        this.name = name;
        this.price = price;
        this.instructorName = instructorName;
        this.participantsCount = participantsCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.description = description;
    }
}
