package com.carara.course.dto;

import com.carara.course.model.CourseLevel;
import com.carara.course.model.CourseStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CourseDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String imageUrl;

    @NotNull
    private CourseStatus courseStatus;

    @NotNull
    private CourseLevel courseLevel;

    @NotNull
    private UUID userInstructor;

}
