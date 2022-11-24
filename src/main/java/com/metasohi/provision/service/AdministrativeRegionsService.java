package com.metasohi.provision.service;

import com.metasohi.provision.service.dto.AdministrativeRegionsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.metasohi.provision.domain.AdministrativeRegions}.
 */
public interface AdministrativeRegionsService {
    /**
     * Save a administrativeRegions.
     *
     * @param administrativeRegionsDTO the entity to save.
     * @return the persisted entity.
     */
    AdministrativeRegionsDTO save(AdministrativeRegionsDTO administrativeRegionsDTO);

    /**
     * Updates a administrativeRegions.
     *
     * @param administrativeRegionsDTO the entity to update.
     * @return the persisted entity.
     */
    AdministrativeRegionsDTO update(AdministrativeRegionsDTO administrativeRegionsDTO);

    /**
     * Partially updates a administrativeRegions.
     *
     * @param administrativeRegionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdministrativeRegionsDTO> partialUpdate(AdministrativeRegionsDTO administrativeRegionsDTO);

    /**
     * Get all the administrativeRegions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdministrativeRegionsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" administrativeRegions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdministrativeRegionsDTO> findOne(Long id);

    /**
     * Delete the "id" administrativeRegions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
