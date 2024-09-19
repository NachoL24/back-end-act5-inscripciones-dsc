package com.example.demo.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseDTO {

    private String name;

    private String description;

    private Integer Capacity;

}
