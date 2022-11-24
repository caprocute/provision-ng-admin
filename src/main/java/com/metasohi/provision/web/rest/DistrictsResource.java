package com.metasohi.provision.web.rest;

import com.metasohi.provision.repository.DistrictsRepository;
import com.metasohi.provision.service.DistrictsService;
import com.metasohi.provision.service.dto.DistrictsDTO;
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
 * REST controller for managing {@link com.metasohi.provision.domain.Districts}.
 */
@RestController
@RequestMapping("/api")
public class DistrictsResource {

    private final Logger log = LoggerFactory.getLogger(DistrictsResource.class);

    private static final String ENTITY_NAME = "districts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistrictsService districtsService;

    private final DistrictsRepository districtsRepository;

    public DistrictsResource(DistrictsService districtsService, DistrictsRepository districtsRepository) {
        this.districtsService = districtsService;
        this.districtsRepository = districtsRepository;
    }

    /**
     * {@code POST  /districts} : Create a new districts.
     *
     * @param districtsDTO the districtsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new districtsDTO, or with status {@code 400 (Bad Request)} if the districts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/districts")
    public ResponseEntity<DistrictsDTO> createDistricts(@RequestBody DistrictsDTO districtsDTO) throws URISyntaxException {
        log.debug("REST request to save Districts : {}", districtsDTO);
        if (districtsDTO.getCode() != null) {
            throw new BadRequestAlertException("A new districts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DistrictsDTO result = districtsService.save(districtsDTO);
        return ResponseEntity
            .created(new URI("/api/districts/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCode()))
            .body(result);
    }

    /**
     * {@code PUT  /districts/:code} : Updates an existing districts.
     *
     * @param code the id of the districtsDTO to save.
     * @param districtsDTO the districtsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtsDTO,
     * or with status {@code 400 (Bad Request)} if the districtsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the districtsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/districts/{code}")
    public ResponseEntity<DistrictsDTO> updateDistricts(
        @PathVariable(value = "code", required = false) final String code,
        @RequestBody DistrictsDTO districtsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Districts : {}, {}", code, districtsDTO);
        if (districtsDTO.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, districtsDTO.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!districtsRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DistrictsDTO result = districtsService.update(districtsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, districtsDTO.getCode()))
            .body(result);
    }

    /**
     * {@code PATCH  /districts/:code} : Partial updates given fields of an existing districts, field will ignore if it is null
     *
     * @param code the id of the districtsDTO to save.
     * @param districtsDTO the districtsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtsDTO,
     * or with status {@code 400 (Bad Request)} if the districtsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the districtsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the districtsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/districts/{code}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DistrictsDTO> partialUpdateDistricts(
        @PathVariable(value = "code", required = false) final String code,
        @RequestBody DistrictsDTO districtsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Districts partially : {}, {}", code, districtsDTO);
        if (districtsDTO.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, districtsDTO.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!districtsRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DistrictsDTO> result = districtsService.partialUpdate(districtsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, districtsDTO.getCode())
        );
    }

    /**
     * {@code GET  /districts} : get all the districts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of districts in body.
     */
    @GetMapping("/districts")
    public ResponseEntity<List<DistrictsDTO>> getAllDistricts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Districts");
        Page<DistrictsDTO> page = districtsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /districts/:id} : get the "id" districts.
     *
     * @param id the id of the districtsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the districtsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/districts/{id}")
    public ResponseEntity<DistrictsDTO> getDistricts(@PathVariable String id) {
        log.debug("REST request to get Districts : {}", id);
        Optional<DistrictsDTO> districtsDTO = districtsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(districtsDTO);
    }

    /**
     * {@code DELETE  /districts/:id} : delete the "id" districts.
     *
     * @param id the id of the districtsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/districts/{id}")
    public ResponseEntity<Void> deleteDistricts(@PathVariable String id) {
        log.debug("REST request to delete Districts : {}", id);
        districtsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
