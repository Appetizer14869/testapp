package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Grade;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.domain.Subject;
import com.mycompany.myapp.service.dto.GradeDTO;
import com.mycompany.myapp.service.dto.StudentDTO;
import com.mycompany.myapp.service.dto.SubjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Grade} and its DTO {@link GradeDTO}.
 */
@Mapper(componentModel = "spring")
public interface GradeMapper extends EntityMapper<GradeDTO, Grade> {
    @Mapping(target = "student", source = "student", qualifiedByName = "studentId")
    @Mapping(target = "subject", source = "subject", qualifiedByName = "subjectId")
    GradeDTO toDto(Grade s);

    @Named("studentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StudentDTO toDtoStudentId(Student student);

    @Named("subjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubjectDTO toDtoSubjectId(Subject subject);
}
