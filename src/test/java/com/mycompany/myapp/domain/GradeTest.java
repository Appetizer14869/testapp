package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.GradeTestSamples.*;
import static com.mycompany.myapp.domain.StudentTestSamples.*;
import static com.mycompany.myapp.domain.SubjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GradeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grade.class);
        Grade grade1 = getGradeSample1();
        Grade grade2 = new Grade();
        assertThat(grade1).isNotEqualTo(grade2);

        grade2.setId(grade1.getId());
        assertThat(grade1).isEqualTo(grade2);

        grade2 = getGradeSample2();
        assertThat(grade1).isNotEqualTo(grade2);
    }

    @Test
    void studentTest() {
        Grade grade = getGradeRandomSampleGenerator();
        Student studentBack = getStudentRandomSampleGenerator();

        grade.setStudent(studentBack);
        assertThat(grade.getStudent()).isEqualTo(studentBack);

        grade.student(null);
        assertThat(grade.getStudent()).isNull();
    }

    @Test
    void subjectTest() {
        Grade grade = getGradeRandomSampleGenerator();
        Subject subjectBack = getSubjectRandomSampleGenerator();

        grade.setSubject(subjectBack);
        assertThat(grade.getSubject()).isEqualTo(subjectBack);

        grade.subject(null);
        assertThat(grade.getSubject()).isNull();
    }
}
