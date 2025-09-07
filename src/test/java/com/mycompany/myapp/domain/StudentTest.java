package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ClassRoomTestSamples.*;
import static com.mycompany.myapp.domain.CourseTestSamples.*;
import static com.mycompany.myapp.domain.ParentTestSamples.*;
import static com.mycompany.myapp.domain.StudentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Student.class);
        Student student1 = getStudentSample1();
        Student student2 = new Student();
        assertThat(student1).isNotEqualTo(student2);

        student2.setId(student1.getId());
        assertThat(student1).isEqualTo(student2);

        student2 = getStudentSample2();
        assertThat(student1).isNotEqualTo(student2);
    }

    @Test
    void classroomTest() {
        Student student = getStudentRandomSampleGenerator();
        ClassRoom classRoomBack = getClassRoomRandomSampleGenerator();

        student.setClassroom(classRoomBack);
        assertThat(student.getClassroom()).isEqualTo(classRoomBack);

        student.classroom(null);
        assertThat(student.getClassroom()).isNull();
    }

    @Test
    void courseTest() {
        Student student = getStudentRandomSampleGenerator();
        Course courseBack = getCourseRandomSampleGenerator();

        student.addCourse(courseBack);
        assertThat(student.getCourses()).containsOnly(courseBack);

        student.removeCourse(courseBack);
        assertThat(student.getCourses()).doesNotContain(courseBack);

        student.courses(new HashSet<>(Set.of(courseBack)));
        assertThat(student.getCourses()).containsOnly(courseBack);

        student.setCourses(new HashSet<>());
        assertThat(student.getCourses()).doesNotContain(courseBack);
    }

    @Test
    void parentTest() {
        Student student = getStudentRandomSampleGenerator();
        Parent parentBack = getParentRandomSampleGenerator();

        student.setParent(parentBack);
        assertThat(student.getParent()).isEqualTo(parentBack);

        student.parent(null);
        assertThat(student.getParent()).isNull();
    }
}
