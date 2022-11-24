package com.metasohi.provision.service;

import com.metasohi.provision.service.dto.DistrictsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.metasohi.provision.domain.Districts}.
 */
public interface DistrictsService {
    /**
     * Save a districts.
     *
     * @param districtsDTO the entity to save.
     * @return the persisted entity.
     */
    DistrictsDTO save(DistrictsDTO districtsDTO);

    /**
     * Updates a districts.
     *
     * @param districtsDTO the entity to update.
     * @return the persisted entity.
     */
    DistrictsDTO update(DistrictsDTO districtsDTO);

    /**
     * Partially updates a districts.
     *
     * @param districtsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DistrictsDTO> partialUpdate(DistrictsDTO districtsDTO);

    /**
     * Get all the districts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DistrictsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" districts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DistrictsDTO> findOne(String id);

    /**
     * Delete the "id" districts.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
