package com.metasohi.provision.service;

import com.metasohi.provision.service.dto.SysAreaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.metasohi.provision.domain.SysArea}.
 */
public interface SysAreaService {
    /**
     * Save a sysArea.
     *
     * @param sysAreaDTO the entity to save.
     * @return the persisted entity.
     */
    SysAreaDTO save(SysAreaDTO sysAreaDTO);

    /**
     * Updates a sysArea.
     *
     * @param sysAreaDTO the entity to update.
     * @return the persisted entity.
     */
    SysAreaDTO update(SysAreaDTO sysAreaDTO);

    /**
     * Partially updates a sysArea.
     *
     * @param sysAreaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SysAreaDTO> partialUpdate(SysAreaDTO sysAreaDTO);

    /**
     * Get all the sysAreas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SysAreaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sysArea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysAreaDTO> findOne(Long id);

    /**
     * Delete the "id" sysArea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
