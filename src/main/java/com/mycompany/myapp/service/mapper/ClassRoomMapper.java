package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ClassRoom;
import com.mycompany.myapp.service.dto.ClassRoomDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassRoom} and its DTO {@link ClassRoomDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClassRoomMapper extends EntityMapper<ClassRoomDTO, ClassRoom> {}
