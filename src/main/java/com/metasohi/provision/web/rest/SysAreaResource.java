package com.metasohi.provision.web.rest;

import com.metasohi.provision.repository.SysAreaRepository;
import com.metasohi.provision.service.SysAreaService;
import com.metasohi.provision.service.dto.SysAreaDTO;
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
 * REST controller for managing {@link com.metasohi.provision.domain.SysArea}.
 */
@RestController
@RequestMapping("/api")
public class SysAreaResource {

    private final Logger log = LoggerFactory.getLogger(SysAreaResource.class);

    private static final String ENTITY_NAME = "sysArea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysAreaService sysAreaService;

    private final SysAreaRepository sysAreaRepository;

    public SysAreaResource(SysAreaService sysAreaService, SysAreaRepository sysAreaRepository) {
        this.sysAreaService = sysAreaService;
        this.sysAreaRepository = sysAreaRepository;
    }

    /**
     * {@code POST  /sys-areas} : Create a new sysArea.
     *
     * @param sysAreaDTO the sysAreaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysAreaDTO, or with status {@code 400 (Bad Request)} if the sysArea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-areas")
    public ResponseEntity<SysAreaDTO> createSysArea(@RequestBody SysAreaDTO sysAreaDTO) throws URISyntaxException {
        log.debug("REST request to save SysArea : {}", sysAreaDTO);
        if (sysAreaDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysArea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysAreaDTO result = sysAreaService.save(sysAreaDTO);
        return ResponseEntity
            .created(new URI("/api/sys-areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-areas/:id} : Updates an existing sysArea.
     *
     * @param id the id of the sysAreaDTO to save.
     * @param sysAreaDTO the sysAreaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysAreaDTO,
     * or with status {@code 400 (Bad Request)} if the sysAreaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysAreaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-areas/{id}")
    public ResponseEntity<SysAreaDTO> updateSysArea(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SysAreaDTO sysAreaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SysArea : {}, {}", id, sysAreaDTO);
        if (sysAreaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sysAreaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sysAreaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SysAreaDTO result = sysAreaService.update(sysAreaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysAreaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sys-areas/:id} : Partial updates given fields of an existing sysArea, field will ignore if it is null
     *
     * @param id the id of the sysAreaDTO to save.
     * @param sysAreaDTO the sysAreaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysAreaDTO,
     * or with status {@code 400 (Bad Request)} if the sysAreaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sysAreaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sysAreaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sys-areas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SysAreaDTO> partialUpdateSysArea(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SysAreaDTO sysAreaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SysArea partially : {}, {}", id, sysAreaDTO);
        if (sysAreaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sysAreaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sysAreaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SysAreaDTO> result = sysAreaService.partialUpdate(sysAreaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysAreaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sys-areas} : get all the sysAreas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysAreas in body.
     */
    @GetMapping("/sys-areas")
    public ResponseEntity<List<SysAreaDTO>> getAllSysAreas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SysAreas");
        Page<SysAreaDTO> page = sysAreaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sys-areas/:id} : get the "id" sysArea.
     *
     * @param id the id of the sysAreaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysAreaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-areas/{id}")
    public ResponseEntity<SysAreaDTO> getSysArea(@PathVariable Long id) {
        log.debug("REST request to get SysArea : {}", id);
        Optional<SysAreaDTO> sysAreaDTO = sysAreaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysAreaDTO);
    }

    /**
     * {@code DELETE  /sys-areas/:id} : delete the "id" sysArea.
     *
     * @param id the id of the sysAreaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-areas/{id}")
    public ResponseEntity<Void> deleteSysArea(@PathVariable Long id) {
        log.debug("REST request to delete SysArea : {}", id);
        sysAreaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
