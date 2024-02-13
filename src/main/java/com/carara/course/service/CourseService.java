package com.carara.course.service;

import com.carara.course.model.CourseModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    void delete(CourseModel courseModel);

    CourseModel save(CourseModel couseModel);

    Optional<CourseModel> findById(UUID courseId);

    CourseModel update(CourseModel courseModel);

    List<CourseModel> findAll();
}
