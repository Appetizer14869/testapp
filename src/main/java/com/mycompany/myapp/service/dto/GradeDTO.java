package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Grade} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GradeDTO implements Serializable {

    private Long id;

    @NotNull
    private Double score;

    private String remarks;

    private Instant dateAwarded;

    private StudentDTO student;

    private SubjectDTO subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Instant getDateAwarded() {
        return dateAwarded;
    }

    public void setDateAwarded(Instant dateAwarded) {
        this.dateAwarded = dateAwarded;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GradeDTO)) {
            return false;
        }

        GradeDTO gradeDTO = (GradeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gradeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GradeDTO{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", remarks='" + getRemarks() + "'" +
            ", dateAwarded='" + getDateAwarded() + "'" +
            ", student=" + getStudent() +
            ", subject=" + getSubject() +
            "}";
    }
}
