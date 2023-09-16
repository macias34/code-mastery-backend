package com.macias34.codemastery.course.entity;

import com.macias34.codemastery.order.entity.OrderEntity;
import com.macias34.codemastery.user.entity.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
    @Column(name = "avatar_file_extension")
    private String avatarFileExtension;
    private double price;
    @Column(name = "instructor_name")
    private String instructorName;
    @Column(name = "participants_count")
    private int participantsCount;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "course_category",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<CategoryEntity> categories = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<PropertyEntity> properties = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
    private Set<UserEntity> users = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<ChapterEntity> chapters;

    @OneToMany(mappedBy = "course")
    private List<OrderEntity> orders;

    public CourseEntity(String name, double price, String instructorName, int participantsCount, String description,String avatarFileExtension) {
        this.name = name;
        this.price = price;
        this.instructorName = instructorName;
        this.participantsCount = participantsCount;
        this.description = description;
        this.avatarFileExtension = avatarFileExtension;
    }
}
