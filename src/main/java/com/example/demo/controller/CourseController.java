package com.example.demo.controller;

import com.example.demo.dto.request.CourseDTO;
import com.example.demo.dto.request.StudentDTO;
import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/public/course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(course.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO courseDTO) {
        return courseService.createCourse(courseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        return courseService.updateCourse(id,courseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }

    @GetMapping("/{courseId}/student/{studentId}")
    public ResponseEntity<?> addStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        return courseService.addStudentToCourse(courseId, studentId);
    }

    @DeleteMapping("/{courseId}/student/{studentId}")
    public ResponseEntity<?> removeStudentFromCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        return courseService.removeStudentFromCourse(courseId, studentId);
    }

}
