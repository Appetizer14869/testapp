package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DepartmentTestSamples.*;
import static com.mycompany.myapp.domain.TeacherTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DepartmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Department.class);
        Department department1 = getDepartmentSample1();
        Department department2 = new Department();
        assertThat(department1).isNotEqualTo(department2);

        department2.setId(department1.getId());
        assertThat(department1).isEqualTo(department2);

        department2 = getDepartmentSample2();
        assertThat(department1).isNotEqualTo(department2);
    }

    @Test
    void teacherTest() {
        Department department = getDepartmentRandomSampleGenerator();
        Teacher teacherBack = getTeacherRandomSampleGenerator();

        department.addTeacher(teacherBack);
        assertThat(department.getTeachers()).containsOnly(teacherBack);
        assertThat(teacherBack.getDepartment()).isEqualTo(department);

        department.removeTeacher(teacherBack);
        assertThat(department.getTeachers()).doesNotContain(teacherBack);
        assertThat(teacherBack.getDepartment()).isNull();

        department.teachers(new HashSet<>(Set.of(teacherBack)));
        assertThat(department.getTeachers()).containsOnly(teacherBack);
        assertThat(teacherBack.getDepartment()).isEqualTo(department);

        department.setTeachers(new HashSet<>());
        assertThat(department.getTeachers()).doesNotContain(teacherBack);
        assertThat(teacherBack.getDepartment()).isNull();
    }
}
