package com.metasohi.provision.web.rest;

import com.metasohi.provision.repository.ProvincesRepository;
import com.metasohi.provision.service.ProvincesService;
import com.metasohi.provision.service.dto.ProvincesDTO;
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
 * REST controller for managing {@link com.metasohi.provision.domain.Provinces}.
 */
@RestController
@RequestMapping("/api")
public class ProvincesResource {

    private final Logger log = LoggerFactory.getLogger(ProvincesResource.class);

    private static final String ENTITY_NAME = "provinces";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProvincesService provincesService;

    private final ProvincesRepository provincesRepository;

    public ProvincesResource(ProvincesService provincesService, ProvincesRepository provincesRepository) {
        this.provincesService = provincesService;
        this.provincesRepository = provincesRepository;
    }

    /**
     * {@code POST  /provinces} : Create a new provinces.
     *
     * @param provincesDTO the provincesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new provincesDTO, or with status {@code 400 (Bad Request)} if the provinces has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/provinces")
    public ResponseEntity<ProvincesDTO> createProvinces(@RequestBody ProvincesDTO provincesDTO) throws URISyntaxException {
        log.debug("REST request to save Provinces : {}", provincesDTO);
        if (provincesDTO.getCode() != null) {
            throw new BadRequestAlertException("A new provinces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProvincesDTO result = provincesService.save(provincesDTO);
        return ResponseEntity
            .created(new URI("/api/provinces/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCode()))
            .body(result);
    }

    /**
     * {@code PUT  /provinces/:code} : Updates an existing provinces.
     *
     * @param code the id of the provincesDTO to save.
     * @param provincesDTO the provincesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provincesDTO,
     * or with status {@code 400 (Bad Request)} if the provincesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the provincesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/provinces/{code}")
    public ResponseEntity<ProvincesDTO> updateProvinces(
        @PathVariable(value = "code", required = false) final String code,
        @RequestBody ProvincesDTO provincesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Provinces : {}, {}", code, provincesDTO);
        if (provincesDTO.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, provincesDTO.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provincesRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProvincesDTO result = provincesService.update(provincesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, provincesDTO.getCode()))
            .body(result);
    }

    /**
     * {@code PATCH  /provinces/:code} : Partial updates given fields of an existing provinces, field will ignore if it is null
     *
     * @param code the id of the provincesDTO to save.
     * @param provincesDTO the provincesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provincesDTO,
     * or with status {@code 400 (Bad Request)} if the provincesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the provincesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the provincesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/provinces/{code}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProvincesDTO> partialUpdateProvinces(
        @PathVariable(value = "code", required = false) final String code,
        @RequestBody ProvincesDTO provincesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Provinces partially : {}, {}", code, provincesDTO);
        if (provincesDTO.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, provincesDTO.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provincesRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProvincesDTO> result = provincesService.partialUpdate(provincesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, provincesDTO.getCode())
        );
    }

    /**
     * {@code GET  /provinces} : get all the provinces.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of provinces in body.
     */
    @GetMapping("/provinces")
    public ResponseEntity<List<ProvincesDTO>> getAllProvinces(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Provinces");
        Page<ProvincesDTO> page = provincesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /provinces/:id} : get the "id" provinces.
     *
     * @param id the id of the provincesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the provincesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/provinces/{id}")
    public ResponseEntity<ProvincesDTO> getProvinces(@PathVariable String id) {
        log.debug("REST request to get Provinces : {}", id);
        Optional<ProvincesDTO> provincesDTO = provincesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(provincesDTO);
    }

    /**
     * {@code DELETE  /provinces/:id} : delete the "id" provinces.
     *
     * @param id the id of the provincesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/provinces/{id}")
    public ResponseEntity<Void> deleteProvinces(@PathVariable String id) {
        log.debug("REST request to delete Provinces : {}", id);
        provincesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
