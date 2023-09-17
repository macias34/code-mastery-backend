package com.macias34.codemastery.course.entity;

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

@Entity
@Table(name = "lesson")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "chapter_id", referencedColumnName = "id")
    private ChapterEntity chapter;

    public LessonEntity(String name, ChapterEntity chapter){
        this.name = name;
        this.chapter = chapter;
    }
}
