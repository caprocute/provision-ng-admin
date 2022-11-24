package com.metasohi.provision.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.metasohi.provision.IntegrationTest;
import com.metasohi.provision.domain.AdministrativeUnits;
import com.metasohi.provision.repository.AdministrativeUnitsRepository;
import com.metasohi.provision.service.dto.AdministrativeUnitsDTO;
import com.metasohi.provision.service.mapper.AdministrativeUnitsMapper;
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
 * Integration tests for the {@link AdministrativeUnitsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AdministrativeUnitsResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CODE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_CODE_NAME_EN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/administrative-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdministrativeUnitsRepository administrativeUnitsRepository;

    @Autowired
    private AdministrativeUnitsMapper administrativeUnitsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdministrativeUnitsMockMvc;

    private AdministrativeUnits administrativeUnits;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdministrativeUnits createEntity(EntityManager em) {
        AdministrativeUnits administrativeUnits = new AdministrativeUnits()
            .fullName(DEFAULT_FULL_NAME)
            .fullNameEn(DEFAULT_FULL_NAME_EN)
            .shortName(DEFAULT_SHORT_NAME)
            .shortNameEn(DEFAULT_SHORT_NAME_EN)
            .codeName(DEFAULT_CODE_NAME)
            .codeNameEn(DEFAULT_CODE_NAME_EN);
        return administrativeUnits;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdministrativeUnits createUpdatedEntity(EntityManager em) {
        AdministrativeUnits administrativeUnits = new AdministrativeUnits()
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .shortName(UPDATED_SHORT_NAME)
            .shortNameEn(UPDATED_SHORT_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .codeNameEn(UPDATED_CODE_NAME_EN);
        return administrativeUnits;
    }

    @BeforeEach
    public void initTest() {
        administrativeUnits = createEntity(em);
    }

    @Test
    @Transactional
    void createAdministrativeUnits() throws Exception {
        int databaseSizeBeforeCreate = administrativeUnitsRepository.findAll().size();
        // Create the AdministrativeUnits
        AdministrativeUnitsDTO administrativeUnitsDTO = administrativeUnitsMapper.toDto(administrativeUnits);
        restAdministrativeUnitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeUnitsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdministrativeUnits in the database
        List<AdministrativeUnits> administrativeUnitsList = administrativeUnitsRepository.findAll();
        assertThat(administrativeUnitsList).hasSize(databaseSizeBeforeCreate + 1);
        AdministrativeUnits testAdministrativeUnits = administrativeUnitsList.get(administrativeUnitsList.size() - 1);
        assertThat(testAdministrativeUnits.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testAdministrativeUnits.getFullNameEn()).isEqualTo(DEFAULT_FULL_NAME_EN);
        assertThat(testAdministrativeUnits.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testAdministrativeUnits.getShortNameEn()).isEqualTo(DEFAULT_SHORT_NAME_EN);
        assertThat(testAdministrativeUnits.getCodeName()).isEqualTo(DEFAULT_CODE_NAME);
        assertThat(testAdministrativeUnits.getCodeNameEn()).isEqualTo(DEFAULT_CODE_NAME_EN);
    }

    @Test
    @Transactional
    void createAdministrativeUnitsWithExistingId() throws Exception {
        // Create the AdministrativeUnits with an existing ID
        administrativeUnits.setId(1L);
        AdministrativeUnitsDTO administrativeUnitsDTO = administrativeUnitsMapper.toDto(administrativeUnits);

        int databaseSizeBeforeCreate = administrativeUnitsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdministrativeUnitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeUnitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeUnits in the database
        List<AdministrativeUnits> administrativeUnitsList = administrativeUnitsRepository.findAll();
        assertThat(administrativeUnitsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdministrativeUnits() throws Exception {
        // Initialize the database
        administrativeUnitsRepository.saveAndFlush(administrativeUnits);

        // Get all the administrativeUnitsList
        restAdministrativeUnitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administrativeUnits.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].fullNameEn").value(hasItem(DEFAULT_FULL_NAME_EN)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].shortNameEn").value(hasItem(DEFAULT_SHORT_NAME_EN)))
            .andExpect(jsonPath("$.[*].codeName").value(hasItem(DEFAULT_CODE_NAME)))
            .andExpect(jsonPath("$.[*].codeNameEn").value(hasItem(DEFAULT_CODE_NAME_EN)));
    }

    @Test
    @Transactional
    void getAdministrativeUnits() throws Exception {
        // Initialize the database
        administrativeUnitsRepository.saveAndFlush(administrativeUnits);

        // Get the administrativeUnits
        restAdministrativeUnitsMockMvc
            .perform(get(ENTITY_API_URL_ID, administrativeUnits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(administrativeUnits.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.fullNameEn").value(DEFAULT_FULL_NAME_EN))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.shortNameEn").value(DEFAULT_SHORT_NAME_EN))
            .andExpect(jsonPath("$.codeName").value(DEFAULT_CODE_NAME))
            .andExpect(jsonPath("$.codeNameEn").value(DEFAULT_CODE_NAME_EN));
    }

    @Test
    @Transactional
    void getNonExistingAdministrativeUnits() throws Exception {
        // Get the administrativeUnits
        restAdministrativeUnitsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdministrativeUnits() throws Exception {
        // Initialize the database
        administrativeUnitsRepository.saveAndFlush(administrativeUnits);

        int databaseSizeBeforeUpdate = administrativeUnitsRepository.findAll().size();

        // Update the administrativeUnits
        AdministrativeUnits updatedAdministrativeUnits = administrativeUnitsRepository.findById(administrativeUnits.getId()).get();
        // Disconnect from session so that the updates on updatedAdministrativeUnits are not directly saved in db
        em.detach(updatedAdministrativeUnits);
        updatedAdministrativeUnits
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .shortName(UPDATED_SHORT_NAME)
            .shortNameEn(UPDATED_SHORT_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .codeNameEn(UPDATED_CODE_NAME_EN);
        AdministrativeUnitsDTO administrativeUnitsDTO = administrativeUnitsMapper.toDto(updatedAdministrativeUnits);

        restAdministrativeUnitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, administrativeUnitsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeUnitsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdministrativeUnits in the database
        List<AdministrativeUnits> administrativeUnitsList = administrativeUnitsRepository.findAll();
        assertThat(administrativeUnitsList).hasSize(databaseSizeBeforeUpdate);
        AdministrativeUnits testAdministrativeUnits = administrativeUnitsList.get(administrativeUnitsList.size() - 1);
        assertThat(testAdministrativeUnits.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testAdministrativeUnits.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testAdministrativeUnits.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testAdministrativeUnits.getShortNameEn()).isEqualTo(UPDATED_SHORT_NAME_EN);
        assertThat(testAdministrativeUnits.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testAdministrativeUnits.getCodeNameEn()).isEqualTo(UPDATED_CODE_NAME_EN);
    }

    @Test
    @Transactional
    void putNonExistingAdministrativeUnits() throws Exception {
        int databaseSizeBeforeUpdate = administrativeUnitsRepository.findAll().size();
        administrativeUnits.setId(count.incrementAndGet());

        // Create the AdministrativeUnits
        AdministrativeUnitsDTO administrativeUnitsDTO = administrativeUnitsMapper.toDto(administrativeUnits);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministrativeUnitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, administrativeUnitsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeUnitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeUnits in the database
        List<AdministrativeUnits> administrativeUnitsList = administrativeUnitsRepository.findAll();
        assertThat(administrativeUnitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdministrativeUnits() throws Exception {
        int databaseSizeBeforeUpdate = administrativeUnitsRepository.findAll().size();
        administrativeUnits.setId(count.incrementAndGet());

        // Create the AdministrativeUnits
        AdministrativeUnitsDTO administrativeUnitsDTO = administrativeUnitsMapper.toDto(administrativeUnits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrativeUnitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeUnitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeUnits in the database
        List<AdministrativeUnits> administrativeUnitsList = administrativeUnitsRepository.findAll();
        assertThat(administrativeUnitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdministrativeUnits() throws Exception {
        int databaseSizeBeforeUpdate = administrativeUnitsRepository.findAll().size();
        administrativeUnits.setId(count.incrementAndGet());

        // Create the AdministrativeUnits
        AdministrativeUnitsDTO administrativeUnitsDTO = administrativeUnitsMapper.toDto(administrativeUnits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrativeUnitsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeUnitsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdministrativeUnits in the database
        List<AdministrativeUnits> administrativeUnitsList = administrativeUnitsRepository.findAll();
        assertThat(administrativeUnitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdministrativeUnitsWithPatch() throws Exception {
        // Initialize the database
        administrativeUnitsRepository.saveAndFlush(administrativeUnits);

        int databaseSizeBeforeUpdate = administrativeUnitsRepository.findAll().size();

        // Update the administrativeUnits using partial update
        AdministrativeUnits partialUpdatedAdministrativeUnits = new AdministrativeUnits();
        partialUpdatedAdministrativeUnits.setId(administrativeUnits.getId());

        partialUpdatedAdministrativeUnits
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .shortName(UPDATED_SHORT_NAME)
            .codeName(UPDATED_CODE_NAME)
            .codeNameEn(UPDATED_CODE_NAME_EN);

        restAdministrativeUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdministrativeUnits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdministrativeUnits))
            )
            .andExpect(status().isOk());

        // Validate the AdministrativeUnits in the database
        List<AdministrativeUnits> administrativeUnitsList = administrativeUnitsRepository.findAll();
        assertThat(administrativeUnitsList).hasSize(databaseSizeBeforeUpdate);
        AdministrativeUnits testAdministrativeUnits = administrativeUnitsList.get(administrativeUnitsList.size() - 1);
        assertThat(testAdministrativeUnits.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testAdministrativeUnits.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testAdministrativeUnits.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testAdministrativeUnits.getShortNameEn()).isEqualTo(DEFAULT_SHORT_NAME_EN);
        assertThat(testAdministrativeUnits.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testAdministrativeUnits.getCodeNameEn()).isEqualTo(UPDATED_CODE_NAME_EN);
    }

    @Test
    @Transactional
    void fullUpdateAdministrativeUnitsWithPatch() throws Exception {
        // Initialize the database
        administrativeUnitsRepository.saveAndFlush(administrativeUnits);

        int databaseSizeBeforeUpdate = administrativeUnitsRepository.findAll().size();

        // Update the administrativeUnits using partial update
        AdministrativeUnits partialUpdatedAdministrativeUnits = new AdministrativeUnits();
        partialUpdatedAdministrativeUnits.setId(administrativeUnits.getId());

        partialUpdatedAdministrativeUnits
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .shortName(UPDATED_SHORT_NAME)
            .shortNameEn(UPDATED_SHORT_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .codeNameEn(UPDATED_CODE_NAME_EN);

        restAdministrativeUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdministrativeUnits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdministrativeUnits))
            )
            .andExpect(status().isOk());

        // Validate the AdministrativeUnits in the database
        List<AdministrativeUnits> administrativeUnitsList = administrativeUnitsRepository.findAll();
        assertThat(administrativeUnitsList).hasSize(databaseSizeBeforeUpdate);
        AdministrativeUnits testAdministrativeUnits = administrativeUnitsList.get(administrativeUnitsList.size() - 1);
        assertThat(testAdministrativeUnits.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testAdministrativeUnits.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testAdministrativeUnits.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testAdministrativeUnits.getShortNameEn()).isEqualTo(UPDATED_SHORT_NAME_EN);
        assertThat(testAdministrativeUnits.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testAdministrativeUnits.getCodeNameEn()).isEqualTo(UPDATED_CODE_NAME_EN);
    }

    @Test
    @Transactional
    void patchNonExistingAdministrativeUnits() throws Exception {
        int databaseSizeBeforeUpdate = administrativeUnitsRepository.findAll().size();
        administrativeUnits.setId(count.incrementAndGet());

        // Create the AdministrativeUnits
        AdministrativeUnitsDTO administrativeUnitsDTO = administrativeUnitsMapper.toDto(administrativeUnits);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministrativeUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, administrativeUnitsDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(administrativeUnitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeUnits in the database
        List<AdministrativeUnits> administrativeUnitsList = administrativeUnitsRepository.findAll();
        assertThat(administrativeUnitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdministrativeUnits() throws Exception {
        int databaseSizeBeforeUpdate = administrativeUnitsRepository.findAll().size();
        administrativeUnits.setId(count.incrementAndGet());

        // Create the AdministrativeUnits
        AdministrativeUnitsDTO administrativeUnitsDTO = administrativeUnitsMapper.toDto(administrativeUnits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrativeUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(administrativeUnitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeUnits in the database
        List<AdministrativeUnits> administrativeUnitsList = administrativeUnitsRepository.findAll();
        assertThat(administrativeUnitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdministrativeUnits() throws Exception {
        int databaseSizeBeforeUpdate = administrativeUnitsRepository.findAll().size();
        administrativeUnits.setId(count.incrementAndGet());

        // Create the AdministrativeUnits
        AdministrativeUnitsDTO administrativeUnitsDTO = administrativeUnitsMapper.toDto(administrativeUnits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrativeUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(administrativeUnitsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdministrativeUnits in the database
        List<AdministrativeUnits> administrativeUnitsList = administrativeUnitsRepository.findAll();
        assertThat(administrativeUnitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdministrativeUnits() throws Exception {
        // Initialize the database
        administrativeUnitsRepository.saveAndFlush(administrativeUnits);

        int databaseSizeBeforeDelete = administrativeUnitsRepository.findAll().size();

        // Delete the administrativeUnits
        restAdministrativeUnitsMockMvc
            .perform(delete(ENTITY_API_URL_ID, administrativeUnits.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdministrativeUnits> administrativeUnitsList = administrativeUnitsRepository.findAll();
        assertThat(administrativeUnitsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
