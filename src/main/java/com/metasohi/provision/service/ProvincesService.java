package com.metasohi.provision.service;

import com.metasohi.provision.service.dto.ProvincesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.metasohi.provision.domain.Provinces}.
 */
public interface ProvincesService {
    /**
     * Save a provinces.
     *
     * @param provincesDTO the entity to save.
     * @return the persisted entity.
     */
    ProvincesDTO save(ProvincesDTO provincesDTO);

    /**
     * Updates a provinces.
     *
     * @param provincesDTO the entity to update.
     * @return the persisted entity.
     */
    ProvincesDTO update(ProvincesDTO provincesDTO);

    /**
     * Partially updates a provinces.
     *
     * @param provincesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProvincesDTO> partialUpdate(ProvincesDTO provincesDTO);

    /**
     * Get all the provinces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProvincesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" provinces.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProvincesDTO> findOne(String id);

    /**
     * Delete the "id" provinces.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
