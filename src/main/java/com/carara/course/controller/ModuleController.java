package com.carara.course.controller;

import com.carara.course.dto.ModuleDTO;
import com.carara.course.model.CourseModel;
import com.carara.course.model.ModuleModel;
import com.carara.course.service.CourseService;
import com.carara.course.service.ModuleService;
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
public class ModuleController {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/{courseId}/modules")
    public ResponseEntity<Page<ModuleModel>> getAllModules(@PathVariable(value = "courseId") UUID courseId,
                                                           SpecificationTemplate.ModuleSpec spec,
                                                           @PageableDefault(page = 0, size = 10, sort = "moduleId", direction = Sort.Direction.ASC)
                                                           Pageable pageable) {
        return ResponseEntity.status(OK).body(moduleService.findByCourseId(SpecificationTemplate.moduleByCourseId(courseId).and(spec), pageable));
    }

    @GetMapping("/asd")
    public ResponseEntity<String> getAllModules() {
        return ResponseEntity.status(OK).body("bbbb");
    }



    @GetMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId,
                                               @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<ModuleModel> moduleOptional = moduleService.findModuleIntoCourse(moduleId, courseId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found for this course.");
        }
        return ResponseEntity.status(OK).body(moduleOptional.get());
    }

    @PostMapping("/{courseId}/modules")
    public ResponseEntity<Object> createModule(@Validated @RequestBody ModuleDTO moduleDTO,
                                               @PathVariable(value = "courseId") UUID courseId) {
        Optional<CourseModel> courseOptional = courseService.findById(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Course not found.");
        }

        var couseModel = new CourseModel();
        BeanUtils.copyProperties(couseModel, couseModel);

        var moduleModel = new ModuleModel();
        BeanUtils.copyProperties(moduleDTO, moduleModel);
        moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        moduleModel.setCourse(courseOptional.get());

        return ResponseEntity.status(CREATED).body(moduleService.save(moduleModel));
    }

    @PutMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> updateCourse(@Validated @RequestBody ModuleDTO moduleDTO,
                                               @PathVariable(value = "courseId") UUID courseId,
                                               @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<ModuleModel> moduleOptional = moduleService.findModuleIntoCourse(moduleId, courseId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found for this course.");
        }
        var moduleModel = moduleOptional.get();
        BeanUtils.copyProperties(moduleDTO, moduleModel);
        return ResponseEntity.status(OK).body(moduleService.save(moduleModel));
    }

    @DeleteMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteModule(@PathVariable(value = "courseId") UUID courseId,
                                               @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<ModuleModel> moduleModel = moduleService.findModuleIntoCourse(moduleId, courseId);
        if (moduleModel.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found for this course.");
        }
        moduleService.delete(moduleModel.get());
        return ResponseEntity.status(NO_CONTENT).body("Module successfully deleted.");
    }

}
