package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CourseTestSamples.*;
import static com.mycompany.myapp.domain.SubjectTestSamples.*;
import static com.mycompany.myapp.domain.TeacherTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SubjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subject.class);
        Subject subject1 = getSubjectSample1();
        Subject subject2 = new Subject();
        assertThat(subject1).isNotEqualTo(subject2);

        subject2.setId(subject1.getId());
        assertThat(subject1).isEqualTo(subject2);

        subject2 = getSubjectSample2();
        assertThat(subject1).isNotEqualTo(subject2);
    }

    @Test
    void teacherTest() {
        Subject subject = getSubjectRandomSampleGenerator();
        Teacher teacherBack = getTeacherRandomSampleGenerator();

        subject.setTeacher(teacherBack);
        assertThat(subject.getTeacher()).isEqualTo(teacherBack);

        subject.teacher(null);
        assertThat(subject.getTeacher()).isNull();
    }

    @Test
    void courseTest() {
        Subject subject = getSubjectRandomSampleGenerator();
        Course courseBack = getCourseRandomSampleGenerator();

        subject.addCourse(courseBack);
        assertThat(subject.getCourses()).containsOnly(courseBack);
        assertThat(courseBack.getSubjects()).containsOnly(subject);

        subject.removeCourse(courseBack);
        assertThat(subject.getCourses()).doesNotContain(courseBack);
        assertThat(courseBack.getSubjects()).doesNotContain(subject);

        subject.courses(new HashSet<>(Set.of(courseBack)));
        assertThat(subject.getCourses()).containsOnly(courseBack);
        assertThat(courseBack.getSubjects()).containsOnly(subject);

        subject.setCourses(new HashSet<>());
        assertThat(subject.getCourses()).doesNotContain(courseBack);
        assertThat(courseBack.getSubjects()).doesNotContain(subject);
    }
}
