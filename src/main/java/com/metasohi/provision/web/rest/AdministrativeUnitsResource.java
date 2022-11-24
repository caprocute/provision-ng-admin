package com.metasohi.provision.web.rest;

import com.metasohi.provision.repository.AdministrativeUnitsRepository;
import com.metasohi.provision.service.AdministrativeUnitsService;
import com.metasohi.provision.service.dto.AdministrativeUnitsDTO;
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
 * REST controller for managing {@link com.metasohi.provision.domain.AdministrativeUnits}.
 */
@RestController
@RequestMapping("/api")
public class AdministrativeUnitsResource {

    private final Logger log = LoggerFactory.getLogger(AdministrativeUnitsResource.class);

    private static final String ENTITY_NAME = "administrativeUnits";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdministrativeUnitsService administrativeUnitsService;

    private final AdministrativeUnitsRepository administrativeUnitsRepository;

    public AdministrativeUnitsResource(
        AdministrativeUnitsService administrativeUnitsService,
        AdministrativeUnitsRepository administrativeUnitsRepository
    ) {
        this.administrativeUnitsService = administrativeUnitsService;
        this.administrativeUnitsRepository = administrativeUnitsRepository;
    }

    /**
     * {@code POST  /administrative-units} : Create a new administrativeUnits.
     *
     * @param administrativeUnitsDTO the administrativeUnitsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new administrativeUnitsDTO, or with status {@code 400 (Bad Request)} if the administrativeUnits has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/administrative-units")
    public ResponseEntity<AdministrativeUnitsDTO> createAdministrativeUnits(@RequestBody AdministrativeUnitsDTO administrativeUnitsDTO)
        throws URISyntaxException {
        log.debug("REST request to save AdministrativeUnits : {}", administrativeUnitsDTO);
        if (administrativeUnitsDTO.getId() != null) {
            throw new BadRequestAlertException("A new administrativeUnits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdministrativeUnitsDTO result = administrativeUnitsService.save(administrativeUnitsDTO);
        return ResponseEntity
            .created(new URI("/api/administrative-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /administrative-units/:id} : Updates an existing administrativeUnits.
     *
     * @param id the id of the administrativeUnitsDTO to save.
     * @param administrativeUnitsDTO the administrativeUnitsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrativeUnitsDTO,
     * or with status {@code 400 (Bad Request)} if the administrativeUnitsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the administrativeUnitsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/administrative-units/{id}")
    public ResponseEntity<AdministrativeUnitsDTO> updateAdministrativeUnits(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdministrativeUnitsDTO administrativeUnitsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdministrativeUnits : {}, {}", id, administrativeUnitsDTO);
        if (administrativeUnitsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, administrativeUnitsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!administrativeUnitsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdministrativeUnitsDTO result = administrativeUnitsService.update(administrativeUnitsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administrativeUnitsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /administrative-units/:id} : Partial updates given fields of an existing administrativeUnits, field will ignore if it is null
     *
     * @param id the id of the administrativeUnitsDTO to save.
     * @param administrativeUnitsDTO the administrativeUnitsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrativeUnitsDTO,
     * or with status {@code 400 (Bad Request)} if the administrativeUnitsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the administrativeUnitsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the administrativeUnitsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/administrative-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdministrativeUnitsDTO> partialUpdateAdministrativeUnits(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdministrativeUnitsDTO administrativeUnitsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdministrativeUnits partially : {}, {}", id, administrativeUnitsDTO);
        if (administrativeUnitsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, administrativeUnitsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!administrativeUnitsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdministrativeUnitsDTO> result = administrativeUnitsService.partialUpdate(administrativeUnitsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administrativeUnitsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /administrative-units} : get all the administrativeUnits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of administrativeUnits in body.
     */
    @GetMapping("/administrative-units")
    public ResponseEntity<List<AdministrativeUnitsDTO>> getAllAdministrativeUnits(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AdministrativeUnits");
        Page<AdministrativeUnitsDTO> page = administrativeUnitsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /administrative-units/:id} : get the "id" administrativeUnits.
     *
     * @param id the id of the administrativeUnitsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the administrativeUnitsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/administrative-units/{id}")
    public ResponseEntity<AdministrativeUnitsDTO> getAdministrativeUnits(@PathVariable Long id) {
        log.debug("REST request to get AdministrativeUnits : {}", id);
        Optional<AdministrativeUnitsDTO> administrativeUnitsDTO = administrativeUnitsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(administrativeUnitsDTO);
    }

    /**
     * {@code DELETE  /administrative-units/:id} : delete the "id" administrativeUnits.
     *
     * @param id the id of the administrativeUnitsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/administrative-units/{id}")
    public ResponseEntity<Void> deleteAdministrativeUnits(@PathVariable Long id) {
        log.debug("REST request to delete AdministrativeUnits : {}", id);
        administrativeUnitsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
