package com.metasohi.provision.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.metasohi.provision.IntegrationTest;
import com.metasohi.provision.domain.SysArea;
import com.metasohi.provision.repository.SysAreaRepository;
import com.metasohi.provision.service.dto.SysAreaDTO;
import com.metasohi.provision.service.mapper.SysAreaMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link SysAreaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SysAreaResourceIT {

    private static final String DEFAULT_AREA_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AREA_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AREA_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_WARD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_WARD_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Float DEFAULT_LOCATION_LAT = 1F;
    private static final Float UPDATED_LOCATION_LAT = 2F;

    private static final Float DEFAULT_LOCATION_LAN = 1F;
    private static final Float UPDATED_LOCATION_LAN = 2F;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/sys-areas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SysAreaRepository sysAreaRepository;

    @Autowired
    private SysAreaMapper sysAreaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSysAreaMockMvc;

    private SysArea sysArea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysArea createEntity(EntityManager em) {
        SysArea sysArea = new SysArea()
            .areaCode(DEFAULT_AREA_CODE)
            .areaName(DEFAULT_AREA_NAME)
            .provinceCode(DEFAULT_PROVINCE_CODE)
            .districtCode(DEFAULT_DISTRICT_CODE)
            .wardCode(DEFAULT_WARD_CODE)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE)
            .status(DEFAULT_STATUS)
            .locationLat(DEFAULT_LOCATION_LAT)
            .locationLan(DEFAULT_LOCATION_LAN)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return sysArea;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysArea createUpdatedEntity(EntityManager em) {
        SysArea sysArea = new SysArea()
            .areaCode(UPDATED_AREA_CODE)
            .areaName(UPDATED_AREA_NAME)
            .provinceCode(UPDATED_PROVINCE_CODE)
            .districtCode(UPDATED_DISTRICT_CODE)
            .wardCode(UPDATED_WARD_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .status(UPDATED_STATUS)
            .locationLat(UPDATED_LOCATION_LAT)
            .locationLan(UPDATED_LOCATION_LAN)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return sysArea;
    }

    @BeforeEach
    public void initTest() {
        sysArea = createEntity(em);
    }

    @Test
    @Transactional
    void createSysArea() throws Exception {
        int databaseSizeBeforeCreate = sysAreaRepository.findAll().size();
        // Create the SysArea
        SysAreaDTO sysAreaDTO = sysAreaMapper.toDto(sysArea);
        restSysAreaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysAreaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SysArea in the database
        List<SysArea> sysAreaList = sysAreaRepository.findAll();
        assertThat(sysAreaList).hasSize(databaseSizeBeforeCreate + 1);
        SysArea testSysArea = sysAreaList.get(sysAreaList.size() - 1);
        assertThat(testSysArea.getAreaCode()).isEqualTo(DEFAULT_AREA_CODE);
        assertThat(testSysArea.getAreaName()).isEqualTo(DEFAULT_AREA_NAME);
        assertThat(testSysArea.getProvinceCode()).isEqualTo(DEFAULT_PROVINCE_CODE);
        assertThat(testSysArea.getDistrictCode()).isEqualTo(DEFAULT_DISTRICT_CODE);
        assertThat(testSysArea.getWardCode()).isEqualTo(DEFAULT_WARD_CODE);
        assertThat(testSysArea.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSysArea.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSysArea.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSysArea.getLocationLat()).isEqualTo(DEFAULT_LOCATION_LAT);
        assertThat(testSysArea.getLocationLan()).isEqualTo(DEFAULT_LOCATION_LAN);
        assertThat(testSysArea.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSysArea.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSysArea.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSysArea.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSysAreaWithExistingId() throws Exception {
        // Create the SysArea with an existing ID
        sysArea.setId(1L);
        SysAreaDTO sysAreaDTO = sysAreaMapper.toDto(sysArea);

        int databaseSizeBeforeCreate = sysAreaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysAreaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysAreaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysArea in the database
        List<SysArea> sysAreaList = sysAreaRepository.findAll();
        assertThat(sysAreaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSysAreas() throws Exception {
        // Initialize the database
        sysAreaRepository.saveAndFlush(sysArea);

        // Get all the sysAreaList
        restSysAreaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].areaCode").value(hasItem(DEFAULT_AREA_CODE)))
            .andExpect(jsonPath("$.[*].areaName").value(hasItem(DEFAULT_AREA_NAME)))
            .andExpect(jsonPath("$.[*].provinceCode").value(hasItem(DEFAULT_PROVINCE_CODE)))
            .andExpect(jsonPath("$.[*].districtCode").value(hasItem(DEFAULT_DISTRICT_CODE)))
            .andExpect(jsonPath("$.[*].wardCode").value(hasItem(DEFAULT_WARD_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].locationLat").value(hasItem(DEFAULT_LOCATION_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].locationLan").value(hasItem(DEFAULT_LOCATION_LAN.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getSysArea() throws Exception {
        // Initialize the database
        sysAreaRepository.saveAndFlush(sysArea);

        // Get the sysArea
        restSysAreaMockMvc
            .perform(get(ENTITY_API_URL_ID, sysArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sysArea.getId().intValue()))
            .andExpect(jsonPath("$.areaCode").value(DEFAULT_AREA_CODE))
            .andExpect(jsonPath("$.areaName").value(DEFAULT_AREA_NAME))
            .andExpect(jsonPath("$.provinceCode").value(DEFAULT_PROVINCE_CODE))
            .andExpect(jsonPath("$.districtCode").value(DEFAULT_DISTRICT_CODE))
            .andExpect(jsonPath("$.wardCode").value(DEFAULT_WARD_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.locationLat").value(DEFAULT_LOCATION_LAT.doubleValue()))
            .andExpect(jsonPath("$.locationLan").value(DEFAULT_LOCATION_LAN.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSysArea() throws Exception {
        // Get the sysArea
        restSysAreaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSysArea() throws Exception {
        // Initialize the database
        sysAreaRepository.saveAndFlush(sysArea);

        int databaseSizeBeforeUpdate = sysAreaRepository.findAll().size();

        // Update the sysArea
        SysArea updatedSysArea = sysAreaRepository.findById(sysArea.getId()).get();
        // Disconnect from session so that the updates on updatedSysArea are not directly saved in db
        em.detach(updatedSysArea);
        updatedSysArea
            .areaCode(UPDATED_AREA_CODE)
            .areaName(UPDATED_AREA_NAME)
            .provinceCode(UPDATED_PROVINCE_CODE)
            .districtCode(UPDATED_DISTRICT_CODE)
            .wardCode(UPDATED_WARD_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .status(UPDATED_STATUS)
            .locationLat(UPDATED_LOCATION_LAT)
            .locationLan(UPDATED_LOCATION_LAN)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        SysAreaDTO sysAreaDTO = sysAreaMapper.toDto(updatedSysArea);

        restSysAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sysAreaDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysAreaDTO))
            )
            .andExpect(status().isOk());

        // Validate the SysArea in the database
        List<SysArea> sysAreaList = sysAreaRepository.findAll();
        assertThat(sysAreaList).hasSize(databaseSizeBeforeUpdate);
        SysArea testSysArea = sysAreaList.get(sysAreaList.size() - 1);
        assertThat(testSysArea.getAreaCode()).isEqualTo(UPDATED_AREA_CODE);
        assertThat(testSysArea.getAreaName()).isEqualTo(UPDATED_AREA_NAME);
        assertThat(testSysArea.getProvinceCode()).isEqualTo(UPDATED_PROVINCE_CODE);
        assertThat(testSysArea.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
        assertThat(testSysArea.getWardCode()).isEqualTo(UPDATED_WARD_CODE);
        assertThat(testSysArea.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSysArea.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSysArea.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSysArea.getLocationLat()).isEqualTo(UPDATED_LOCATION_LAT);
        assertThat(testSysArea.getLocationLan()).isEqualTo(UPDATED_LOCATION_LAN);
        assertThat(testSysArea.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSysArea.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSysArea.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysArea.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSysArea() throws Exception {
        int databaseSizeBeforeUpdate = sysAreaRepository.findAll().size();
        sysArea.setId(count.incrementAndGet());

        // Create the SysArea
        SysAreaDTO sysAreaDTO = sysAreaMapper.toDto(sysArea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sysAreaDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysAreaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysArea in the database
        List<SysArea> sysAreaList = sysAreaRepository.findAll();
        assertThat(sysAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSysArea() throws Exception {
        int databaseSizeBeforeUpdate = sysAreaRepository.findAll().size();
        sysArea.setId(count.incrementAndGet());

        // Create the SysArea
        SysAreaDTO sysAreaDTO = sysAreaMapper.toDto(sysArea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysAreaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysArea in the database
        List<SysArea> sysAreaList = sysAreaRepository.findAll();
        assertThat(sysAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSysArea() throws Exception {
        int databaseSizeBeforeUpdate = sysAreaRepository.findAll().size();
        sysArea.setId(count.incrementAndGet());

        // Create the SysArea
        SysAreaDTO sysAreaDTO = sysAreaMapper.toDto(sysArea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysAreaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysAreaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SysArea in the database
        List<SysArea> sysAreaList = sysAreaRepository.findAll();
        assertThat(sysAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSysAreaWithPatch() throws Exception {
        // Initialize the database
        sysAreaRepository.saveAndFlush(sysArea);

        int databaseSizeBeforeUpdate = sysAreaRepository.findAll().size();

        // Update the sysArea using partial update
        SysArea partialUpdatedSysArea = new SysArea();
        partialUpdatedSysArea.setId(sysArea.getId());

        partialUpdatedSysArea
            .areaCode(UPDATED_AREA_CODE)
            .provinceCode(UPDATED_PROVINCE_CODE)
            .description(UPDATED_DESCRIPTION)
            .locationLan(UPDATED_LOCATION_LAN)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restSysAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSysArea.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSysArea))
            )
            .andExpect(status().isOk());

        // Validate the SysArea in the database
        List<SysArea> sysAreaList = sysAreaRepository.findAll();
        assertThat(sysAreaList).hasSize(databaseSizeBeforeUpdate);
        SysArea testSysArea = sysAreaList.get(sysAreaList.size() - 1);
        assertThat(testSysArea.getAreaCode()).isEqualTo(UPDATED_AREA_CODE);
        assertThat(testSysArea.getAreaName()).isEqualTo(DEFAULT_AREA_NAME);
        assertThat(testSysArea.getProvinceCode()).isEqualTo(UPDATED_PROVINCE_CODE);
        assertThat(testSysArea.getDistrictCode()).isEqualTo(DEFAULT_DISTRICT_CODE);
        assertThat(testSysArea.getWardCode()).isEqualTo(DEFAULT_WARD_CODE);
        assertThat(testSysArea.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSysArea.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSysArea.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSysArea.getLocationLat()).isEqualTo(DEFAULT_LOCATION_LAT);
        assertThat(testSysArea.getLocationLan()).isEqualTo(UPDATED_LOCATION_LAN);
        assertThat(testSysArea.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSysArea.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSysArea.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysArea.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSysAreaWithPatch() throws Exception {
        // Initialize the database
        sysAreaRepository.saveAndFlush(sysArea);

        int databaseSizeBeforeUpdate = sysAreaRepository.findAll().size();

        // Update the sysArea using partial update
        SysArea partialUpdatedSysArea = new SysArea();
        partialUpdatedSysArea.setId(sysArea.getId());

        partialUpdatedSysArea
            .areaCode(UPDATED_AREA_CODE)
            .areaName(UPDATED_AREA_NAME)
            .provinceCode(UPDATED_PROVINCE_CODE)
            .districtCode(UPDATED_DISTRICT_CODE)
            .wardCode(UPDATED_WARD_CODE)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .status(UPDATED_STATUS)
            .locationLat(UPDATED_LOCATION_LAT)
            .locationLan(UPDATED_LOCATION_LAN)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSysAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSysArea.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSysArea))
            )
            .andExpect(status().isOk());

        // Validate the SysArea in the database
        List<SysArea> sysAreaList = sysAreaRepository.findAll();
        assertThat(sysAreaList).hasSize(databaseSizeBeforeUpdate);
        SysArea testSysArea = sysAreaList.get(sysAreaList.size() - 1);
        assertThat(testSysArea.getAreaCode()).isEqualTo(UPDATED_AREA_CODE);
        assertThat(testSysArea.getAreaName()).isEqualTo(UPDATED_AREA_NAME);
        assertThat(testSysArea.getProvinceCode()).isEqualTo(UPDATED_PROVINCE_CODE);
        assertThat(testSysArea.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
        assertThat(testSysArea.getWardCode()).isEqualTo(UPDATED_WARD_CODE);
        assertThat(testSysArea.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSysArea.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSysArea.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSysArea.getLocationLat()).isEqualTo(UPDATED_LOCATION_LAT);
        assertThat(testSysArea.getLocationLan()).isEqualTo(UPDATED_LOCATION_LAN);
        assertThat(testSysArea.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSysArea.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSysArea.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysArea.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSysArea() throws Exception {
        int databaseSizeBeforeUpdate = sysAreaRepository.findAll().size();
        sysArea.setId(count.incrementAndGet());

        // Create the SysArea
        SysAreaDTO sysAreaDTO = sysAreaMapper.toDto(sysArea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sysAreaDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sysAreaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysArea in the database
        List<SysArea> sysAreaList = sysAreaRepository.findAll();
        assertThat(sysAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSysArea() throws Exception {
        int databaseSizeBeforeUpdate = sysAreaRepository.findAll().size();
        sysArea.setId(count.incrementAndGet());

        // Create the SysArea
        SysAreaDTO sysAreaDTO = sysAreaMapper.toDto(sysArea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sysAreaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysArea in the database
        List<SysArea> sysAreaList = sysAreaRepository.findAll();
        assertThat(sysAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSysArea() throws Exception {
        int databaseSizeBeforeUpdate = sysAreaRepository.findAll().size();
        sysArea.setId(count.incrementAndGet());

        // Create the SysArea
        SysAreaDTO sysAreaDTO = sysAreaMapper.toDto(sysArea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysAreaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sysAreaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SysArea in the database
        List<SysArea> sysAreaList = sysAreaRepository.findAll();
        assertThat(sysAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSysArea() throws Exception {
        // Initialize the database
        sysAreaRepository.saveAndFlush(sysArea);

        int databaseSizeBeforeDelete = sysAreaRepository.findAll().size();

        // Delete the sysArea
        restSysAreaMockMvc
            .perform(delete(ENTITY_API_URL_ID, sysArea.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysArea> sysAreaList = sysAreaRepository.findAll();
        assertThat(sysAreaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
