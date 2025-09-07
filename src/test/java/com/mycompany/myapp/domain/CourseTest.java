package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CourseTestSamples.*;
import static com.mycompany.myapp.domain.StudentTestSamples.*;
import static com.mycompany.myapp.domain.SubjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CourseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Course.class);
        Course course1 = getCourseSample1();
        Course course2 = new Course();
        assertThat(course1).isNotEqualTo(course2);

        course2.setId(course1.getId());
        assertThat(course1).isEqualTo(course2);

        course2 = getCourseSample2();
        assertThat(course1).isNotEqualTo(course2);
    }

    @Test
    void subjectTest() {
        Course course = getCourseRandomSampleGenerator();
        Subject subjectBack = getSubjectRandomSampleGenerator();

        course.addSubject(subjectBack);
        assertThat(course.getSubjects()).containsOnly(subjectBack);

        course.removeSubject(subjectBack);
        assertThat(course.getSubjects()).doesNotContain(subjectBack);

        course.subjects(new HashSet<>(Set.of(subjectBack)));
        assertThat(course.getSubjects()).containsOnly(subjectBack);

        course.setSubjects(new HashSet<>());
        assertThat(course.getSubjects()).doesNotContain(subjectBack);
    }

    @Test
    void studentTest() {
        Course course = getCourseRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        course.addStudent(studentBack);
        assertThat(course.getStudents()).containsOnly(studentBack);
        assertThat(studentBack.getCourses()).containsOnly(course);

        course.removeStudent(studentBack);
        assertThat(course.getStudents()).doesNotContain(studentBack);
        assertThat(studentBack.getCourses()).doesNotContain(course);

        course.students(new HashSet<>(Set.of(studentBack)));
        assertThat(course.getStudents()).containsOnly(studentBack);
        assertThat(studentBack.getCourses()).containsOnly(course);

        course.setStudents(new HashSet<>());
        assertThat(course.getStudents()).doesNotContain(studentBack);
        assertThat(studentBack.getCourses()).doesNotContain(course);
    }
}
