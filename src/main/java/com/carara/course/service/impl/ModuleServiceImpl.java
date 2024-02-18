package com.carara.course.service.impl;

import com.carara.course.model.LessonModel;
import com.carara.course.model.ModuleModel;
import com.carara.course.repository.LessonRepository;
import com.carara.course.repository.ModuleRepository;
import com.carara.course.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    @Transactional
    public void delete(ModuleModel moduleModel) {
        List<LessonModel> allLessonsIntoModule = lessonRepository.findAllLessonsIntoModule(moduleModel.getModuleId());
        if (!allLessonsIntoModule.isEmpty()) {
            lessonRepository.deleteAll(allLessonsIntoModule);
        }
        moduleRepository.delete(moduleModel);
    }

    @Override
    public ModuleModel save(ModuleModel moduleModel) {
        return moduleRepository.save(moduleModel);
    }

    @Override
    public Optional<ModuleModel> findModuleIntoCourse(UUID moduleId, UUID courseId) {
        return moduleRepository.getByModuleIdAndCourse_CourseId(moduleId, courseId);
    }

    @Override
    public List<ModuleModel> findByCourseId(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }

    @Override
    public Optional<ModuleModel> findById(UUID moduleId) {
        return moduleRepository.findById(moduleId);
    }

    @Override
    public Page<ModuleModel> findByCourseId(Specification<ModuleModel> spec, Pageable pageable) {
        return moduleRepository.findAll(spec, pageable);
    }

}
