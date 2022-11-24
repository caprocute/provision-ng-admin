package com.metasohi.provision.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.metasohi.provision.IntegrationTest;
import com.metasohi.provision.domain.SysOrganizationArea;
import com.metasohi.provision.repository.SysOrganizationAreaRepository;
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
 * Integration tests for the {@link SysOrganizationAreaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SysOrganizationAreaResourceIT {

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final String DEFAULT_AREA_ID = "AAAAAAAAAA";
    private static final String UPDATED_AREA_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/sys-organization-areas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SysOrganizationAreaRepository sysOrganizationAreaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSysOrganizationAreaMockMvc;

    private SysOrganizationArea sysOrganizationArea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysOrganizationArea createEntity(EntityManager em) {
        SysOrganizationArea sysOrganizationArea = new SysOrganizationArea()
            .orgId(DEFAULT_ORG_ID)
            .areaId(DEFAULT_AREA_ID)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return sysOrganizationArea;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysOrganizationArea createUpdatedEntity(EntityManager em) {
        SysOrganizationArea sysOrganizationArea = new SysOrganizationArea()
            .orgId(UPDATED_ORG_ID)
            .areaId(UPDATED_AREA_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return sysOrganizationArea;
    }

    @BeforeEach
    public void initTest() {
        sysOrganizationArea = createEntity(em);
    }

    @Test
    @Transactional
    void createSysOrganizationArea() throws Exception {
        int databaseSizeBeforeCreate = sysOrganizationAreaRepository.findAll().size();
        // Create the SysOrganizationArea
        restSysOrganizationAreaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationArea))
            )
            .andExpect(status().isCreated());

        // Validate the SysOrganizationArea in the database
        List<SysOrganizationArea> sysOrganizationAreaList = sysOrganizationAreaRepository.findAll();
        assertThat(sysOrganizationAreaList).hasSize(databaseSizeBeforeCreate + 1);
        SysOrganizationArea testSysOrganizationArea = sysOrganizationAreaList.get(sysOrganizationAreaList.size() - 1);
        assertThat(testSysOrganizationArea.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testSysOrganizationArea.getAreaId()).isEqualTo(DEFAULT_AREA_ID);
        assertThat(testSysOrganizationArea.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSysOrganizationArea.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSysOrganizationArea.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSysOrganizationArea.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSysOrganizationArea.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSysOrganizationAreaWithExistingId() throws Exception {
        // Create the SysOrganizationArea with an existing ID
        sysOrganizationArea.setId(1L);

        int databaseSizeBeforeCreate = sysOrganizationAreaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysOrganizationAreaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationArea))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysOrganizationArea in the database
        List<SysOrganizationArea> sysOrganizationAreaList = sysOrganizationAreaRepository.findAll();
        assertThat(sysOrganizationAreaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSysOrganizationAreas() throws Exception {
        // Initialize the database
        sysOrganizationAreaRepository.saveAndFlush(sysOrganizationArea);

        // Get all the sysOrganizationAreaList
        restSysOrganizationAreaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysOrganizationArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].areaId").value(hasItem(DEFAULT_AREA_ID)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getSysOrganizationArea() throws Exception {
        // Initialize the database
        sysOrganizationAreaRepository.saveAndFlush(sysOrganizationArea);

        // Get the sysOrganizationArea
        restSysOrganizationAreaMockMvc
            .perform(get(ENTITY_API_URL_ID, sysOrganizationArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sysOrganizationArea.getId().intValue()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.areaId").value(DEFAULT_AREA_ID))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSysOrganizationArea() throws Exception {
        // Get the sysOrganizationArea
        restSysOrganizationAreaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSysOrganizationArea() throws Exception {
        // Initialize the database
        sysOrganizationAreaRepository.saveAndFlush(sysOrganizationArea);

        int databaseSizeBeforeUpdate = sysOrganizationAreaRepository.findAll().size();

        // Update the sysOrganizationArea
        SysOrganizationArea updatedSysOrganizationArea = sysOrganizationAreaRepository.findById(sysOrganizationArea.getId()).get();
        // Disconnect from session so that the updates on updatedSysOrganizationArea are not directly saved in db
        em.detach(updatedSysOrganizationArea);
        updatedSysOrganizationArea
            .orgId(UPDATED_ORG_ID)
            .areaId(UPDATED_AREA_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSysOrganizationAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSysOrganizationArea.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSysOrganizationArea))
            )
            .andExpect(status().isOk());

        // Validate the SysOrganizationArea in the database
        List<SysOrganizationArea> sysOrganizationAreaList = sysOrganizationAreaRepository.findAll();
        assertThat(sysOrganizationAreaList).hasSize(databaseSizeBeforeUpdate);
        SysOrganizationArea testSysOrganizationArea = sysOrganizationAreaList.get(sysOrganizationAreaList.size() - 1);
        assertThat(testSysOrganizationArea.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testSysOrganizationArea.getAreaId()).isEqualTo(UPDATED_AREA_ID);
        assertThat(testSysOrganizationArea.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSysOrganizationArea.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSysOrganizationArea.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSysOrganizationArea.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysOrganizationArea.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSysOrganizationArea() throws Exception {
        int databaseSizeBeforeUpdate = sysOrganizationAreaRepository.findAll().size();
        sysOrganizationArea.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysOrganizationAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sysOrganizationArea.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationArea))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysOrganizationArea in the database
        List<SysOrganizationArea> sysOrganizationAreaList = sysOrganizationAreaRepository.findAll();
        assertThat(sysOrganizationAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSysOrganizationArea() throws Exception {
        int databaseSizeBeforeUpdate = sysOrganizationAreaRepository.findAll().size();
        sysOrganizationArea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysOrganizationAreaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationArea))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysOrganizationArea in the database
        List<SysOrganizationArea> sysOrganizationAreaList = sysOrganizationAreaRepository.findAll();
        assertThat(sysOrganizationAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSysOrganizationArea() throws Exception {
        int databaseSizeBeforeUpdate = sysOrganizationAreaRepository.findAll().size();
        sysOrganizationArea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysOrganizationAreaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationArea))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SysOrganizationArea in the database
        List<SysOrganizationArea> sysOrganizationAreaList = sysOrganizationAreaRepository.findAll();
        assertThat(sysOrganizationAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSysOrganizationAreaWithPatch() throws Exception {
        // Initialize the database
        sysOrganizationAreaRepository.saveAndFlush(sysOrganizationArea);

        int databaseSizeBeforeUpdate = sysOrganizationAreaRepository.findAll().size();

        // Update the sysOrganizationArea using partial update
        SysOrganizationArea partialUpdatedSysOrganizationArea = new SysOrganizationArea();
        partialUpdatedSysOrganizationArea.setId(sysOrganizationArea.getId());

        partialUpdatedSysOrganizationArea
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSysOrganizationAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSysOrganizationArea.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSysOrganizationArea))
            )
            .andExpect(status().isOk());

        // Validate the SysOrganizationArea in the database
        List<SysOrganizationArea> sysOrganizationAreaList = sysOrganizationAreaRepository.findAll();
        assertThat(sysOrganizationAreaList).hasSize(databaseSizeBeforeUpdate);
        SysOrganizationArea testSysOrganizationArea = sysOrganizationAreaList.get(sysOrganizationAreaList.size() - 1);
        assertThat(testSysOrganizationArea.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testSysOrganizationArea.getAreaId()).isEqualTo(DEFAULT_AREA_ID);
        assertThat(testSysOrganizationArea.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSysOrganizationArea.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSysOrganizationArea.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSysOrganizationArea.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysOrganizationArea.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSysOrganizationAreaWithPatch() throws Exception {
        // Initialize the database
        sysOrganizationAreaRepository.saveAndFlush(sysOrganizationArea);

        int databaseSizeBeforeUpdate = sysOrganizationAreaRepository.findAll().size();

        // Update the sysOrganizationArea using partial update
        SysOrganizationArea partialUpdatedSysOrganizationArea = new SysOrganizationArea();
        partialUpdatedSysOrganizationArea.setId(sysOrganizationArea.getId());

        partialUpdatedSysOrganizationArea
            .orgId(UPDATED_ORG_ID)
            .areaId(UPDATED_AREA_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSysOrganizationAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSysOrganizationArea.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSysOrganizationArea))
            )
            .andExpect(status().isOk());

        // Validate the SysOrganizationArea in the database
        List<SysOrganizationArea> sysOrganizationAreaList = sysOrganizationAreaRepository.findAll();
        assertThat(sysOrganizationAreaList).hasSize(databaseSizeBeforeUpdate);
        SysOrganizationArea testSysOrganizationArea = sysOrganizationAreaList.get(sysOrganizationAreaList.size() - 1);
        assertThat(testSysOrganizationArea.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testSysOrganizationArea.getAreaId()).isEqualTo(UPDATED_AREA_ID);
        assertThat(testSysOrganizationArea.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSysOrganizationArea.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSysOrganizationArea.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSysOrganizationArea.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysOrganizationArea.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSysOrganizationArea() throws Exception {
        int databaseSizeBeforeUpdate = sysOrganizationAreaRepository.findAll().size();
        sysOrganizationArea.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysOrganizationAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sysOrganizationArea.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationArea))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysOrganizationArea in the database
        List<SysOrganizationArea> sysOrganizationAreaList = sysOrganizationAreaRepository.findAll();
        assertThat(sysOrganizationAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSysOrganizationArea() throws Exception {
        int databaseSizeBeforeUpdate = sysOrganizationAreaRepository.findAll().size();
        sysOrganizationArea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysOrganizationAreaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationArea))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysOrganizationArea in the database
        List<SysOrganizationArea> sysOrganizationAreaList = sysOrganizationAreaRepository.findAll();
        assertThat(sysOrganizationAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSysOrganizationArea() throws Exception {
        int databaseSizeBeforeUpdate = sysOrganizationAreaRepository.findAll().size();
        sysOrganizationArea.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysOrganizationAreaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationArea))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SysOrganizationArea in the database
        List<SysOrganizationArea> sysOrganizationAreaList = sysOrganizationAreaRepository.findAll();
        assertThat(sysOrganizationAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSysOrganizationArea() throws Exception {
        // Initialize the database
        sysOrganizationAreaRepository.saveAndFlush(sysOrganizationArea);

        int databaseSizeBeforeDelete = sysOrganizationAreaRepository.findAll().size();

        // Delete the sysOrganizationArea
        restSysOrganizationAreaMockMvc
            .perform(delete(ENTITY_API_URL_ID, sysOrganizationArea.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysOrganizationArea> sysOrganizationAreaList = sysOrganizationAreaRepository.findAll();
        assertThat(sysOrganizationAreaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
