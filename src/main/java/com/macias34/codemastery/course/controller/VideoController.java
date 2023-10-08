package com.macias34.codemastery.course.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macias34.codemastery.course.service.VideoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/video")
@Tag(name = "video")
@AllArgsConstructor
public class VideoController {

	private final VideoService videoService;

	@GetMapping("/{id}")
	public ResponseEntity<String> getVideoPresignedUrl(@PathVariable int id) {
		return ResponseEntity.ok(videoService.getVideoPresignedUrl(id));
	}

}
