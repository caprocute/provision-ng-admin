package com.metasohi.provision.service;

import com.metasohi.provision.service.dto.WardsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.metasohi.provision.domain.Wards}.
 */
public interface WardsService {
    /**
     * Save a wards.
     *
     * @param wardsDTO the entity to save.
     * @return the persisted entity.
     */
    WardsDTO save(WardsDTO wardsDTO);

    /**
     * Updates a wards.
     *
     * @param wardsDTO the entity to update.
     * @return the persisted entity.
     */
    WardsDTO update(WardsDTO wardsDTO);

    /**
     * Partially updates a wards.
     *
     * @param wardsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WardsDTO> partialUpdate(WardsDTO wardsDTO);

    /**
     * Get all the wards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WardsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" wards.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WardsDTO> findOne(String id);

    /**
     * Delete the "id" wards.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
