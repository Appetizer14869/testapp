package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SubjectDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Subject}.
 */
public interface SubjectService {
    /**
     * Save a subject.
     *
     * @param subjectDTO the entity to save.
     * @return the persisted entity.
     */
    SubjectDTO save(SubjectDTO subjectDTO);

    /**
     * Updates a subject.
     *
     * @param subjectDTO the entity to update.
     * @return the persisted entity.
     */
    SubjectDTO update(SubjectDTO subjectDTO);

    /**
     * Partially updates a subject.
     *
     * @param subjectDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SubjectDTO> partialUpdate(SubjectDTO subjectDTO);

    /**
     * Get all the subjects.
     *
     * @return the list of entities.
     */
    List<SubjectDTO> findAll();

    /**
     * Get the "id" subject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubjectDTO> findOne(Long id);

    /**
     * Delete the "id" subject.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
