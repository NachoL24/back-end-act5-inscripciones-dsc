package com.example.demo.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
}
