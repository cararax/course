package com.carara.course.controller;

import com.carara.course.dto.CourseDTO;
import com.carara.course.model.CourseModel;
import com.carara.course.service.CourseService;
import com.carara.course.specification.SpecificationTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAllCourses(SpecificationTemplate.CourseSpec spec,
                                                           @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC)
                                                           Pageable pageable) {
        return ResponseEntity.status(OK).body(courseService.findAll(spec, pageable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId) {
        Optional<CourseModel> courseOptional = courseService.findById(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Course not found.");
        }
        return ResponseEntity.status(OK).body(courseOptional.get());
    }

    @PostMapping
    public ResponseEntity<Object> createCourse(@Validated @RequestBody CourseDTO courseDTO) {
        var couseModel = new CourseModel();
        BeanUtils.copyProperties(courseDTO, couseModel);
        couseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        couseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(CREATED).body(courseService.save(couseModel));
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId") UUID courseId,
                                               @Validated @RequestBody CourseDTO courseDTO) {
        Optional<CourseModel> courseOptional = courseService.findById(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Course not found.");
        }
        var courseModel = courseOptional.get();
        BeanUtils.copyProperties(courseDTO, courseModel);
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(OK).body(courseService.save(courseModel));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId) {
        Optional<CourseModel> courseOptional = courseService.findById(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Course not found.");
        }
        courseService.delete(courseOptional.get());
        return ResponseEntity.status(NO_CONTENT).body("Course successfully deleted.");
    }

}
