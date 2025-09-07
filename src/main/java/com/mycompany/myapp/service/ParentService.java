package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ParentDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Parent}.
 */
public interface ParentService {
    /**
     * Save a parent.
     *
     * @param parentDTO the entity to save.
     * @return the persisted entity.
     */
    ParentDTO save(ParentDTO parentDTO);

    /**
     * Updates a parent.
     *
     * @param parentDTO the entity to update.
     * @return the persisted entity.
     */
    ParentDTO update(ParentDTO parentDTO);

    /**
     * Partially updates a parent.
     *
     * @param parentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ParentDTO> partialUpdate(ParentDTO parentDTO);

    /**
     * Get all the parents.
     *
     * @return the list of entities.
     */
    List<ParentDTO> findAll();

    /**
     * Get the "id" parent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParentDTO> findOne(Long id);

    /**
     * Delete the "id" parent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
