package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.ClassRoomAsserts.*;
import static com.mycompany.myapp.domain.ClassRoomTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassRoomMapperTest {

    private ClassRoomMapper classRoomMapper;

    @BeforeEach
    void setUp() {
        classRoomMapper = new ClassRoomMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getClassRoomSample1();
        var actual = classRoomMapper.toEntity(classRoomMapper.toDto(expected));
        assertClassRoomAllPropertiesEquals(expected, actual);
    }
}
