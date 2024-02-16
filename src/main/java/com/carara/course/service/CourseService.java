package com.carara.course.service;

import com.carara.course.model.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    void delete(CourseModel courseModel);

    CourseModel save(CourseModel couseModel);

    Optional<CourseModel> findById(UUID courseId);

    CourseModel update(CourseModel courseModel);

    Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable);
}
