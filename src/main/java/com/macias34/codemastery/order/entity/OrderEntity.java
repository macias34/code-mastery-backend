package com.macias34.codemastery.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.macias34.codemastery.course.entity.CourseEntity;
import com.macias34.codemastery.user.entity.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.sql.Timestamp;

@Entity
@Table(name = "order_")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private OrderStatus status;
    private double price;

//    @JsonManagedReference
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private CourseEntity course;

//    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;


}
