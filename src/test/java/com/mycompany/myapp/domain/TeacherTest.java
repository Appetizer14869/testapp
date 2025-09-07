package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DepartmentTestSamples.*;
import static com.mycompany.myapp.domain.SubjectTestSamples.*;
import static com.mycompany.myapp.domain.TeacherTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TeacherTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Teacher.class);
        Teacher teacher1 = getTeacherSample1();
        Teacher teacher2 = new Teacher();
        assertThat(teacher1).isNotEqualTo(teacher2);

        teacher2.setId(teacher1.getId());
        assertThat(teacher1).isEqualTo(teacher2);

        teacher2 = getTeacherSample2();
        assertThat(teacher1).isNotEqualTo(teacher2);
    }

    @Test
    void subjectTest() {
        Teacher teacher = getTeacherRandomSampleGenerator();
        Subject subjectBack = getSubjectRandomSampleGenerator();

        teacher.addSubject(subjectBack);
        assertThat(teacher.getSubjects()).containsOnly(subjectBack);
        assertThat(subjectBack.getTeacher()).isEqualTo(teacher);

        teacher.removeSubject(subjectBack);
        assertThat(teacher.getSubjects()).doesNotContain(subjectBack);
        assertThat(subjectBack.getTeacher()).isNull();

        teacher.subjects(new HashSet<>(Set.of(subjectBack)));
        assertThat(teacher.getSubjects()).containsOnly(subjectBack);
        assertThat(subjectBack.getTeacher()).isEqualTo(teacher);

        teacher.setSubjects(new HashSet<>());
        assertThat(teacher.getSubjects()).doesNotContain(subjectBack);
        assertThat(subjectBack.getTeacher()).isNull();
    }

    @Test
    void departmentTest() {
        Teacher teacher = getTeacherRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        teacher.setDepartment(departmentBack);
        assertThat(teacher.getDepartment()).isEqualTo(departmentBack);

        teacher.department(null);
        assertThat(teacher.getDepartment()).isNull();
    }
}
