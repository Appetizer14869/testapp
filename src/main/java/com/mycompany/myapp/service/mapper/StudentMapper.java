package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ClassRoom;
import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.Parent;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.service.dto.ClassRoomDTO;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.dto.ParentDTO;
import com.mycompany.myapp.service.dto.StudentDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring")
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {
    @Mapping(target = "classroom", source = "classroom", qualifiedByName = "classRoomId")
    @Mapping(target = "courses", source = "courses", qualifiedByName = "courseTitleSet")
    @Mapping(target = "parent", source = "parent", qualifiedByName = "parentId")
    StudentDTO toDto(Student s);

    @Mapping(target = "removeCourse", ignore = true)
    Student toEntity(StudentDTO studentDTO);

    @Named("classRoomId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClassRoomDTO toDtoClassRoomId(ClassRoom classRoom);

    @Named("courseTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    CourseDTO toDtoCourseTitle(Course course);

    @Named("courseTitleSet")
    default Set<CourseDTO> toDtoCourseTitleSet(Set<Course> course) {
        return course.stream().map(this::toDtoCourseTitle).collect(Collectors.toSet());
    }

    @Named("parentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParentDTO toDtoParentId(Parent parent);
}
