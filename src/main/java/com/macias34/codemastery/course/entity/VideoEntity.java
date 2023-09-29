package com.macias34.codemastery.course.entity;

import com.macias34.codemastery.storage.entity.StorageFile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "video")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoEntity extends StorageFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(mappedBy = "video")
	private LessonEntity lesson;

	public VideoEntity(String src, String fileName, String objectName, LessonEntity lesson) {
		super(src, fileName, objectName);
		this.lesson = lesson;
	}
}
