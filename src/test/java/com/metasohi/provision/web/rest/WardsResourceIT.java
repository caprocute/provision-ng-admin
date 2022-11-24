package com.metasohi.provision.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.metasohi.provision.IntegrationTest;
import com.metasohi.provision.domain.Wards;
import com.metasohi.provision.repository.WardsRepository;
import com.metasohi.provision.service.dto.WardsDTO;
import com.metasohi.provision.service.mapper.WardsMapper;
import java.util.List;
import java.util.UUID;
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
 * Integration tests for the {@link WardsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class WardsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CODE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ADMINISTRATIVE_UNIT_ID = 1L;
    private static final Long UPDATED_ADMINISTRATIVE_UNIT_ID = 2L;

    private static final String ENTITY_API_URL = "/api/wards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{code}";

    @Autowired
    private WardsRepository wardsRepository;

    @Autowired
    private WardsMapper wardsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWardsMockMvc;

    private Wards wards;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wards createEntity(EntityManager em) {
        Wards wards = new Wards()
            .name(DEFAULT_NAME)
            .nameEn(DEFAULT_NAME_EN)
            .fullName(DEFAULT_FULL_NAME)
            .fullNameEn(DEFAULT_FULL_NAME_EN)
            .codeName(DEFAULT_CODE_NAME)
            .districtCode(DEFAULT_DISTRICT_CODE)
            .administrativeUnitId(DEFAULT_ADMINISTRATIVE_UNIT_ID);
        return wards;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wards createUpdatedEntity(EntityManager em) {
        Wards wards = new Wards()
            .name(UPDATED_NAME)
            .nameEn(UPDATED_NAME_EN)
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .districtCode(UPDATED_DISTRICT_CODE)
            .administrativeUnitId(UPDATED_ADMINISTRATIVE_UNIT_ID);
        return wards;
    }

    @BeforeEach
    public void initTest() {
        wards = createEntity(em);
    }

    @Test
    @Transactional
    void createWards() throws Exception {
        int databaseSizeBeforeCreate = wardsRepository.findAll().size();
        // Create the Wards
        WardsDTO wardsDTO = wardsMapper.toDto(wards);
        restWardsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wardsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeCreate + 1);
        Wards testWards = wardsList.get(wardsList.size() - 1);
        assertThat(testWards.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWards.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testWards.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testWards.getFullNameEn()).isEqualTo(DEFAULT_FULL_NAME_EN);
        assertThat(testWards.getCodeName()).isEqualTo(DEFAULT_CODE_NAME);
        assertThat(testWards.getDistrictCode()).isEqualTo(DEFAULT_DISTRICT_CODE);
        assertThat(testWards.getAdministrativeUnitId()).isEqualTo(DEFAULT_ADMINISTRATIVE_UNIT_ID);
    }

    @Test
    @Transactional
    void createWardsWithExistingId() throws Exception {
        // Create the Wards with an existing ID
        wards.setCode("existing_id");
        WardsDTO wardsDTO = wardsMapper.toDto(wards);

        int databaseSizeBeforeCreate = wardsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWardsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wardsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWards() throws Exception {
        // Initialize the database
        wards.setCode(UUID.randomUUID().toString());
        wardsRepository.saveAndFlush(wards);

        // Get all the wardsList
        restWardsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=code,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].code").value(hasItem(wards.getCode())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].fullNameEn").value(hasItem(DEFAULT_FULL_NAME_EN)))
            .andExpect(jsonPath("$.[*].codeName").value(hasItem(DEFAULT_CODE_NAME)))
            .andExpect(jsonPath("$.[*].districtCode").value(hasItem(DEFAULT_DISTRICT_CODE)))
            .andExpect(jsonPath("$.[*].administrativeUnitId").value(hasItem(DEFAULT_ADMINISTRATIVE_UNIT_ID.intValue())));
    }

    @Test
    @Transactional
    void getWards() throws Exception {
        // Initialize the database
        wards.setCode(UUID.randomUUID().toString());
        wardsRepository.saveAndFlush(wards);

        // Get the wards
        restWardsMockMvc
            .perform(get(ENTITY_API_URL_ID, wards.getCode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.code").value(wards.getCode()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.fullNameEn").value(DEFAULT_FULL_NAME_EN))
            .andExpect(jsonPath("$.codeName").value(DEFAULT_CODE_NAME))
            .andExpect(jsonPath("$.districtCode").value(DEFAULT_DISTRICT_CODE))
            .andExpect(jsonPath("$.administrativeUnitId").value(DEFAULT_ADMINISTRATIVE_UNIT_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingWards() throws Exception {
        // Get the wards
        restWardsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWards() throws Exception {
        // Initialize the database
        wards.setCode(UUID.randomUUID().toString());
        wardsRepository.saveAndFlush(wards);

        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();

        // Update the wards
        Wards updatedWards = wardsRepository.findById(wards.getCode()).get();
        // Disconnect from session so that the updates on updatedWards are not directly saved in db
        em.detach(updatedWards);
        updatedWards
            .name(UPDATED_NAME)
            .nameEn(UPDATED_NAME_EN)
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .districtCode(UPDATED_DISTRICT_CODE)
            .administrativeUnitId(UPDATED_ADMINISTRATIVE_UNIT_ID);
        WardsDTO wardsDTO = wardsMapper.toDto(updatedWards);

        restWardsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wardsDTO.getCode())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wardsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
        Wards testWards = wardsList.get(wardsList.size() - 1);
        assertThat(testWards.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWards.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testWards.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testWards.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testWards.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testWards.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
        assertThat(testWards.getAdministrativeUnitId()).isEqualTo(UPDATED_ADMINISTRATIVE_UNIT_ID);
    }

    @Test
    @Transactional
    void putNonExistingWards() throws Exception {
        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();
        wards.setCode(UUID.randomUUID().toString());

        // Create the Wards
        WardsDTO wardsDTO = wardsMapper.toDto(wards);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWardsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wardsDTO.getCode())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wardsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWards() throws Exception {
        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();
        wards.setCode(UUID.randomUUID().toString());

        // Create the Wards
        WardsDTO wardsDTO = wardsMapper.toDto(wards);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wardsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWards() throws Exception {
        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();
        wards.setCode(UUID.randomUUID().toString());

        // Create the Wards
        WardsDTO wardsDTO = wardsMapper.toDto(wards);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wardsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWardsWithPatch() throws Exception {
        // Initialize the database
        wards.setCode(UUID.randomUUID().toString());
        wardsRepository.saveAndFlush(wards);

        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();

        // Update the wards using partial update
        Wards partialUpdatedWards = new Wards();
        partialUpdatedWards.setCode(wards.getCode());

        partialUpdatedWards.districtCode(UPDATED_DISTRICT_CODE);

        restWardsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWards.getCode())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWards))
            )
            .andExpect(status().isOk());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
        Wards testWards = wardsList.get(wardsList.size() - 1);
        assertThat(testWards.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWards.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testWards.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testWards.getFullNameEn()).isEqualTo(DEFAULT_FULL_NAME_EN);
        assertThat(testWards.getCodeName()).isEqualTo(DEFAULT_CODE_NAME);
        assertThat(testWards.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
        assertThat(testWards.getAdministrativeUnitId()).isEqualTo(DEFAULT_ADMINISTRATIVE_UNIT_ID);
    }

    @Test
    @Transactional
    void fullUpdateWardsWithPatch() throws Exception {
        // Initialize the database
        wards.setCode(UUID.randomUUID().toString());
        wardsRepository.saveAndFlush(wards);

        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();

        // Update the wards using partial update
        Wards partialUpdatedWards = new Wards();
        partialUpdatedWards.setCode(wards.getCode());

        partialUpdatedWards
            .name(UPDATED_NAME)
            .nameEn(UPDATED_NAME_EN)
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .districtCode(UPDATED_DISTRICT_CODE)
            .administrativeUnitId(UPDATED_ADMINISTRATIVE_UNIT_ID);

        restWardsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWards.getCode())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWards))
            )
            .andExpect(status().isOk());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
        Wards testWards = wardsList.get(wardsList.size() - 1);
        assertThat(testWards.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWards.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testWards.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testWards.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testWards.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testWards.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
        assertThat(testWards.getAdministrativeUnitId()).isEqualTo(UPDATED_ADMINISTRATIVE_UNIT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingWards() throws Exception {
        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();
        wards.setCode(UUID.randomUUID().toString());

        // Create the Wards
        WardsDTO wardsDTO = wardsMapper.toDto(wards);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWardsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wardsDTO.getCode())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wardsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWards() throws Exception {
        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();
        wards.setCode(UUID.randomUUID().toString());

        // Create the Wards
        WardsDTO wardsDTO = wardsMapper.toDto(wards);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wardsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWards() throws Exception {
        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();
        wards.setCode(UUID.randomUUID().toString());

        // Create the Wards
        WardsDTO wardsDTO = wardsMapper.toDto(wards);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wardsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWards() throws Exception {
        // Initialize the database
        wards.setCode(UUID.randomUUID().toString());
        wardsRepository.saveAndFlush(wards);

        int databaseSizeBeforeDelete = wardsRepository.findAll().size();

        // Delete the wards
        restWardsMockMvc
            .perform(delete(ENTITY_API_URL_ID, wards.getCode()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
