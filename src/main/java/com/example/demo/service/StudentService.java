package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.dto.request.StudentDTO;
import com.example.demo.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public ResponseEntity<?> createStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setPhone(studentDTO.getPhone());
        student.setPassword(studentDTO.getPassword());
        studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> deleteStudent(Long id) {
        return getStudentById(id).map(student -> {
            studentRepository.delete(student);
            return ResponseEntity.status(HttpStatus.OK).build();
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<?> updateStudent(Long id, StudentDTO studentDTO) {
        return getStudentById(id).map(student -> {
            student.setFirstName(studentDTO.getFirstName());
            student.setLastName(studentDTO.getLastName());
            student.setEmail(studentDTO.getEmail());
            student.setPhone(studentDTO.getPhone());
            student.setPassword(studentDTO.getPassword());
            studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.OK).build();
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
