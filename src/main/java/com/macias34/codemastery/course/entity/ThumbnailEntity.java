package com.macias34.codemastery.course.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "thumbnail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThumbnailEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String src;
	@Column(name = "object_name")
	private String objectName;

	@OneToOne(mappedBy = "thumbnail")
	private CourseEntity course;

	public ThumbnailEntity(String src, String objectName, CourseEntity course) {
		this.src = src;
		this.objectName = objectName;
		this.course = course;
	}

}
