package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.domain.Subject;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.dto.StudentDTO;
import com.mycompany.myapp.service.dto.SubjectDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Course} and its DTO {@link CourseDTO}.
 */
@Mapper(componentModel = "spring")
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {
    @Mapping(target = "subjects", source = "subjects", qualifiedByName = "subjectSubjectNameSet")
    @Mapping(target = "students", source = "students", qualifiedByName = "studentIdSet")
    CourseDTO toDto(Course s);

    @Mapping(target = "removeSubject", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "removeStudent", ignore = true)
    Course toEntity(CourseDTO courseDTO);

    @Named("subjectSubjectName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "subjectName", source = "subjectName")
    SubjectDTO toDtoSubjectSubjectName(Subject subject);

    @Named("subjectSubjectNameSet")
    default Set<SubjectDTO> toDtoSubjectSubjectNameSet(Set<Subject> subject) {
        return subject.stream().map(this::toDtoSubjectSubjectName).collect(Collectors.toSet());
    }

    @Named("studentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StudentDTO toDtoStudentId(Student student);

    @Named("studentIdSet")
    default Set<StudentDTO> toDtoStudentIdSet(Set<Student> student) {
        return student.stream().map(this::toDtoStudentId).collect(Collectors.toSet());
    }
}
