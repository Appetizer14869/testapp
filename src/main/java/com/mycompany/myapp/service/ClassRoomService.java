package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ClassRoomDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ClassRoom}.
 */
public interface ClassRoomService {
    /**
     * Save a classRoom.
     *
     * @param classRoomDTO the entity to save.
     * @return the persisted entity.
     */
    ClassRoomDTO save(ClassRoomDTO classRoomDTO);

    /**
     * Updates a classRoom.
     *
     * @param classRoomDTO the entity to update.
     * @return the persisted entity.
     */
    ClassRoomDTO update(ClassRoomDTO classRoomDTO);

    /**
     * Partially updates a classRoom.
     *
     * @param classRoomDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClassRoomDTO> partialUpdate(ClassRoomDTO classRoomDTO);

    /**
     * Get all the classRooms.
     *
     * @return the list of entities.
     */
    List<ClassRoomDTO> findAll();

    /**
     * Get the "id" classRoom.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClassRoomDTO> findOne(Long id);

    /**
     * Delete the "id" classRoom.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
