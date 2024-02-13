package com.carara.course.service.impl;

import com.carara.course.repository.LessonRepository;
import com.carara.course.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;
}
