package com.metasohi.provision.web.rest;

import com.metasohi.provision.repository.WardsRepository;
import com.metasohi.provision.service.WardsService;
import com.metasohi.provision.service.dto.WardsDTO;
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
 * REST controller for managing {@link com.metasohi.provision.domain.Wards}.
 */
@RestController
@RequestMapping("/api")
public class WardsResource {

    private final Logger log = LoggerFactory.getLogger(WardsResource.class);

    private static final String ENTITY_NAME = "wards";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WardsService wardsService;

    private final WardsRepository wardsRepository;

    public WardsResource(WardsService wardsService, WardsRepository wardsRepository) {
        this.wardsService = wardsService;
        this.wardsRepository = wardsRepository;
    }

    /**
     * {@code POST  /wards} : Create a new wards.
     *
     * @param wardsDTO the wardsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wardsDTO, or with status {@code 400 (Bad Request)} if the wards has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wards")
    public ResponseEntity<WardsDTO> createWards(@RequestBody WardsDTO wardsDTO) throws URISyntaxException {
        log.debug("REST request to save Wards : {}", wardsDTO);
        if (wardsDTO.getCode() != null) {
            throw new BadRequestAlertException("A new wards cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WardsDTO result = wardsService.save(wardsDTO);
        return ResponseEntity
            .created(new URI("/api/wards/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCode()))
            .body(result);
    }

    /**
     * {@code PUT  /wards/:code} : Updates an existing wards.
     *
     * @param code the id of the wardsDTO to save.
     * @param wardsDTO the wardsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wardsDTO,
     * or with status {@code 400 (Bad Request)} if the wardsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wardsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wards/{code}")
    public ResponseEntity<WardsDTO> updateWards(
        @PathVariable(value = "code", required = false) final String code,
        @RequestBody WardsDTO wardsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Wards : {}, {}", code, wardsDTO);
        if (wardsDTO.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, wardsDTO.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wardsRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WardsDTO result = wardsService.update(wardsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wardsDTO.getCode()))
            .body(result);
    }

    /**
     * {@code PATCH  /wards/:code} : Partial updates given fields of an existing wards, field will ignore if it is null
     *
     * @param code the id of the wardsDTO to save.
     * @param wardsDTO the wardsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wardsDTO,
     * or with status {@code 400 (Bad Request)} if the wardsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the wardsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the wardsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/wards/{code}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WardsDTO> partialUpdateWards(
        @PathVariable(value = "code", required = false) final String code,
        @RequestBody WardsDTO wardsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wards partially : {}, {}", code, wardsDTO);
        if (wardsDTO.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, wardsDTO.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wardsRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WardsDTO> result = wardsService.partialUpdate(wardsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wardsDTO.getCode())
        );
    }

    /**
     * {@code GET  /wards} : get all the wards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wards in body.
     */
    @GetMapping("/wards")
    public ResponseEntity<List<WardsDTO>> getAllWards(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Wards");
        Page<WardsDTO> page = wardsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wards/:id} : get the "id" wards.
     *
     * @param id the id of the wardsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wardsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wards/{id}")
    public ResponseEntity<WardsDTO> getWards(@PathVariable String id) {
        log.debug("REST request to get Wards : {}", id);
        Optional<WardsDTO> wardsDTO = wardsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wardsDTO);
    }

    /**
     * {@code DELETE  /wards/:id} : delete the "id" wards.
     *
     * @param id the id of the wardsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wards/{id}")
    public ResponseEntity<Void> deleteWards(@PathVariable String id) {
        log.debug("REST request to delete Wards : {}", id);
        wardsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
