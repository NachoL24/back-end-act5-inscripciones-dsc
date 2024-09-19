package com.example.demo.service;

import com.example.demo.dto.request.CourseDTO;
import com.example.demo.dto.request.StudentDTO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final StudentService studentService;

    public ResponseEntity<?> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public ResponseEntity<?> createCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setCapacity(courseDTO.getCapacity());
        courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> updateCourse(Long id, CourseDTO courseDTO) {
        return getCourseById(id).map(course -> {
            course.setName(courseDTO.getName());
            course.setDescription(courseDTO.getDescription());
            course.setCapacity(courseDTO.getCapacity());
            courseRepository.save(course);
            return ResponseEntity.status(HttpStatus.OK).build();
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<?> deleteCourse(Long id) {
        return getCourseById(id).map(course -> {
            courseRepository.delete(course);
            return ResponseEntity.status(HttpStatus.OK).build();
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<?> addStudentToCourse(Long courseId, Long studentId) {
        return getCourseById(courseId).map(course -> {
            if (course.getStudents().size() < course.getCapacity()) {
                Optional<Student> student = studentService.getStudentById(studentId);
                if (student.isPresent()) {
                    if(course.getStudents().contains(student.get())){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                    }
                    course.getStudents().add(student.get());
                    courseRepository.save(course);
                    return ResponseEntity.status(HttpStatus.OK).build();
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{response: Course is full}");
            }
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<?> removeStudentFromCourse(Long courseId, Long studentId) {
        return getCourseById(courseId).map(course -> {
            Optional<Student> student = studentService.getStudentById(studentId);
            if (student.isPresent()) {
                if(!course.getStudents().contains(student.get())){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
                course.getStudents().remove(student.get());
                courseRepository.save(course);
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
