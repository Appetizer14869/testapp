package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ClassRoom} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClassRoomDTO implements Serializable {

    private Long id;

    @NotNull
    private String roomNumber;

    private Integer capacity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassRoomDTO)) {
            return false;
        }

        ClassRoomDTO classRoomDTO = (ClassRoomDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, classRoomDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassRoomDTO{" +
            "id=" + getId() +
            ", roomNumber='" + getRoomNumber() + "'" +
            ", capacity=" + getCapacity() +
            "}";
    }
}
