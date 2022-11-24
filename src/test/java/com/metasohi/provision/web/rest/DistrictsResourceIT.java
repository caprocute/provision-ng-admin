package com.metasohi.provision.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.metasohi.provision.IntegrationTest;
import com.metasohi.provision.domain.Districts;
import com.metasohi.provision.repository.DistrictsRepository;
import com.metasohi.provision.service.dto.DistrictsDTO;
import com.metasohi.provision.service.mapper.DistrictsMapper;
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
 * Integration tests for the {@link DistrictsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DistrictsResourceIT {

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

    private static final String DEFAULT_PROVINCE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ADMINISTRATIVE_UNIT_ID = 1L;
    private static final Long UPDATED_ADMINISTRATIVE_UNIT_ID = 2L;

    private static final String ENTITY_API_URL = "/api/districts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{code}";

    @Autowired
    private DistrictsRepository districtsRepository;

    @Autowired
    private DistrictsMapper districtsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictsMockMvc;

    private Districts districts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Districts createEntity(EntityManager em) {
        Districts districts = new Districts()
            .name(DEFAULT_NAME)
            .nameEn(DEFAULT_NAME_EN)
            .fullName(DEFAULT_FULL_NAME)
            .fullNameEn(DEFAULT_FULL_NAME_EN)
            .codeName(DEFAULT_CODE_NAME)
            .provinceCode(DEFAULT_PROVINCE_CODE)
            .administrativeUnitId(DEFAULT_ADMINISTRATIVE_UNIT_ID);
        return districts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Districts createUpdatedEntity(EntityManager em) {
        Districts districts = new Districts()
            .name(UPDATED_NAME)
            .nameEn(UPDATED_NAME_EN)
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .provinceCode(UPDATED_PROVINCE_CODE)
            .administrativeUnitId(UPDATED_ADMINISTRATIVE_UNIT_ID);
        return districts;
    }

    @BeforeEach
    public void initTest() {
        districts = createEntity(em);
    }

    @Test
    @Transactional
    void createDistricts() throws Exception {
        int databaseSizeBeforeCreate = districtsRepository.findAll().size();
        // Create the Districts
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);
        restDistrictsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeCreate + 1);
        Districts testDistricts = districtsList.get(districtsList.size() - 1);
        assertThat(testDistricts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDistricts.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testDistricts.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testDistricts.getFullNameEn()).isEqualTo(DEFAULT_FULL_NAME_EN);
        assertThat(testDistricts.getCodeName()).isEqualTo(DEFAULT_CODE_NAME);
        assertThat(testDistricts.getProvinceCode()).isEqualTo(DEFAULT_PROVINCE_CODE);
        assertThat(testDistricts.getAdministrativeUnitId()).isEqualTo(DEFAULT_ADMINISTRATIVE_UNIT_ID);
    }

    @Test
    @Transactional
    void createDistrictsWithExistingId() throws Exception {
        // Create the Districts with an existing ID
        districts.setCode("existing_id");
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);

        int databaseSizeBeforeCreate = districtsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDistricts() throws Exception {
        // Initialize the database
        districts.setCode(UUID.randomUUID().toString());
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList
        restDistrictsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=code,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].code").value(hasItem(districts.getCode())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].fullNameEn").value(hasItem(DEFAULT_FULL_NAME_EN)))
            .andExpect(jsonPath("$.[*].codeName").value(hasItem(DEFAULT_CODE_NAME)))
            .andExpect(jsonPath("$.[*].provinceCode").value(hasItem(DEFAULT_PROVINCE_CODE)))
            .andExpect(jsonPath("$.[*].administrativeUnitId").value(hasItem(DEFAULT_ADMINISTRATIVE_UNIT_ID.intValue())));
    }

    @Test
    @Transactional
    void getDistricts() throws Exception {
        // Initialize the database
        districts.setCode(UUID.randomUUID().toString());
        districtsRepository.saveAndFlush(districts);

        // Get the districts
        restDistrictsMockMvc
            .perform(get(ENTITY_API_URL_ID, districts.getCode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.code").value(districts.getCode()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.fullNameEn").value(DEFAULT_FULL_NAME_EN))
            .andExpect(jsonPath("$.codeName").value(DEFAULT_CODE_NAME))
            .andExpect(jsonPath("$.provinceCode").value(DEFAULT_PROVINCE_CODE))
            .andExpect(jsonPath("$.administrativeUnitId").value(DEFAULT_ADMINISTRATIVE_UNIT_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDistricts() throws Exception {
        // Get the districts
        restDistrictsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDistricts() throws Exception {
        // Initialize the database
        districts.setCode(UUID.randomUUID().toString());
        districtsRepository.saveAndFlush(districts);

        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();

        // Update the districts
        Districts updatedDistricts = districtsRepository.findById(districts.getCode()).get();
        // Disconnect from session so that the updates on updatedDistricts are not directly saved in db
        em.detach(updatedDistricts);
        updatedDistricts
            .name(UPDATED_NAME)
            .nameEn(UPDATED_NAME_EN)
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .provinceCode(UPDATED_PROVINCE_CODE)
            .administrativeUnitId(UPDATED_ADMINISTRATIVE_UNIT_ID);
        DistrictsDTO districtsDTO = districtsMapper.toDto(updatedDistricts);

        restDistrictsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtsDTO.getCode())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
        Districts testDistricts = districtsList.get(districtsList.size() - 1);
        assertThat(testDistricts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistricts.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testDistricts.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testDistricts.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testDistricts.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testDistricts.getProvinceCode()).isEqualTo(UPDATED_PROVINCE_CODE);
        assertThat(testDistricts.getAdministrativeUnitId()).isEqualTo(UPDATED_ADMINISTRATIVE_UNIT_ID);
    }

    @Test
    @Transactional
    void putNonExistingDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();
        districts.setCode(UUID.randomUUID().toString());

        // Create the Districts
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtsDTO.getCode())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();
        districts.setCode(UUID.randomUUID().toString());

        // Create the Districts
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();
        districts.setCode(UUID.randomUUID().toString());

        // Create the Districts
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDistrictsWithPatch() throws Exception {
        // Initialize the database
        districts.setCode(UUID.randomUUID().toString());
        districtsRepository.saveAndFlush(districts);

        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();

        // Update the districts using partial update
        Districts partialUpdatedDistricts = new Districts();
        partialUpdatedDistricts.setCode(districts.getCode());

        partialUpdatedDistricts.nameEn(UPDATED_NAME_EN).administrativeUnitId(UPDATED_ADMINISTRATIVE_UNIT_ID);

        restDistrictsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistricts.getCode())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistricts))
            )
            .andExpect(status().isOk());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
        Districts testDistricts = districtsList.get(districtsList.size() - 1);
        assertThat(testDistricts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDistricts.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testDistricts.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testDistricts.getFullNameEn()).isEqualTo(DEFAULT_FULL_NAME_EN);
        assertThat(testDistricts.getCodeName()).isEqualTo(DEFAULT_CODE_NAME);
        assertThat(testDistricts.getProvinceCode()).isEqualTo(DEFAULT_PROVINCE_CODE);
        assertThat(testDistricts.getAdministrativeUnitId()).isEqualTo(UPDATED_ADMINISTRATIVE_UNIT_ID);
    }

    @Test
    @Transactional
    void fullUpdateDistrictsWithPatch() throws Exception {
        // Initialize the database
        districts.setCode(UUID.randomUUID().toString());
        districtsRepository.saveAndFlush(districts);

        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();

        // Update the districts using partial update
        Districts partialUpdatedDistricts = new Districts();
        partialUpdatedDistricts.setCode(districts.getCode());

        partialUpdatedDistricts
            .name(UPDATED_NAME)
            .nameEn(UPDATED_NAME_EN)
            .fullName(UPDATED_FULL_NAME)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .codeName(UPDATED_CODE_NAME)
            .provinceCode(UPDATED_PROVINCE_CODE)
            .administrativeUnitId(UPDATED_ADMINISTRATIVE_UNIT_ID);

        restDistrictsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistricts.getCode())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistricts))
            )
            .andExpect(status().isOk());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
        Districts testDistricts = districtsList.get(districtsList.size() - 1);
        assertThat(testDistricts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistricts.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testDistricts.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testDistricts.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testDistricts.getCodeName()).isEqualTo(UPDATED_CODE_NAME);
        assertThat(testDistricts.getProvinceCode()).isEqualTo(UPDATED_PROVINCE_CODE);
        assertThat(testDistricts.getAdministrativeUnitId()).isEqualTo(UPDATED_ADMINISTRATIVE_UNIT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();
        districts.setCode(UUID.randomUUID().toString());

        // Create the Districts
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, districtsDTO.getCode())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();
        districts.setCode(UUID.randomUUID().toString());

        // Create the Districts
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();
        districts.setCode(UUID.randomUUID().toString());

        // Create the Districts
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDistricts() throws Exception {
        // Initialize the database
        districts.setCode(UUID.randomUUID().toString());
        districtsRepository.saveAndFlush(districts);

        int databaseSizeBeforeDelete = districtsRepository.findAll().size();

        // Delete the districts
        restDistrictsMockMvc
            .perform(delete(ENTITY_API_URL_ID, districts.getCode()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
