package com.metasohi.provision.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.metasohi.provision.IntegrationTest;
import com.metasohi.provision.domain.Provinces;
import com.metasohi.provision.repository.ProvincesRepository;
import com.metasohi.provision.service.dto.ProvincesDTO;
import com.metasohi.provision.service.mapper.ProvincesMapper;
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
 * Integration tests for the {@link ProvincesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProvincesResourceIT {

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

    private static final Long DEFAULT_ADMINISTRATIVE_UNIT_ID = 1L;
    private static final Long UPDATED_ADMINISTRATIVE_UNIT_ID = 2L;

    private static final Long DEFAULT_ADMINISTRATIVE_REGION_ID = 1L;
    private static final Long UPDATED_ADMINISTRATIVE_REGION_ID = 2L;

    private static final String ENTITY_API_URL = "/api/provinces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{code}";

    @Autowired
    private ProvincesRepository provincesRepository;

    @Autowired
    private ProvincesMapper provincesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProvincesMockMvc;

    private Provinces provinces;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Provinces createEntity(EntityManager em) {
        Provinces provinces = new Provinces()
            .name(DEFAULT_NAME)
            .nameEn(DEFAULT_NAME_EN)
            .fullName(DEFAULT_FULL_NAME)
            .fullNameEn(DEFAULT_FULL_NAME_EN)
            .codeName(DEFAULT_CODE_NAME)
            .administrativeUnitId(DEFAULT_ADMINISTRATIVE_UNIT_ID)
            .administrativeRegionId(DEFAULT_ADMINISTRATIVE_REGION_ID);
        return provinces;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Provinces createUpdatedEntity(EntityManager em) {
        Provinces provinces = new Provinces()
            .name(UPDATED_NAME)
            .nameEn(UPDATED_NAME_EN)
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .administrativeUnitId(UPDATED_ADMINISTRATIVE_UNIT_ID)
            .administrativeRegionId(UPDATED_ADMINISTRATIVE_REGION_ID);
        return provinces;
    }

    @BeforeEach
    public void initTest() {
        provinces = createEntity(em);
    }

    @Test
    @Transactional
    void createProvinces() throws Exception {
        int databaseSizeBeforeCreate = provincesRepository.findAll().size();
        // Create the Provinces
        ProvincesDTO provincesDTO = provincesMapper.toDto(provinces);
        restProvincesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provincesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Provinces in the database
        List<Provinces> provincesList = provincesRepository.findAll();
        assertThat(provincesList).hasSize(databaseSizeBeforeCreate + 1);
        Provinces testProvinces = provincesList.get(provincesList.size() - 1);
        assertThat(testProvinces.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProvinces.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testProvinces.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testProvinces.getFullNameEn()).isEqualTo(DEFAULT_FULL_NAME_EN);
        assertThat(testProvinces.getCodeName()).isEqualTo(DEFAULT_CODE_NAME);
        assertThat(testProvinces.getAdministrativeUnitId()).isEqualTo(DEFAULT_ADMINISTRATIVE_UNIT_ID);
        assertThat(testProvinces.getAdministrativeRegionId()).isEqualTo(DEFAULT_ADMINISTRATIVE_REGION_ID);
    }

    @Test
    @Transactional
    void createProvincesWithExistingId() throws Exception {
        // Create the Provinces with an existing ID
        provinces.setCode("existing_id");
        ProvincesDTO provincesDTO = provincesMapper.toDto(provinces);

        int databaseSizeBeforeCreate = provincesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProvincesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provincesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provinces in the database
        List<Provinces> provincesList = provincesRepository.findAll();
        assertThat(provincesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProvinces() throws Exception {
        // Initialize the database
        provinces.setCode(UUID.randomUUID().toString());
        provincesRepository.saveAndFlush(provinces);

        // Get all the provincesList
        restProvincesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=code,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].code").value(hasItem(provinces.getCode())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].fullNameEn").value(hasItem(DEFAULT_FULL_NAME_EN)))
            .andExpect(jsonPath("$.[*].codeName").value(hasItem(DEFAULT_CODE_NAME)))
            .andExpect(jsonPath("$.[*].administrativeUnitId").value(hasItem(DEFAULT_ADMINISTRATIVE_UNIT_ID.intValue())))
            .andExpect(jsonPath("$.[*].administrativeRegionId").value(hasItem(DEFAULT_ADMINISTRATIVE_REGION_ID.intValue())));
    }

    @Test
    @Transactional
    void getProvinces() throws Exception {
        // Initialize the database
        provinces.setCode(UUID.randomUUID().toString());
        provincesRepository.saveAndFlush(provinces);

        // Get the provinces
        restProvincesMockMvc
            .perform(get(ENTITY_API_URL_ID, provinces.getCode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.code").value(provinces.getCode()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.fullNameEn").value(DEFAULT_FULL_NAME_EN))
            .andExpect(jsonPath("$.codeName").value(DEFAULT_CODE_NAME))
            .andExpect(jsonPath("$.administrativeUnitId").value(DEFAULT_ADMINISTRATIVE_UNIT_ID.intValue()))
            .andExpect(jsonPath("$.administrativeRegionId").value(DEFAULT_ADMINISTRATIVE_REGION_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingProvinces() throws Exception {
        // Get the provinces
        restProvincesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProvinces() throws Exception {
        // Initialize the database
        provinces.setCode(UUID.randomUUID().toString());
        provincesRepository.saveAndFlush(provinces);

        int databaseSizeBeforeUpdate = provincesRepository.findAll().size();

        // Update the provinces
        Provinces updatedProvinces = provincesRepository.findById(provinces.getCode()).get();
        // Disconnect from session so that the updates on updatedProvinces are not directly saved in db
        em.detach(updatedProvinces);
        updatedProvinces
            .name(UPDATED_NAME)
            .nameEn(UPDATED_NAME_EN)
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .administrativeUnitId(UPDATED_ADMINISTRATIVE_UNIT_ID)
            .administrativeRegionId(UPDATED_ADMINISTRATIVE_REGION_ID);
        ProvincesDTO provincesDTO = provincesMapper.toDto(updatedProvinces);

        restProvincesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, provincesDTO.getCode())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provincesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Provinces in the database
        List<Provinces> provincesList = provincesRepository.findAll();
        assertThat(provincesList).hasSize(databaseSizeBeforeUpdate);
        Provinces testProvinces = provincesList.get(provincesList.size() - 1);
        assertThat(testProvinces.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProvinces.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testProvinces.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testProvinces.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testProvinces.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testProvinces.getAdministrativeUnitId()).isEqualTo(UPDATED_ADMINISTRATIVE_UNIT_ID);
        assertThat(testProvinces.getAdministrativeRegionId()).isEqualTo(UPDATED_ADMINISTRATIVE_REGION_ID);
    }

    @Test
    @Transactional
    void putNonExistingProvinces() throws Exception {
        int databaseSizeBeforeUpdate = provincesRepository.findAll().size();
        provinces.setCode(UUID.randomUUID().toString());

        // Create the Provinces
        ProvincesDTO provincesDTO = provincesMapper.toDto(provinces);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvincesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, provincesDTO.getCode())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provincesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provinces in the database
        List<Provinces> provincesList = provincesRepository.findAll();
        assertThat(provincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProvinces() throws Exception {
        int databaseSizeBeforeUpdate = provincesRepository.findAll().size();
        provinces.setCode(UUID.randomUUID().toString());

        // Create the Provinces
        ProvincesDTO provincesDTO = provincesMapper.toDto(provinces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvincesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provincesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provinces in the database
        List<Provinces> provincesList = provincesRepository.findAll();
        assertThat(provincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProvinces() throws Exception {
        int databaseSizeBeforeUpdate = provincesRepository.findAll().size();
        provinces.setCode(UUID.randomUUID().toString());

        // Create the Provinces
        ProvincesDTO provincesDTO = provincesMapper.toDto(provinces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvincesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provincesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Provinces in the database
        List<Provinces> provincesList = provincesRepository.findAll();
        assertThat(provincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProvincesWithPatch() throws Exception {
        // Initialize the database
        provinces.setCode(UUID.randomUUID().toString());
        provincesRepository.saveAndFlush(provinces);

        int databaseSizeBeforeUpdate = provincesRepository.findAll().size();

        // Update the provinces using partial update
        Provinces partialUpdatedProvinces = new Provinces();
        partialUpdatedProvinces.setCode(provinces.getCode());

        partialUpdatedProvinces
            .name(UPDATED_NAME)
            .fullName(UPDATED_FULL_NAME)
            .codeName(UPDATED_CODE_NAME)
            .administrativeUnitId(UPDATED_ADMINISTRATIVE_UNIT_ID);

        restProvincesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvinces.getCode())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProvinces))
            )
            .andExpect(status().isOk());

        // Validate the Provinces in the database
        List<Provinces> provincesList = provincesRepository.findAll();
        assertThat(provincesList).hasSize(databaseSizeBeforeUpdate);
        Provinces testProvinces = provincesList.get(provincesList.size() - 1);
        assertThat(testProvinces.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProvinces.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testProvinces.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testProvinces.getFullNameEn()).isEqualTo(DEFAULT_FULL_NAME_EN);
        assertThat(testProvinces.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testProvinces.getAdministrativeUnitId()).isEqualTo(UPDATED_ADMINISTRATIVE_UNIT_ID);
        assertThat(testProvinces.getAdministrativeRegionId()).isEqualTo(DEFAULT_ADMINISTRATIVE_REGION_ID);
    }

    @Test
    @Transactional
    void fullUpdateProvincesWithPatch() throws Exception {
        // Initialize the database
        provinces.setCode(UUID.randomUUID().toString());
        provincesRepository.saveAndFlush(provinces);

        int databaseSizeBeforeUpdate = provincesRepository.findAll().size();

        // Update the provinces using partial update
        Provinces partialUpdatedProvinces = new Provinces();
        partialUpdatedProvinces.setCode(provinces.getCode());

        partialUpdatedProvinces
            .name(UPDATED_NAME)
            .nameEn(UPDATED_NAME_EN)
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .administrativeUnitId(UPDATED_ADMINISTRATIVE_UNIT_ID)
            .administrativeRegionId(UPDATED_ADMINISTRATIVE_REGION_ID);

        restProvincesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvinces.getCode())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProvinces))
            )
            .andExpect(status().isOk());

        // Validate the Provinces in the database
        List<Provinces> provincesList = provincesRepository.findAll();
        assertThat(provincesList).hasSize(databaseSizeBeforeUpdate);
        Provinces testProvinces = provincesList.get(provincesList.size() - 1);
        assertThat(testProvinces.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProvinces.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testProvinces.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testProvinces.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testProvinces.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testProvinces.getAdministrativeUnitId()).isEqualTo(UPDATED_ADMINISTRATIVE_UNIT_ID);
        assertThat(testProvinces.getAdministrativeRegionId()).isEqualTo(UPDATED_ADMINISTRATIVE_REGION_ID);
    }

    @Test
    @Transactional
    void patchNonExistingProvinces() throws Exception {
        int databaseSizeBeforeUpdate = provincesRepository.findAll().size();
        provinces.setCode(UUID.randomUUID().toString());

        // Create the Provinces
        ProvincesDTO provincesDTO = provincesMapper.toDto(provinces);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvincesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, provincesDTO.getCode())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(provincesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provinces in the database
        List<Provinces> provincesList = provincesRepository.findAll();
        assertThat(provincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProvinces() throws Exception {
        int databaseSizeBeforeUpdate = provincesRepository.findAll().size();
        provinces.setCode(UUID.randomUUID().toString());

        // Create the Provinces
        ProvincesDTO provincesDTO = provincesMapper.toDto(provinces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvincesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(provincesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provinces in the database
        List<Provinces> provincesList = provincesRepository.findAll();
        assertThat(provincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProvinces() throws Exception {
        int databaseSizeBeforeUpdate = provincesRepository.findAll().size();
        provinces.setCode(UUID.randomUUID().toString());

        // Create the Provinces
        ProvincesDTO provincesDTO = provincesMapper.toDto(provinces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvincesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(provincesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Provinces in the database
        List<Provinces> provincesList = provincesRepository.findAll();
        assertThat(provincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProvinces() throws Exception {
        // Initialize the database
        provinces.setCode(UUID.randomUUID().toString());
        provincesRepository.saveAndFlush(provinces);

        int databaseSizeBeforeDelete = provincesRepository.findAll().size();

        // Delete the provinces
        restProvincesMockMvc
            .perform(delete(ENTITY_API_URL_ID, provinces.getCode()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Provinces> provincesList = provincesRepository.findAll();
        assertThat(provincesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
