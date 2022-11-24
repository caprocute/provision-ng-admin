package com.metasohi.provision.web.rest;

import com.metasohi.provision.domain.SysOrganizationArea;
import com.metasohi.provision.repository.SysOrganizationAreaRepository;
import com.metasohi.provision.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.metasohi.provision.domain.SysOrganizationArea}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SysOrganizationAreaResource {

    private final Logger log = LoggerFactory.getLogger(SysOrganizationAreaResource.class);

    private static final String ENTITY_NAME = "sysOrganizationArea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysOrganizationAreaRepository sysOrganizationAreaRepository;

    public SysOrganizationAreaResource(SysOrganizationAreaRepository sysOrganizationAreaRepository) {
        this.sysOrganizationAreaRepository = sysOrganizationAreaRepository;
    }

    /**
     * {@code POST  /sys-organization-areas} : Create a new sysOrganizationArea.
     *
     * @param sysOrganizationArea the sysOrganizationArea to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysOrganizationArea, or with status {@code 400 (Bad Request)} if the sysOrganizationArea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-organization-areas")
    public ResponseEntity<SysOrganizationArea> createSysOrganizationArea(@RequestBody SysOrganizationArea sysOrganizationArea)
        throws URISyntaxException {
        log.debug("REST request to save SysOrganizationArea : {}", sysOrganizationArea);
        if (sysOrganizationArea.getId() != null) {
            throw new BadRequestAlertException("A new sysOrganizationArea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysOrganizationArea result = sysOrganizationAreaRepository.save(sysOrganizationArea);
        return ResponseEntity
            .created(new URI("/api/sys-organization-areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-organization-areas/:id} : Updates an existing sysOrganizationArea.
     *
     * @param id the id of the sysOrganizationArea to save.
     * @param sysOrganizationArea the sysOrganizationArea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysOrganizationArea,
     * or with status {@code 400 (Bad Request)} if the sysOrganizationArea is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysOrganizationArea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-organization-areas/{id}")
    public ResponseEntity<SysOrganizationArea> updateSysOrganizationArea(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SysOrganizationArea sysOrganizationArea
    ) throws URISyntaxException {
        log.debug("REST request to update SysOrganizationArea : {}, {}", id, sysOrganizationArea);
        if (sysOrganizationArea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sysOrganizationArea.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sysOrganizationAreaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SysOrganizationArea result = sysOrganizationAreaRepository.save(sysOrganizationArea);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysOrganizationArea.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sys-organization-areas/:id} : Partial updates given fields of an existing sysOrganizationArea, field will ignore if it is null
     *
     * @param id the id of the sysOrganizationArea to save.
     * @param sysOrganizationArea the sysOrganizationArea to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysOrganizationArea,
     * or with status {@code 400 (Bad Request)} if the sysOrganizationArea is not valid,
     * or with status {@code 404 (Not Found)} if the sysOrganizationArea is not found,
     * or with status {@code 500 (Internal Server Error)} if the sysOrganizationArea couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sys-organization-areas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SysOrganizationArea> partialUpdateSysOrganizationArea(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SysOrganizationArea sysOrganizationArea
    ) throws URISyntaxException {
        log.debug("REST request to partial update SysOrganizationArea partially : {}, {}", id, sysOrganizationArea);
        if (sysOrganizationArea.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sysOrganizationArea.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sysOrganizationAreaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SysOrganizationArea> result = sysOrganizationAreaRepository
            .findById(sysOrganizationArea.getId())
            .map(existingSysOrganizationArea -> {
                if (sysOrganizationArea.getOrgId() != null) {
                    existingSysOrganizationArea.setOrgId(sysOrganizationArea.getOrgId());
                }
                if (sysOrganizationArea.getAreaId() != null) {
                    existingSysOrganizationArea.setAreaId(sysOrganizationArea.getAreaId());
                }
                if (sysOrganizationArea.getIsActive() != null) {
                    existingSysOrganizationArea.setIsActive(sysOrganizationArea.getIsActive());
                }
                if (sysOrganizationArea.getCreatedBy() != null) {
                    existingSysOrganizationArea.setCreatedBy(sysOrganizationArea.getCreatedBy());
                }
                if (sysOrganizationArea.getCreatedDate() != null) {
                    existingSysOrganizationArea.setCreatedDate(sysOrganizationArea.getCreatedDate());
                }
                if (sysOrganizationArea.getLastModifiedBy() != null) {
                    existingSysOrganizationArea.setLastModifiedBy(sysOrganizationArea.getLastModifiedBy());
                }
                if (sysOrganizationArea.getLastModifiedDate() != null) {
                    existingSysOrganizationArea.setLastModifiedDate(sysOrganizationArea.getLastModifiedDate());
                }

                return existingSysOrganizationArea;
            })
            .map(sysOrganizationAreaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysOrganizationArea.getId().toString())
        );
    }

    /**
     * {@code GET  /sys-organization-areas} : get all the sysOrganizationAreas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysOrganizationAreas in body.
     */
    @GetMapping("/sys-organization-areas")
    public List<SysOrganizationArea> getAllSysOrganizationAreas() {
        log.debug("REST request to get all SysOrganizationAreas");
        return sysOrganizationAreaRepository.findAll();
    }

    /**
     * {@code GET  /sys-organization-areas/:id} : get the "id" sysOrganizationArea.
     *
     * @param id the id of the sysOrganizationArea to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysOrganizationArea, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-organization-areas/{id}")
    public ResponseEntity<SysOrganizationArea> getSysOrganizationArea(@PathVariable Long id) {
        log.debug("REST request to get SysOrganizationArea : {}", id);
        Optional<SysOrganizationArea> sysOrganizationArea = sysOrganizationAreaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sysOrganizationArea);
    }

    /**
     * {@code DELETE  /sys-organization-areas/:id} : delete the "id" sysOrganizationArea.
     *
     * @param id the id of the sysOrganizationArea to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-organization-areas/{id}")
    public ResponseEntity<Void> deleteSysOrganizationArea(@PathVariable Long id) {
        log.debug("REST request to delete SysOrganizationArea : {}", id);
        sysOrganizationAreaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
