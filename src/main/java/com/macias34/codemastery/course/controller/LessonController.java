package com.macias34.codemastery.course.controller;


import com.macias34.codemastery.course.dto.category.CategoryDto;
import com.macias34.codemastery.course.dto.lesson.CreateLessonDto;
import com.macias34.codemastery.course.dto.lesson.LessonDto;
import com.macias34.codemastery.course.dto.lesson.UpdateLessonDto;
import com.macias34.codemastery.course.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> getLessonById(
            @PathVariable int id
    ){
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<Resource> getLessonFileById(
            @PathVariable int id
    ){
        // TODO Check if user bought course
        Resource resource = lessonService.getLessonFileById(id);
        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"") // Downloading file
                .header(HttpHeaders.CONTENT_TYPE,"video/mp4") //Displaying file
                .body(resource);
    }

    @GetMapping("/chapter/{id}")
    public ResponseEntity<List<LessonDto>> getLessonsByChapterId(
            @PathVariable int id
    ){
        return ResponseEntity.ok(lessonService.getLessonsByChapterId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LessonDto> removeLessonById(
            @PathVariable int id
    ){
        return ResponseEntity.ok(lessonService.deleteLessonById(id));
    }

    //TODO Handle file upload
    @RequestMapping(method = RequestMethod.POST, value = "/",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<LessonDto> createLesson(
            @RequestParam("chapterId") int chapterId,
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file
    ){
        return ResponseEntity.ok(lessonService.createLesson(new CreateLessonDto(name,chapterId),file));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LessonDto> updateLesson(
            @PathVariable int id,
            @RequestBody UpdateLessonDto dto

    ){
        return ResponseEntity.ok(lessonService.updateLessonById(id,dto));
    }
}
