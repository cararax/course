package com.carara.course.service.impl;

import com.carara.course.model.LessonModel;
import com.carara.course.model.ModuleModel;
import com.carara.course.repository.LessonRepository;
import com.carara.course.repository.ModuleRepository;
import com.carara.course.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

}
