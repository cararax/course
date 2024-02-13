package com.carara.course.service.impl;

import com.carara.course.model.CourseModel;
import com.carara.course.model.LessonModel;
import com.carara.course.model.ModuleModel;
import com.carara.course.repository.CourseRepository;
import com.carara.course.repository.LessonRepository;
import com.carara.course.repository.ModuleRepository;
import com.carara.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
}