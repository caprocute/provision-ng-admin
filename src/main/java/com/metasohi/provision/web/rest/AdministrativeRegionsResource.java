package com.metasohi.provision.web.rest;

import com.metasohi.provision.repository.AdministrativeRegionsRepository;
import com.metasohi.provision.service.AdministrativeRegionsService;
import com.metasohi.provision.service.dto.AdministrativeRegionsDTO;
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
 * REST controller for managing {@link com.metasohi.provision.domain.AdministrativeRegions}.
 */
@RestController
@RequestMapping("/api")
public class AdministrativeRegionsResource {

    private final Logger log = LoggerFactory.getLogger(AdministrativeRegionsResource.class);

    private static final String ENTITY_NAME = "administrativeRegions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdministrativeRegionsService administrativeRegionsService;

    private final AdministrativeRegionsRepository administrativeRegionsRepository;

    public AdministrativeRegionsResource(
        AdministrativeRegionsService administrativeRegionsService,
        AdministrativeRegionsRepository administrativeRegionsRepository
    ) {
        this.administrativeRegionsService = administrativeRegionsService;
        this.administrativeRegionsRepository = administrativeRegionsRepository;
    }

    /**
     * {@code POST  /administrative-regions} : Create a new administrativeRegions.
     *
     * @param administrativeRegionsDTO the administrativeRegionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new administrativeRegionsDTO, or with status {@code 400 (Bad Request)} if the administrativeRegions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/administrative-regions")
    public ResponseEntity<AdministrativeRegionsDTO> createAdministrativeRegions(
        @RequestBody AdministrativeRegionsDTO administrativeRegionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AdministrativeRegions : {}", administrativeRegionsDTO);
        if (administrativeRegionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new administrativeRegions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdministrativeRegionsDTO result = administrativeRegionsService.save(administrativeRegionsDTO);
        return ResponseEntity
            .created(new URI("/api/administrative-regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /administrative-regions/:id} : Updates an existing administrativeRegions.
     *
     * @param id the id of the administrativeRegionsDTO to save.
     * @param administrativeRegionsDTO the administrativeRegionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrativeRegionsDTO,
     * or with status {@code 400 (Bad Request)} if the administrativeRegionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the administrativeRegionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/administrative-regions/{id}")
    public ResponseEntity<AdministrativeRegionsDTO> updateAdministrativeRegions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdministrativeRegionsDTO administrativeRegionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdministrativeRegions : {}, {}", id, administrativeRegionsDTO);
        if (administrativeRegionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, administrativeRegionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!administrativeRegionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdministrativeRegionsDTO result = administrativeRegionsService.update(administrativeRegionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administrativeRegionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /administrative-regions/:id} : Partial updates given fields of an existing administrativeRegions, field will ignore if it is null
     *
     * @param id the id of the administrativeRegionsDTO to save.
     * @param administrativeRegionsDTO the administrativeRegionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrativeRegionsDTO,
     * or with status {@code 400 (Bad Request)} if the administrativeRegionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the administrativeRegionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the administrativeRegionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/administrative-regions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdministrativeRegionsDTO> partialUpdateAdministrativeRegions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdministrativeRegionsDTO administrativeRegionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdministrativeRegions partially : {}, {}", id, administrativeRegionsDTO);
        if (administrativeRegionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, administrativeRegionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!administrativeRegionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdministrativeRegionsDTO> result = administrativeRegionsService.partialUpdate(administrativeRegionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administrativeRegionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /administrative-regions} : get all the administrativeRegions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of administrativeRegions in body.
     */
    @GetMapping("/administrative-regions")
    public ResponseEntity<List<AdministrativeRegionsDTO>> getAllAdministrativeRegions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AdministrativeRegions");
        Page<AdministrativeRegionsDTO> page = administrativeRegionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /administrative-regions/:id} : get the "id" administrativeRegions.
     *
     * @param id the id of the administrativeRegionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the administrativeRegionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/administrative-regions/{id}")
    public ResponseEntity<AdministrativeRegionsDTO> getAdministrativeRegions(@PathVariable Long id) {
        log.debug("REST request to get AdministrativeRegions : {}", id);
        Optional<AdministrativeRegionsDTO> administrativeRegionsDTO = administrativeRegionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(administrativeRegionsDTO);
    }

    /**
     * {@code DELETE  /administrative-regions/:id} : delete the "id" administrativeRegions.
     *
     * @param id the id of the administrativeRegionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/administrative-regions/{id}")
    public ResponseEntity<Void> deleteAdministrativeRegions(@PathVariable Long id) {
        log.debug("REST request to delete AdministrativeRegions : {}", id);
        administrativeRegionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
