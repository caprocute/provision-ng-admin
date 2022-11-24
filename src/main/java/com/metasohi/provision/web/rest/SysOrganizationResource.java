package com.metasohi.provision.web.rest;

import com.metasohi.provision.repository.SysOrganizationRepository;
import com.metasohi.provision.service.SysOrganizationService;
import com.metasohi.provision.service.dto.SysOrganizationDTO;
import com.metasohi.provision.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.metasohi.provision.domain.SysOrganization}.
 */
@RestController
@RequestMapping("/api")
public class SysOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(SysOrganizationResource.class);

    private static final String ENTITY_NAME = "sysOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysOrganizationService sysOrganizationService;

    private final SysOrganizationRepository sysOrganizationRepository;

    public SysOrganizationResource(SysOrganizationService sysOrganizationService, SysOrganizationRepository sysOrganizationRepository) {
        this.sysOrganizationService = sysOrganizationService;
        this.sysOrganizationRepository = sysOrganizationRepository;
    }

    /**
     * {@code POST  /sys-organizations} : Create a new sysOrganization.
     *
     * @param sysOrganizationDTO the sysOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysOrganizationDTO, or with status {@code 400 (Bad Request)} if the sysOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-organizations")
    public ResponseEntity<SysOrganizationDTO> createSysOrganization(@RequestBody SysOrganizationDTO sysOrganizationDTO)
        throws URISyntaxException {
        log.debug("REST request to save SysOrganization : {}", sysOrganizationDTO);
        if (sysOrganizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysOrganization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysOrganizationDTO result = sysOrganizationService.save(sysOrganizationDTO);
        return ResponseEntity
            .created(new URI("/api/sys-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-organizations/:id} : Updates an existing sysOrganization.
     *
     * @param id the id of the sysOrganizationDTO to save.
     * @param sysOrganizationDTO the sysOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the sysOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-organizations/{id}")
    public ResponseEntity<SysOrganizationDTO> updateSysOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SysOrganizationDTO sysOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SysOrganization : {}, {}", id, sysOrganizationDTO);
        if (sysOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sysOrganizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sysOrganizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SysOrganizationDTO result = sysOrganizationService.update(sysOrganizationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysOrganizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sys-organizations/:id} : Partial updates given fields of an existing sysOrganization, field will ignore if it is null
     *
     * @param id the id of the sysOrganizationDTO to save.
     * @param sysOrganizationDTO the sysOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the sysOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sysOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sysOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sys-organizations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SysOrganizationDTO> partialUpdateSysOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SysOrganizationDTO sysOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SysOrganization partially : {}, {}", id, sysOrganizationDTO);
        if (sysOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sysOrganizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sysOrganizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SysOrganizationDTO> result = sysOrganizationService.partialUpdate(sysOrganizationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysOrganizationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sys-organizations} : get all the sysOrganizations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysOrganizations in body.
     */
    @GetMapping("/sys-organizations")
    public ResponseEntity<List<SysOrganizationDTO>> getAllSysOrganizations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SysOrganizations");
        Page<SysOrganizationDTO> page = sysOrganizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sys-organizations/:id} : get the "id" sysOrganization.
     *
     * @param id the id of the sysOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-organizations/{id}")
    public ResponseEntity<SysOrganizationDTO> getSysOrganization(@PathVariable Long id) {
        log.debug("REST request to get SysOrganization : {}", id);
        Optional<SysOrganizationDTO> sysOrganizationDTO = sysOrganizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysOrganizationDTO);
    }

    /**
     * {@code DELETE  /sys-organizations/:id} : delete the "id" sysOrganization.
     *
     * @param id the id of the sysOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-organizations/{id}")
    public ResponseEntity<Void> deleteSysOrganization(@PathVariable Long id) {
        log.debug("REST request to delete SysOrganization : {}", id);
        sysOrganizationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
