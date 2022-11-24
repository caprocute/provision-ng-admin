package com.metasohi.provision.service;

import com.metasohi.provision.service.dto.SysCameraDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.metasohi.provision.domain.SysCamera}.
 */
public interface SysCameraService {
    /**
     * Save a sysCamera.
     *
     * @param sysCameraDTO the entity to save.
     * @return the persisted entity.
     */
    SysCameraDTO save(SysCameraDTO sysCameraDTO);

    /**
     * Updates a sysCamera.
     *
     * @param sysCameraDTO the entity to update.
     * @return the persisted entity.
     */
    SysCameraDTO update(SysCameraDTO sysCameraDTO);

    /**
     * Partially updates a sysCamera.
     *
     * @param sysCameraDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SysCameraDTO> partialUpdate(SysCameraDTO sysCameraDTO);

    /**
     * Get all the sysCameras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SysCameraDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sysCamera.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysCameraDTO> findOne(Long id);

    /**
     * Delete the "id" sysCamera.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
