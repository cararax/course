package com.carara.course.controller;

import com.carara.course.dto.LessonDTO;
import com.carara.course.model.LessonModel;
import com.carara.course.model.ModuleModel;
import com.carara.course.service.LessonService;
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
@RequestMapping("/modules")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private LessonService lessonService;

    @GetMapping("/{moduleId}/lessons")
    public ResponseEntity<Page<LessonModel>> getAllLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                                          SpecificationTemplate.LessonSpec spec,
                                                          @PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Sort.Direction.ASC)
                                                          Pageable pageable) {
        return ResponseEntity.status(OK).body(lessonService.findAllByModule(SpecificationTemplate.lessonByModuleId(moduleId).and(spec), pageable));
    }

    @GetMapping("/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> getOneLesson(@PathVariable(value = "lessonId") UUID lessonId,
                                               @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<LessonModel> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Lesson not found for this module.");
        }
        return ResponseEntity.status(OK).body(lessonOptional.get());
    }

    @PostMapping("/{moduleId}/lessons")
    public ResponseEntity<Object> createLesson(@Validated @RequestBody LessonDTO lessonDTO,
                                               @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<ModuleModel> moduleOptional = moduleService.findById(moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found.");
        }

        var lessonModel = new LessonModel();
        BeanUtils.copyProperties(lessonDTO, lessonModel);
        lessonModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lessonModel.setModule(moduleOptional.get());

        return ResponseEntity.status(CREATED).body(lessonService.save(lessonModel));
    }

    @PutMapping("/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> updateLesson(@Validated @RequestBody LessonDTO lessonDTO,
                                               @PathVariable(value = "moduleId") UUID moduleId,
                                               @PathVariable(value = "lessonId") UUID lessonId) {
        Optional<LessonModel> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Lesson not found for this module.");
        }

        var lessonModel = lessonOptional.get();
        BeanUtils.copyProperties(lessonDTO, lessonModel);

        return ResponseEntity.status(OK).body(lessonService.save(lessonModel));
    }

    @DeleteMapping("/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable(value = "lessonId") UUID lessonId,
                                               @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<LessonModel> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Lesson not found for this module.");
        }

        lessonService.delete(lessonOptional.get());
        return ResponseEntity.status(NO_CONTENT).body("Lesson successfully deleted.");
    }

}
