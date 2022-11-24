package com.metasohi.provision.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.metasohi.provision.IntegrationTest;
import com.metasohi.provision.domain.AdministrativeRegions;
import com.metasohi.provision.repository.AdministrativeRegionsRepository;
import com.metasohi.provision.service.dto.AdministrativeRegionsDTO;
import com.metasohi.provision.service.mapper.AdministrativeRegionsMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AdministrativeRegionsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AdministrativeRegionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CODE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_CODE_NAME_EN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/administrative-regions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdministrativeRegionsRepository administrativeRegionsRepository;

    @Autowired
    private AdministrativeRegionsMapper administrativeRegionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdministrativeRegionsMockMvc;

    private AdministrativeRegions administrativeRegions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdministrativeRegions createEntity(EntityManager em) {
        AdministrativeRegions administrativeRegions = new AdministrativeRegions()
            .name(DEFAULT_NAME)
            .nameEn(DEFAULT_NAME_EN)
            .codeName(DEFAULT_CODE_NAME)
            .codeNameEn(DEFAULT_CODE_NAME_EN);
        return administrativeRegions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdministrativeRegions createUpdatedEntity(EntityManager em) {
        AdministrativeRegions administrativeRegions = new AdministrativeRegions()
            .name(UPDATED_NAME)
            .nameEn(UPDATED_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .codeNameEn(UPDATED_CODE_NAME_EN);
        return administrativeRegions;
    }

    @BeforeEach
    public void initTest() {
        administrativeRegions = createEntity(em);
    }

    @Test
    @Transactional
    void createAdministrativeRegions() throws Exception {
        int databaseSizeBeforeCreate = administrativeRegionsRepository.findAll().size();
        // Create the AdministrativeRegions
        AdministrativeRegionsDTO administrativeRegionsDTO = administrativeRegionsMapper.toDto(administrativeRegions);
        restAdministrativeRegionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeRegionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdministrativeRegions in the database
        List<AdministrativeRegions> administrativeRegionsList = administrativeRegionsRepository.findAll();
        assertThat(administrativeRegionsList).hasSize(databaseSizeBeforeCreate + 1);
        AdministrativeRegions testAdministrativeRegions = administrativeRegionsList.get(administrativeRegionsList.size() - 1);
        assertThat(testAdministrativeRegions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdministrativeRegions.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testAdministrativeRegions.getCodeName()).isEqualTo(DEFAULT_CODE_NAME);
        assertThat(testAdministrativeRegions.getCodeNameEn()).isEqualTo(DEFAULT_CODE_NAME_EN);
    }

    @Test
    @Transactional
    void createAdministrativeRegionsWithExistingId() throws Exception {
        // Create the AdministrativeRegions with an existing ID
        administrativeRegions.setId(1L);
        AdministrativeRegionsDTO administrativeRegionsDTO = administrativeRegionsMapper.toDto(administrativeRegions);

        int databaseSizeBeforeCreate = administrativeRegionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdministrativeRegionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeRegionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeRegions in the database
        List<AdministrativeRegions> administrativeRegionsList = administrativeRegionsRepository.findAll();
        assertThat(administrativeRegionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdministrativeRegions() throws Exception {
        // Initialize the database
        administrativeRegionsRepository.saveAndFlush(administrativeRegions);

        // Get all the administrativeRegionsList
        restAdministrativeRegionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administrativeRegions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].codeName").value(hasItem(DEFAULT_CODE_NAME)))
            .andExpect(jsonPath("$.[*].codeNameEn").value(hasItem(DEFAULT_CODE_NAME_EN)));
    }

    @Test
    @Transactional
    void getAdministrativeRegions() throws Exception {
        // Initialize the database
        administrativeRegionsRepository.saveAndFlush(administrativeRegions);

        // Get the administrativeRegions
        restAdministrativeRegionsMockMvc
            .perform(get(ENTITY_API_URL_ID, administrativeRegions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(administrativeRegions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.codeName").value(DEFAULT_CODE_NAME))
            .andExpect(jsonPath("$.codeNameEn").value(DEFAULT_CODE_NAME_EN));
    }

    @Test
    @Transactional
    void getNonExistingAdministrativeRegions() throws Exception {
        // Get the administrativeRegions
        restAdministrativeRegionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdministrativeRegions() throws Exception {
        // Initialize the database
        administrativeRegionsRepository.saveAndFlush(administrativeRegions);

        int databaseSizeBeforeUpdate = administrativeRegionsRepository.findAll().size();

        // Update the administrativeRegions
        AdministrativeRegions updatedAdministrativeRegions = administrativeRegionsRepository.findById(administrativeRegions.getId()).get();
        // Disconnect from session so that the updates on updatedAdministrativeRegions are not directly saved in db
        em.detach(updatedAdministrativeRegions);
        updatedAdministrativeRegions
            .name(UPDATED_NAME)
            .nameEn(UPDATED_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .codeNameEn(UPDATED_CODE_NAME_EN);
        AdministrativeRegionsDTO administrativeRegionsDTO = administrativeRegionsMapper.toDto(updatedAdministrativeRegions);

        restAdministrativeRegionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, administrativeRegionsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeRegionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdministrativeRegions in the database
        List<AdministrativeRegions> administrativeRegionsList = administrativeRegionsRepository.findAll();
        assertThat(administrativeRegionsList).hasSize(databaseSizeBeforeUpdate);
        AdministrativeRegions testAdministrativeRegions = administrativeRegionsList.get(administrativeRegionsList.size() - 1);
        assertThat(testAdministrativeRegions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdministrativeRegions.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testAdministrativeRegions.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testAdministrativeRegions.getCodeNameEn()).isEqualTo(UPDATED_CODE_NAME_EN);
    }

    @Test
    @Transactional
    void putNonExistingAdministrativeRegions() throws Exception {
        int databaseSizeBeforeUpdate = administrativeRegionsRepository.findAll().size();
        administrativeRegions.setId(count.incrementAndGet());

        // Create the AdministrativeRegions
        AdministrativeRegionsDTO administrativeRegionsDTO = administrativeRegionsMapper.toDto(administrativeRegions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministrativeRegionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, administrativeRegionsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeRegionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeRegions in the database
        List<AdministrativeRegions> administrativeRegionsList = administrativeRegionsRepository.findAll();
        assertThat(administrativeRegionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdministrativeRegions() throws Exception {
        int databaseSizeBeforeUpdate = administrativeRegionsRepository.findAll().size();
        administrativeRegions.setId(count.incrementAndGet());

        // Create the AdministrativeRegions
        AdministrativeRegionsDTO administrativeRegionsDTO = administrativeRegionsMapper.toDto(administrativeRegions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrativeRegionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeRegionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeRegions in the database
        List<AdministrativeRegions> administrativeRegionsList = administrativeRegionsRepository.findAll();
        assertThat(administrativeRegionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdministrativeRegions() throws Exception {
        int databaseSizeBeforeUpdate = administrativeRegionsRepository.findAll().size();
        administrativeRegions.setId(count.incrementAndGet());

        // Create the AdministrativeRegions
        AdministrativeRegionsDTO administrativeRegionsDTO = administrativeRegionsMapper.toDto(administrativeRegions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrativeRegionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeRegionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdministrativeRegions in the database
        List<AdministrativeRegions> administrativeRegionsList = administrativeRegionsRepository.findAll();
        assertThat(administrativeRegionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdministrativeRegionsWithPatch() throws Exception {
        // Initialize the database
        administrativeRegionsRepository.saveAndFlush(administrativeRegions);

        int databaseSizeBeforeUpdate = administrativeRegionsRepository.findAll().size();

        // Update the administrativeRegions using partial update
        AdministrativeRegions partialUpdatedAdministrativeRegions = new AdministrativeRegions();
        partialUpdatedAdministrativeRegions.setId(administrativeRegions.getId());

        partialUpdatedAdministrativeRegions.codeName(UPDATED_CODE_NAME).codeNameEn(UPDATED_CODE_NAME_EN);

        restAdministrativeRegionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdministrativeRegions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdministrativeRegions))
            )
            .andExpect(status().isOk());

        // Validate the AdministrativeRegions in the database
        List<AdministrativeRegions> administrativeRegionsList = administrativeRegionsRepository.findAll();
        assertThat(administrativeRegionsList).hasSize(databaseSizeBeforeUpdate);
        AdministrativeRegions testAdministrativeRegions = administrativeRegionsList.get(administrativeRegionsList.size() - 1);
        assertThat(testAdministrativeRegions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdministrativeRegions.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testAdministrativeRegions.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testAdministrativeRegions.getCodeNameEn()).isEqualTo(UPDATED_CODE_NAME_EN);
    }

    @Test
    @Transactional
    void fullUpdateAdministrativeRegionsWithPatch() throws Exception {
        // Initialize the database
        administrativeRegionsRepository.saveAndFlush(administrativeRegions);

        int databaseSizeBeforeUpdate = administrativeRegionsRepository.findAll().size();

        // Update the administrativeRegions using partial update
        AdministrativeRegions partialUpdatedAdministrativeRegions = new AdministrativeRegions();
        partialUpdatedAdministrativeRegions.setId(administrativeRegions.getId());

        partialUpdatedAdministrativeRegions
            .name(UPDATED_NAME)
            .nameEn(UPDATED_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .codeNameEn(UPDATED_CODE_NAME_EN);

        restAdministrativeRegionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdministrativeRegions.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdministrativeRegions))
            )
            .andExpect(status().isOk());

        // Validate the AdministrativeRegions in the database
        List<AdministrativeRegions> administrativeRegionsList = administrativeRegionsRepository.findAll();
        assertThat(administrativeRegionsList).hasSize(databaseSizeBeforeUpdate);
        AdministrativeRegions testAdministrativeRegions = administrativeRegionsList.get(administrativeRegionsList.size() - 1);
        assertThat(testAdministrativeRegions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdministrativeRegions.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testAdministrativeRegions.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testAdministrativeRegions.getCodeNameEn()).isEqualTo(UPDATED_CODE_NAME_EN);
    }

    @Test
    @Transactional
    void patchNonExistingAdministrativeRegions() throws Exception {
        int databaseSizeBeforeUpdate = administrativeRegionsRepository.findAll().size();
        administrativeRegions.setId(count.incrementAndGet());

        // Create the AdministrativeRegions
        AdministrativeRegionsDTO administrativeRegionsDTO = administrativeRegionsMapper.toDto(administrativeRegions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministrativeRegionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, administrativeRegionsDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(administrativeRegionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeRegions in the database
        List<AdministrativeRegions> administrativeRegionsList = administrativeRegionsRepository.findAll();
        assertThat(administrativeRegionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdministrativeRegions() throws Exception {
        int databaseSizeBeforeUpdate = administrativeRegionsRepository.findAll().size();
        administrativeRegions.setId(count.incrementAndGet());

        // Create the AdministrativeRegions
        AdministrativeRegionsDTO administrativeRegionsDTO = administrativeRegionsMapper.toDto(administrativeRegions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrativeRegionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(administrativeRegionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeRegions in the database
        List<AdministrativeRegions> administrativeRegionsList = administrativeRegionsRepository.findAll();
        assertThat(administrativeRegionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdministrativeRegions() throws Exception {
        int databaseSizeBeforeUpdate = administrativeRegionsRepository.findAll().size();
        administrativeRegions.setId(count.incrementAndGet());

        // Create the AdministrativeRegions
        AdministrativeRegionsDTO administrativeRegionsDTO = administrativeRegionsMapper.toDto(administrativeRegions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrativeRegionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(administrativeRegionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdministrativeRegions in the database
        List<AdministrativeRegions> administrativeRegionsList = administrativeRegionsRepository.findAll();
        assertThat(administrativeRegionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdministrativeRegions() throws Exception {
        // Initialize the database
        administrativeRegionsRepository.saveAndFlush(administrativeRegions);

        int databaseSizeBeforeDelete = administrativeRegionsRepository.findAll().size();

        // Delete the administrativeRegions
        restAdministrativeRegionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, administrativeRegions.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdministrativeRegions> administrativeRegionsList = administrativeRegionsRepository.findAll();
        assertThat(administrativeRegionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
