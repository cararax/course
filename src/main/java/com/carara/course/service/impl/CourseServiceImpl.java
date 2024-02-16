package com.carara.course.service.impl;

import com.carara.course.model.CourseModel;
import com.carara.course.model.LessonModel;
import com.carara.course.model.ModuleModel;
import com.carara.course.repository.CourseRepository;
import com.carara.course.repository.LessonRepository;
import com.carara.course.repository.ModuleRepository;
import com.carara.course.service.CourseService;
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
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Override
    @Transactional
    public void delete(CourseModel courseModel) {
        List<ModuleModel> modulesIntoCourse = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
        if (modulesIntoCourse.isEmpty()) {
            courseRepository.delete(courseModel);
            return;
        }
        for (ModuleModel module : modulesIntoCourse) {
            List<LessonModel> allLessonsIntoModule = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
            if (!allLessonsIntoModule.isEmpty()) {
                lessonRepository.deleteAll(allLessonsIntoModule);
            }
        }
        moduleRepository.deleteAll(modulesIntoCourse);
        courseRepository.delete(courseModel);
    }

    @Override
    public CourseModel save(CourseModel couseModel) {
        return courseRepository.save(couseModel);
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public CourseModel update(CourseModel courseModel) {
        return null;
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }
}
