package com.metasohi.provision.web.rest;

import com.metasohi.provision.repository.SysCameraRepository;
import com.metasohi.provision.service.SysCameraService;
import com.metasohi.provision.service.dto.SysCameraDTO;
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
 * REST controller for managing {@link com.metasohi.provision.domain.SysCamera}.
 */
@RestController
@RequestMapping("/api")
public class SysCameraResource {

    private final Logger log = LoggerFactory.getLogger(SysCameraResource.class);

    private static final String ENTITY_NAME = "sysCamera";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysCameraService sysCameraService;

    private final SysCameraRepository sysCameraRepository;

    public SysCameraResource(SysCameraService sysCameraService, SysCameraRepository sysCameraRepository) {
        this.sysCameraService = sysCameraService;
        this.sysCameraRepository = sysCameraRepository;
    }

    /**
     * {@code POST  /sys-cameras} : Create a new sysCamera.
     *
     * @param sysCameraDTO the sysCameraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysCameraDTO, or with status {@code 400 (Bad Request)} if the sysCamera has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-cameras")
    public ResponseEntity<SysCameraDTO> createSysCamera(@RequestBody SysCameraDTO sysCameraDTO) throws URISyntaxException {
        log.debug("REST request to save SysCamera : {}", sysCameraDTO);
        if (sysCameraDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysCamera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysCameraDTO result = sysCameraService.save(sysCameraDTO);
        return ResponseEntity
            .created(new URI("/api/sys-cameras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-cameras/:id} : Updates an existing sysCamera.
     *
     * @param id the id of the sysCameraDTO to save.
     * @param sysCameraDTO the sysCameraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysCameraDTO,
     * or with status {@code 400 (Bad Request)} if the sysCameraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysCameraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-cameras/{id}")
    public ResponseEntity<SysCameraDTO> updateSysCamera(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SysCameraDTO sysCameraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SysCamera : {}, {}", id, sysCameraDTO);
        if (sysCameraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sysCameraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sysCameraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SysCameraDTO result = sysCameraService.update(sysCameraDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysCameraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sys-cameras/:id} : Partial updates given fields of an existing sysCamera, field will ignore if it is null
     *
     * @param id the id of the sysCameraDTO to save.
     * @param sysCameraDTO the sysCameraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysCameraDTO,
     * or with status {@code 400 (Bad Request)} if the sysCameraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sysCameraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sysCameraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sys-cameras/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SysCameraDTO> partialUpdateSysCamera(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SysCameraDTO sysCameraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SysCamera partially : {}, {}", id, sysCameraDTO);
        if (sysCameraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sysCameraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sysCameraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SysCameraDTO> result = sysCameraService.partialUpdate(sysCameraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysCameraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sys-cameras} : get all the sysCameras.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysCameras in body.
     */
    @GetMapping("/sys-cameras")
    public ResponseEntity<List<SysCameraDTO>> getAllSysCameras(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SysCameras");
        Page<SysCameraDTO> page = sysCameraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sys-cameras/:id} : get the "id" sysCamera.
     *
     * @param id the id of the sysCameraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysCameraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-cameras/{id}")
    public ResponseEntity<SysCameraDTO> getSysCamera(@PathVariable Long id) {
        log.debug("REST request to get SysCamera : {}", id);
        Optional<SysCameraDTO> sysCameraDTO = sysCameraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysCameraDTO);
    }

    /**
     * {@code DELETE  /sys-cameras/:id} : delete the "id" sysCamera.
     *
     * @param id the id of the sysCameraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-cameras/{id}")
    public ResponseEntity<Void> deleteSysCamera(@PathVariable Long id) {
        log.debug("REST request to delete SysCamera : {}", id);
        sysCameraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
