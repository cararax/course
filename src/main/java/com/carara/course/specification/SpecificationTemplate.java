package com.carara.course.specification;

import com.carara.course.model.CourseModel;
import com.carara.course.model.LessonModel;
import com.carara.course.model.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    public static Specification<ModuleModel> moduleByCourseId(UUID courseId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<ModuleModel> module = root;
            Root<CourseModel> course = query.from(CourseModel.class);
            Expression<Collection<ModuleModel>> coursesModules = course.get("modules");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(course.get("courseId"), courseId),
                    criteriaBuilder.isMember(module, coursesModules)
            );
        };
    }

    public static Specification<LessonModel> lessonByModuleId(UUID moduleId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<LessonModel> lesson = root;
            Root<ModuleModel> module = query.from(ModuleModel.class);
            Expression<Collection<LessonModel>> moduleLessons = module.get("lessons");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(module.get("moduleId"), moduleId),
                    criteriaBuilder.isMember(lesson, moduleLessons)
            );
        };
    }

    @And({
            @Spec(path = "courseLevel", spec = EqualIgnoreCase.class),
            @Spec(path = "courseStatus", spec = EqualIgnoreCase.class),
            @Spec(path = "name", spec = LikeIgnoreCase.class)
    })
    public interface CourseSpec extends Specification<CourseModel> {
    }

    @And({
            @Spec(path = "title", spec = LikeIgnoreCase.class)
    })
    public interface ModuleSpec extends Specification<ModuleModel> {
    }

    @And({
            @Spec(path = "title", spec = LikeIgnoreCase.class)
    })
    public interface LessonSpec extends Specification<LessonModel> {
    }

}
