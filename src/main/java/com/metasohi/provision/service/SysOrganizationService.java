package com.metasohi.provision.service;

import com.metasohi.provision.service.dto.SysOrganizationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.metasohi.provision.domain.SysOrganization}.
 */
public interface SysOrganizationService {
    /**
     * Save a sysOrganization.
     *
     * @param sysOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    SysOrganizationDTO save(SysOrganizationDTO sysOrganizationDTO);

    /**
     * Updates a sysOrganization.
     *
     * @param sysOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    SysOrganizationDTO update(SysOrganizationDTO sysOrganizationDTO);

    /**
     * Partially updates a sysOrganization.
     *
     * @param sysOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SysOrganizationDTO> partialUpdate(SysOrganizationDTO sysOrganizationDTO);

    /**
     * Get all the sysOrganizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SysOrganizationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sysOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysOrganizationDTO> findOne(Long id);

    /**
     * Delete the "id" sysOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
