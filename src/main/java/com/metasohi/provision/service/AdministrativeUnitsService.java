package com.metasohi.provision.service;

import com.metasohi.provision.service.dto.AdministrativeUnitsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.metasohi.provision.domain.AdministrativeUnits}.
 */
public interface AdministrativeUnitsService {
    /**
     * Save a administrativeUnits.
     *
     * @param administrativeUnitsDTO the entity to save.
     * @return the persisted entity.
     */
    AdministrativeUnitsDTO save(AdministrativeUnitsDTO administrativeUnitsDTO);

    /**
     * Updates a administrativeUnits.
     *
     * @param administrativeUnitsDTO the entity to update.
     * @return the persisted entity.
     */
    AdministrativeUnitsDTO update(AdministrativeUnitsDTO administrativeUnitsDTO);

    /**
     * Partially updates a administrativeUnits.
     *
     * @param administrativeUnitsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdministrativeUnitsDTO> partialUpdate(AdministrativeUnitsDTO administrativeUnitsDTO);

    /**
     * Get all the administrativeUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdministrativeUnitsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" administrativeUnits.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdministrativeUnitsDTO> findOne(Long id);

    /**
     * Delete the "id" administrativeUnits.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
