package com.metasohi.provision.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.metasohi.provision.IntegrationTest;
import com.metasohi.provision.domain.SysCamera;
import com.metasohi.provision.repository.SysCameraRepository;
import com.metasohi.provision.service.dto.SysCameraDTO;
import com.metasohi.provision.service.mapper.SysCameraMapper;
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
 * Integration tests for the {@link SysCameraResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SysCameraResourceIT {

    private static final String DEFAULT_CAM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CAM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAM_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_AREA_ID = 1L;
    private static final Long UPDATED_AREA_ID = 2L;

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

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

    private static final String DEFAULT_URL_SFTP = "AAAAAAAAAA";
    private static final String UPDATED_URL_SFTP = "BBBBBBBBBB";

    private static final String DEFAULT_URL_LIVE_STREAM = "AAAAAAAAAA";
    private static final String UPDATED_URL_LIVE_STREAM = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/sys-cameras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SysCameraRepository sysCameraRepository;

    @Autowired
    private SysCameraMapper sysCameraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSysCameraMockMvc;

    private SysCamera sysCamera;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysCamera createEntity(EntityManager em) {
        SysCamera sysCamera = new SysCamera()
            .camCode(DEFAULT_CAM_CODE)
            .camName(DEFAULT_CAM_NAME)
            .areaId(DEFAULT_AREA_ID)
            .branch(DEFAULT_BRANCH)
            .origin(DEFAULT_ORIGIN)
            .description(DEFAULT_DESCRIPTION)
            .isActive(DEFAULT_IS_ACTIVE)
            .status(DEFAULT_STATUS)
            .locationLat(DEFAULT_LOCATION_LAT)
            .locationLan(DEFAULT_LOCATION_LAN)
            .urlSFTP(DEFAULT_URL_SFTP)
            .urlLiveStream(DEFAULT_URL_LIVE_STREAM)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return sysCamera;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysCamera createUpdatedEntity(EntityManager em) {
        SysCamera sysCamera = new SysCamera()
            .camCode(UPDATED_CAM_CODE)
            .camName(UPDATED_CAM_NAME)
            .areaId(UPDATED_AREA_ID)
            .branch(UPDATED_BRANCH)
            .origin(UPDATED_ORIGIN)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .status(UPDATED_STATUS)
            .locationLat(UPDATED_LOCATION_LAT)
            .locationLan(UPDATED_LOCATION_LAN)
            .urlSFTP(UPDATED_URL_SFTP)
            .urlLiveStream(UPDATED_URL_LIVE_STREAM)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return sysCamera;
    }

    @BeforeEach
    public void initTest() {
        sysCamera = createEntity(em);
    }

    @Test
    @Transactional
    void createSysCamera() throws Exception {
        int databaseSizeBeforeCreate = sysCameraRepository.findAll().size();
        // Create the SysCamera
        SysCameraDTO sysCameraDTO = sysCameraMapper.toDto(sysCamera);
        restSysCameraMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysCameraDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SysCamera in the database
        List<SysCamera> sysCameraList = sysCameraRepository.findAll();
        assertThat(sysCameraList).hasSize(databaseSizeBeforeCreate + 1);
        SysCamera testSysCamera = sysCameraList.get(sysCameraList.size() - 1);
        assertThat(testSysCamera.getCamCode()).isEqualTo(DEFAULT_CAM_CODE);
        assertThat(testSysCamera.getCamName()).isEqualTo(DEFAULT_CAM_NAME);
        assertThat(testSysCamera.getAreaId()).isEqualTo(DEFAULT_AREA_ID);
        assertThat(testSysCamera.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testSysCamera.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testSysCamera.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSysCamera.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSysCamera.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSysCamera.getLocationLat()).isEqualTo(DEFAULT_LOCATION_LAT);
        assertThat(testSysCamera.getLocationLan()).isEqualTo(DEFAULT_LOCATION_LAN);
        assertThat(testSysCamera.getUrlSFTP()).isEqualTo(DEFAULT_URL_SFTP);
        assertThat(testSysCamera.getUrlLiveStream()).isEqualTo(DEFAULT_URL_LIVE_STREAM);
        assertThat(testSysCamera.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSysCamera.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSysCamera.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSysCamera.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSysCameraWithExistingId() throws Exception {
        // Create the SysCamera with an existing ID
        sysCamera.setId(1L);
        SysCameraDTO sysCameraDTO = sysCameraMapper.toDto(sysCamera);

        int databaseSizeBeforeCreate = sysCameraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysCameraMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysCameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysCamera in the database
        List<SysCamera> sysCameraList = sysCameraRepository.findAll();
        assertThat(sysCameraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSysCameras() throws Exception {
        // Initialize the database
        sysCameraRepository.saveAndFlush(sysCamera);

        // Get all the sysCameraList
        restSysCameraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysCamera.getId().intValue())))
            .andExpect(jsonPath("$.[*].camCode").value(hasItem(DEFAULT_CAM_CODE)))
            .andExpect(jsonPath("$.[*].camName").value(hasItem(DEFAULT_CAM_NAME)))
            .andExpect(jsonPath("$.[*].areaId").value(hasItem(DEFAULT_AREA_ID.intValue())))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].locationLat").value(hasItem(DEFAULT_LOCATION_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].locationLan").value(hasItem(DEFAULT_LOCATION_LAN.doubleValue())))
            .andExpect(jsonPath("$.[*].urlSFTP").value(hasItem(DEFAULT_URL_SFTP)))
            .andExpect(jsonPath("$.[*].urlLiveStream").value(hasItem(DEFAULT_URL_LIVE_STREAM)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getSysCamera() throws Exception {
        // Initialize the database
        sysCameraRepository.saveAndFlush(sysCamera);

        // Get the sysCamera
        restSysCameraMockMvc
            .perform(get(ENTITY_API_URL_ID, sysCamera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sysCamera.getId().intValue()))
            .andExpect(jsonPath("$.camCode").value(DEFAULT_CAM_CODE))
            .andExpect(jsonPath("$.camName").value(DEFAULT_CAM_NAME))
            .andExpect(jsonPath("$.areaId").value(DEFAULT_AREA_ID.intValue()))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.locationLat").value(DEFAULT_LOCATION_LAT.doubleValue()))
            .andExpect(jsonPath("$.locationLan").value(DEFAULT_LOCATION_LAN.doubleValue()))
            .andExpect(jsonPath("$.urlSFTP").value(DEFAULT_URL_SFTP))
            .andExpect(jsonPath("$.urlLiveStream").value(DEFAULT_URL_LIVE_STREAM))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSysCamera() throws Exception {
        // Get the sysCamera
        restSysCameraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSysCamera() throws Exception {
        // Initialize the database
        sysCameraRepository.saveAndFlush(sysCamera);

        int databaseSizeBeforeUpdate = sysCameraRepository.findAll().size();

        // Update the sysCamera
        SysCamera updatedSysCamera = sysCameraRepository.findById(sysCamera.getId()).get();
        // Disconnect from session so that the updates on updatedSysCamera are not directly saved in db
        em.detach(updatedSysCamera);
        updatedSysCamera
            .camCode(UPDATED_CAM_CODE)
            .camName(UPDATED_CAM_NAME)
            .areaId(UPDATED_AREA_ID)
            .branch(UPDATED_BRANCH)
            .origin(UPDATED_ORIGIN)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .status(UPDATED_STATUS)
            .locationLat(UPDATED_LOCATION_LAT)
            .locationLan(UPDATED_LOCATION_LAN)
            .urlSFTP(UPDATED_URL_SFTP)
            .urlLiveStream(UPDATED_URL_LIVE_STREAM)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        SysCameraDTO sysCameraDTO = sysCameraMapper.toDto(updatedSysCamera);

        restSysCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sysCameraDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysCameraDTO))
            )
            .andExpect(status().isOk());

        // Validate the SysCamera in the database
        List<SysCamera> sysCameraList = sysCameraRepository.findAll();
        assertThat(sysCameraList).hasSize(databaseSizeBeforeUpdate);
        SysCamera testSysCamera = sysCameraList.get(sysCameraList.size() - 1);
        assertThat(testSysCamera.getCamCode()).isEqualTo(UPDATED_CAM_CODE);
        assertThat(testSysCamera.getCamName()).isEqualTo(UPDATED_CAM_NAME);
        assertThat(testSysCamera.getAreaId()).isEqualTo(UPDATED_AREA_ID);
        assertThat(testSysCamera.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testSysCamera.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testSysCamera.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSysCamera.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSysCamera.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSysCamera.getLocationLat()).isEqualTo(UPDATED_LOCATION_LAT);
        assertThat(testSysCamera.getLocationLan()).isEqualTo(UPDATED_LOCATION_LAN);
        assertThat(testSysCamera.getUrlSFTP()).isEqualTo(UPDATED_URL_SFTP);
        assertThat(testSysCamera.getUrlLiveStream()).isEqualTo(UPDATED_URL_LIVE_STREAM);
        assertThat(testSysCamera.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSysCamera.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSysCamera.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysCamera.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSysCamera() throws Exception {
        int databaseSizeBeforeUpdate = sysCameraRepository.findAll().size();
        sysCamera.setId(count.incrementAndGet());

        // Create the SysCamera
        SysCameraDTO sysCameraDTO = sysCameraMapper.toDto(sysCamera);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sysCameraDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysCameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysCamera in the database
        List<SysCamera> sysCameraList = sysCameraRepository.findAll();
        assertThat(sysCameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSysCamera() throws Exception {
        int databaseSizeBeforeUpdate = sysCameraRepository.findAll().size();
        sysCamera.setId(count.incrementAndGet());

        // Create the SysCamera
        SysCameraDTO sysCameraDTO = sysCameraMapper.toDto(sysCamera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysCameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysCamera in the database
        List<SysCamera> sysCameraList = sysCameraRepository.findAll();
        assertThat(sysCameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSysCamera() throws Exception {
        int databaseSizeBeforeUpdate = sysCameraRepository.findAll().size();
        sysCamera.setId(count.incrementAndGet());

        // Create the SysCamera
        SysCameraDTO sysCameraDTO = sysCameraMapper.toDto(sysCamera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysCameraMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysCameraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SysCamera in the database
        List<SysCamera> sysCameraList = sysCameraRepository.findAll();
        assertThat(sysCameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSysCameraWithPatch() throws Exception {
        // Initialize the database
        sysCameraRepository.saveAndFlush(sysCamera);

        int databaseSizeBeforeUpdate = sysCameraRepository.findAll().size();

        // Update the sysCamera using partial update
        SysCamera partialUpdatedSysCamera = new SysCamera();
        partialUpdatedSysCamera.setId(sysCamera.getId());

        partialUpdatedSysCamera
            .areaId(UPDATED_AREA_ID)
            .branch(UPDATED_BRANCH)
            .origin(UPDATED_ORIGIN)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .locationLat(UPDATED_LOCATION_LAT)
            .locationLan(UPDATED_LOCATION_LAN)
            .urlLiveStream(UPDATED_URL_LIVE_STREAM)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restSysCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSysCamera.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSysCamera))
            )
            .andExpect(status().isOk());

        // Validate the SysCamera in the database
        List<SysCamera> sysCameraList = sysCameraRepository.findAll();
        assertThat(sysCameraList).hasSize(databaseSizeBeforeUpdate);
        SysCamera testSysCamera = sysCameraList.get(sysCameraList.size() - 1);
        assertThat(testSysCamera.getCamCode()).isEqualTo(DEFAULT_CAM_CODE);
        assertThat(testSysCamera.getCamName()).isEqualTo(DEFAULT_CAM_NAME);
        assertThat(testSysCamera.getAreaId()).isEqualTo(UPDATED_AREA_ID);
        assertThat(testSysCamera.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testSysCamera.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testSysCamera.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSysCamera.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSysCamera.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSysCamera.getLocationLat()).isEqualTo(UPDATED_LOCATION_LAT);
        assertThat(testSysCamera.getLocationLan()).isEqualTo(UPDATED_LOCATION_LAN);
        assertThat(testSysCamera.getUrlSFTP()).isEqualTo(DEFAULT_URL_SFTP);
        assertThat(testSysCamera.getUrlLiveStream()).isEqualTo(UPDATED_URL_LIVE_STREAM);
        assertThat(testSysCamera.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSysCamera.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSysCamera.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysCamera.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSysCameraWithPatch() throws Exception {
        // Initialize the database
        sysCameraRepository.saveAndFlush(sysCamera);

        int databaseSizeBeforeUpdate = sysCameraRepository.findAll().size();

        // Update the sysCamera using partial update
        SysCamera partialUpdatedSysCamera = new SysCamera();
        partialUpdatedSysCamera.setId(sysCamera.getId());

        partialUpdatedSysCamera
            .camCode(UPDATED_CAM_CODE)
            .camName(UPDATED_CAM_NAME)
            .areaId(UPDATED_AREA_ID)
            .branch(UPDATED_BRANCH)
            .origin(UPDATED_ORIGIN)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .status(UPDATED_STATUS)
            .locationLat(UPDATED_LOCATION_LAT)
            .locationLan(UPDATED_LOCATION_LAN)
            .urlSFTP(UPDATED_URL_SFTP)
            .urlLiveStream(UPDATED_URL_LIVE_STREAM)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSysCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSysCamera.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSysCamera))
            )
            .andExpect(status().isOk());

        // Validate the SysCamera in the database
        List<SysCamera> sysCameraList = sysCameraRepository.findAll();
        assertThat(sysCameraList).hasSize(databaseSizeBeforeUpdate);
        SysCamera testSysCamera = sysCameraList.get(sysCameraList.size() - 1);
        assertThat(testSysCamera.getCamCode()).isEqualTo(UPDATED_CAM_CODE);
        assertThat(testSysCamera.getCamName()).isEqualTo(UPDATED_CAM_NAME);
        assertThat(testSysCamera.getAreaId()).isEqualTo(UPDATED_AREA_ID);
        assertThat(testSysCamera.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testSysCamera.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testSysCamera.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSysCamera.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSysCamera.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSysCamera.getLocationLat()).isEqualTo(UPDATED_LOCATION_LAT);
        assertThat(testSysCamera.getLocationLan()).isEqualTo(UPDATED_LOCATION_LAN);
        assertThat(testSysCamera.getUrlSFTP()).isEqualTo(UPDATED_URL_SFTP);
        assertThat(testSysCamera.getUrlLiveStream()).isEqualTo(UPDATED_URL_LIVE_STREAM);
        assertThat(testSysCamera.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSysCamera.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSysCamera.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysCamera.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSysCamera() throws Exception {
        int databaseSizeBeforeUpdate = sysCameraRepository.findAll().size();
        sysCamera.setId(count.incrementAndGet());

        // Create the SysCamera
        SysCameraDTO sysCameraDTO = sysCameraMapper.toDto(sysCamera);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sysCameraDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sysCameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysCamera in the database
        List<SysCamera> sysCameraList = sysCameraRepository.findAll();
        assertThat(sysCameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSysCamera() throws Exception {
        int databaseSizeBeforeUpdate = sysCameraRepository.findAll().size();
        sysCamera.setId(count.incrementAndGet());

        // Create the SysCamera
        SysCameraDTO sysCameraDTO = sysCameraMapper.toDto(sysCamera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sysCameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysCamera in the database
        List<SysCamera> sysCameraList = sysCameraRepository.findAll();
        assertThat(sysCameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSysCamera() throws Exception {
        int databaseSizeBeforeUpdate = sysCameraRepository.findAll().size();
        sysCamera.setId(count.incrementAndGet());

        // Create the SysCamera
        SysCameraDTO sysCameraDTO = sysCameraMapper.toDto(sysCamera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysCameraMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sysCameraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SysCamera in the database
        List<SysCamera> sysCameraList = sysCameraRepository.findAll();
        assertThat(sysCameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSysCamera() throws Exception {
        // Initialize the database
        sysCameraRepository.saveAndFlush(sysCamera);

        int databaseSizeBeforeDelete = sysCameraRepository.findAll().size();

        // Delete the sysCamera
        restSysCameraMockMvc
            .perform(delete(ENTITY_API_URL_ID, sysCamera.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysCamera> sysCameraList = sysCameraRepository.findAll();
        assertThat(sysCameraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
