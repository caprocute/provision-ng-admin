package com.metasohi.provision.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.metasohi.provision.IntegrationTest;
import com.metasohi.provision.domain.SysOrganization;
import com.metasohi.provision.repository.SysOrganizationRepository;
import com.metasohi.provision.service.dto.SysOrganizationDTO;
import com.metasohi.provision.service.mapper.SysOrganizationMapper;
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
 * Integration tests for the {@link SysOrganizationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SysOrganizationResourceIT {

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final String DEFAULT_SYS_ORG_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SYS_ORG_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SYS_ORG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SYS_ORG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SYS_ORG_PATH = "AAAAAAAAAA";
    private static final String UPDATED_SYS_ORG_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_SYS_ORG_FULL_PATH = "AAAAAAAAAA";
    private static final String UPDATED_SYS_ORG_FULL_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_TYPE = 1L;
    private static final Long UPDATED_TYPE = 2L;

    private static final Long DEFAULT_TENANT_ID = 1L;
    private static final Long UPDATED_TENANT_ID = 2L;

    private static final Long DEFAULT_SYS_ORG_TYPE = 1L;
    private static final Long UPDATED_SYS_ORG_TYPE = 2L;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/sys-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SysOrganizationRepository sysOrganizationRepository;

    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSysOrganizationMockMvc;

    private SysOrganization sysOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysOrganization createEntity(EntityManager em) {
        SysOrganization sysOrganization = new SysOrganization()
            .parentId(DEFAULT_PARENT_ID)
            .sysOrgCode(DEFAULT_SYS_ORG_CODE)
            .sysOrgName(DEFAULT_SYS_ORG_NAME)
            .sysOrgPath(DEFAULT_SYS_ORG_PATH)
            .sysOrgFullPath(DEFAULT_SYS_ORG_FULL_PATH)
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS)
            .type(DEFAULT_TYPE)
            .tenantId(DEFAULT_TENANT_ID)
            .sysOrgType(DEFAULT_SYS_ORG_TYPE)
            .isActive(DEFAULT_IS_ACTIVE)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return sysOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysOrganization createUpdatedEntity(EntityManager em) {
        SysOrganization sysOrganization = new SysOrganization()
            .parentId(UPDATED_PARENT_ID)
            .sysOrgCode(UPDATED_SYS_ORG_CODE)
            .sysOrgName(UPDATED_SYS_ORG_NAME)
            .sysOrgPath(UPDATED_SYS_ORG_PATH)
            .sysOrgFullPath(UPDATED_SYS_ORG_FULL_PATH)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .type(UPDATED_TYPE)
            .tenantId(UPDATED_TENANT_ID)
            .sysOrgType(UPDATED_SYS_ORG_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return sysOrganization;
    }

    @BeforeEach
    public void initTest() {
        sysOrganization = createEntity(em);
    }

    @Test
    @Transactional
    void createSysOrganization() throws Exception {
        int databaseSizeBeforeCreate = sysOrganizationRepository.findAll().size();
        // Create the SysOrganization
        SysOrganizationDTO sysOrganizationDTO = sysOrganizationMapper.toDto(sysOrganization);
        restSysOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SysOrganization in the database
        List<SysOrganization> sysOrganizationList = sysOrganizationRepository.findAll();
        assertThat(sysOrganizationList).hasSize(databaseSizeBeforeCreate + 1);
        SysOrganization testSysOrganization = sysOrganizationList.get(sysOrganizationList.size() - 1);
        assertThat(testSysOrganization.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testSysOrganization.getSysOrgCode()).isEqualTo(DEFAULT_SYS_ORG_CODE);
        assertThat(testSysOrganization.getSysOrgName()).isEqualTo(DEFAULT_SYS_ORG_NAME);
        assertThat(testSysOrganization.getSysOrgPath()).isEqualTo(DEFAULT_SYS_ORG_PATH);
        assertThat(testSysOrganization.getSysOrgFullPath()).isEqualTo(DEFAULT_SYS_ORG_FULL_PATH);
        assertThat(testSysOrganization.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSysOrganization.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSysOrganization.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSysOrganization.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
        assertThat(testSysOrganization.getSysOrgType()).isEqualTo(DEFAULT_SYS_ORG_TYPE);
        assertThat(testSysOrganization.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSysOrganization.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSysOrganization.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSysOrganization.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSysOrganization.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSysOrganization.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSysOrganizationWithExistingId() throws Exception {
        // Create the SysOrganization with an existing ID
        sysOrganization.setId(1L);
        SysOrganizationDTO sysOrganizationDTO = sysOrganizationMapper.toDto(sysOrganization);

        int databaseSizeBeforeCreate = sysOrganizationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysOrganization in the database
        List<SysOrganization> sysOrganizationList = sysOrganizationRepository.findAll();
        assertThat(sysOrganizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSysOrganizations() throws Exception {
        // Initialize the database
        sysOrganizationRepository.saveAndFlush(sysOrganization);

        // Get all the sysOrganizationList
        restSysOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].sysOrgCode").value(hasItem(DEFAULT_SYS_ORG_CODE)))
            .andExpect(jsonPath("$.[*].sysOrgName").value(hasItem(DEFAULT_SYS_ORG_NAME)))
            .andExpect(jsonPath("$.[*].sysOrgPath").value(hasItem(DEFAULT_SYS_ORG_PATH)))
            .andExpect(jsonPath("$.[*].sysOrgFullPath").value(hasItem(DEFAULT_SYS_ORG_FULL_PATH)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID.intValue())))
            .andExpect(jsonPath("$.[*].sysOrgType").value(hasItem(DEFAULT_SYS_ORG_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getSysOrganization() throws Exception {
        // Initialize the database
        sysOrganizationRepository.saveAndFlush(sysOrganization);

        // Get the sysOrganization
        restSysOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, sysOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sysOrganization.getId().intValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.sysOrgCode").value(DEFAULT_SYS_ORG_CODE))
            .andExpect(jsonPath("$.sysOrgName").value(DEFAULT_SYS_ORG_NAME))
            .andExpect(jsonPath("$.sysOrgPath").value(DEFAULT_SYS_ORG_PATH))
            .andExpect(jsonPath("$.sysOrgFullPath").value(DEFAULT_SYS_ORG_FULL_PATH))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.intValue()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID.intValue()))
            .andExpect(jsonPath("$.sysOrgType").value(DEFAULT_SYS_ORG_TYPE.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSysOrganization() throws Exception {
        // Get the sysOrganization
        restSysOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSysOrganization() throws Exception {
        // Initialize the database
        sysOrganizationRepository.saveAndFlush(sysOrganization);

        int databaseSizeBeforeUpdate = sysOrganizationRepository.findAll().size();

        // Update the sysOrganization
        SysOrganization updatedSysOrganization = sysOrganizationRepository.findById(sysOrganization.getId()).get();
        // Disconnect from session so that the updates on updatedSysOrganization are not directly saved in db
        em.detach(updatedSysOrganization);
        updatedSysOrganization
            .parentId(UPDATED_PARENT_ID)
            .sysOrgCode(UPDATED_SYS_ORG_CODE)
            .sysOrgName(UPDATED_SYS_ORG_NAME)
            .sysOrgPath(UPDATED_SYS_ORG_PATH)
            .sysOrgFullPath(UPDATED_SYS_ORG_FULL_PATH)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .type(UPDATED_TYPE)
            .tenantId(UPDATED_TENANT_ID)
            .sysOrgType(UPDATED_SYS_ORG_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        SysOrganizationDTO sysOrganizationDTO = sysOrganizationMapper.toDto(updatedSysOrganization);

        restSysOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sysOrganizationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the SysOrganization in the database
        List<SysOrganization> sysOrganizationList = sysOrganizationRepository.findAll();
        assertThat(sysOrganizationList).hasSize(databaseSizeBeforeUpdate);
        SysOrganization testSysOrganization = sysOrganizationList.get(sysOrganizationList.size() - 1);
        assertThat(testSysOrganization.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testSysOrganization.getSysOrgCode()).isEqualTo(UPDATED_SYS_ORG_CODE);
        assertThat(testSysOrganization.getSysOrgName()).isEqualTo(UPDATED_SYS_ORG_NAME);
        assertThat(testSysOrganization.getSysOrgPath()).isEqualTo(UPDATED_SYS_ORG_PATH);
        assertThat(testSysOrganization.getSysOrgFullPath()).isEqualTo(UPDATED_SYS_ORG_FULL_PATH);
        assertThat(testSysOrganization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSysOrganization.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSysOrganization.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSysOrganization.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
        assertThat(testSysOrganization.getSysOrgType()).isEqualTo(UPDATED_SYS_ORG_TYPE);
        assertThat(testSysOrganization.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSysOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSysOrganization.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSysOrganization.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSysOrganization.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysOrganization.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSysOrganization() throws Exception {
        int databaseSizeBeforeUpdate = sysOrganizationRepository.findAll().size();
        sysOrganization.setId(count.incrementAndGet());

        // Create the SysOrganization
        SysOrganizationDTO sysOrganizationDTO = sysOrganizationMapper.toDto(sysOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sysOrganizationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysOrganization in the database
        List<SysOrganization> sysOrganizationList = sysOrganizationRepository.findAll();
        assertThat(sysOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSysOrganization() throws Exception {
        int databaseSizeBeforeUpdate = sysOrganizationRepository.findAll().size();
        sysOrganization.setId(count.incrementAndGet());

        // Create the SysOrganization
        SysOrganizationDTO sysOrganizationDTO = sysOrganizationMapper.toDto(sysOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysOrganization in the database
        List<SysOrganization> sysOrganizationList = sysOrganizationRepository.findAll();
        assertThat(sysOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSysOrganization() throws Exception {
        int databaseSizeBeforeUpdate = sysOrganizationRepository.findAll().size();
        sysOrganization.setId(count.incrementAndGet());

        // Create the SysOrganization
        SysOrganizationDTO sysOrganizationDTO = sysOrganizationMapper.toDto(sysOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SysOrganization in the database
        List<SysOrganization> sysOrganizationList = sysOrganizationRepository.findAll();
        assertThat(sysOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSysOrganizationWithPatch() throws Exception {
        // Initialize the database
        sysOrganizationRepository.saveAndFlush(sysOrganization);

        int databaseSizeBeforeUpdate = sysOrganizationRepository.findAll().size();

        // Update the sysOrganization using partial update
        SysOrganization partialUpdatedSysOrganization = new SysOrganization();
        partialUpdatedSysOrganization.setId(sysOrganization.getId());

        partialUpdatedSysOrganization
            .parentId(UPDATED_PARENT_ID)
            .sysOrgCode(UPDATED_SYS_ORG_CODE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .tenantId(UPDATED_TENANT_ID)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSysOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSysOrganization.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSysOrganization))
            )
            .andExpect(status().isOk());

        // Validate the SysOrganization in the database
        List<SysOrganization> sysOrganizationList = sysOrganizationRepository.findAll();
        assertThat(sysOrganizationList).hasSize(databaseSizeBeforeUpdate);
        SysOrganization testSysOrganization = sysOrganizationList.get(sysOrganizationList.size() - 1);
        assertThat(testSysOrganization.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testSysOrganization.getSysOrgCode()).isEqualTo(UPDATED_SYS_ORG_CODE);
        assertThat(testSysOrganization.getSysOrgName()).isEqualTo(DEFAULT_SYS_ORG_NAME);
        assertThat(testSysOrganization.getSysOrgPath()).isEqualTo(DEFAULT_SYS_ORG_PATH);
        assertThat(testSysOrganization.getSysOrgFullPath()).isEqualTo(DEFAULT_SYS_ORG_FULL_PATH);
        assertThat(testSysOrganization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSysOrganization.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSysOrganization.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSysOrganization.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
        assertThat(testSysOrganization.getSysOrgType()).isEqualTo(DEFAULT_SYS_ORG_TYPE);
        assertThat(testSysOrganization.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSysOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSysOrganization.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSysOrganization.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSysOrganization.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysOrganization.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSysOrganizationWithPatch() throws Exception {
        // Initialize the database
        sysOrganizationRepository.saveAndFlush(sysOrganization);

        int databaseSizeBeforeUpdate = sysOrganizationRepository.findAll().size();

        // Update the sysOrganization using partial update
        SysOrganization partialUpdatedSysOrganization = new SysOrganization();
        partialUpdatedSysOrganization.setId(sysOrganization.getId());

        partialUpdatedSysOrganization
            .parentId(UPDATED_PARENT_ID)
            .sysOrgCode(UPDATED_SYS_ORG_CODE)
            .sysOrgName(UPDATED_SYS_ORG_NAME)
            .sysOrgPath(UPDATED_SYS_ORG_PATH)
            .sysOrgFullPath(UPDATED_SYS_ORG_FULL_PATH)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .type(UPDATED_TYPE)
            .tenantId(UPDATED_TENANT_ID)
            .sysOrgType(UPDATED_SYS_ORG_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSysOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSysOrganization.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSysOrganization))
            )
            .andExpect(status().isOk());

        // Validate the SysOrganization in the database
        List<SysOrganization> sysOrganizationList = sysOrganizationRepository.findAll();
        assertThat(sysOrganizationList).hasSize(databaseSizeBeforeUpdate);
        SysOrganization testSysOrganization = sysOrganizationList.get(sysOrganizationList.size() - 1);
        assertThat(testSysOrganization.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testSysOrganization.getSysOrgCode()).isEqualTo(UPDATED_SYS_ORG_CODE);
        assertThat(testSysOrganization.getSysOrgName()).isEqualTo(UPDATED_SYS_ORG_NAME);
        assertThat(testSysOrganization.getSysOrgPath()).isEqualTo(UPDATED_SYS_ORG_PATH);
        assertThat(testSysOrganization.getSysOrgFullPath()).isEqualTo(UPDATED_SYS_ORG_FULL_PATH);
        assertThat(testSysOrganization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSysOrganization.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSysOrganization.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSysOrganization.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
        assertThat(testSysOrganization.getSysOrgType()).isEqualTo(UPDATED_SYS_ORG_TYPE);
        assertThat(testSysOrganization.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSysOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSysOrganization.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSysOrganization.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSysOrganization.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysOrganization.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSysOrganization() throws Exception {
        int databaseSizeBeforeUpdate = sysOrganizationRepository.findAll().size();
        sysOrganization.setId(count.incrementAndGet());

        // Create the SysOrganization
        SysOrganizationDTO sysOrganizationDTO = sysOrganizationMapper.toDto(sysOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sysOrganizationDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysOrganization in the database
        List<SysOrganization> sysOrganizationList = sysOrganizationRepository.findAll();
        assertThat(sysOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSysOrganization() throws Exception {
        int databaseSizeBeforeUpdate = sysOrganizationRepository.findAll().size();
        sysOrganization.setId(count.incrementAndGet());

        // Create the SysOrganization
        SysOrganizationDTO sysOrganizationDTO = sysOrganizationMapper.toDto(sysOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SysOrganization in the database
        List<SysOrganization> sysOrganizationList = sysOrganizationRepository.findAll();
        assertThat(sysOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSysOrganization() throws Exception {
        int databaseSizeBeforeUpdate = sysOrganizationRepository.findAll().size();
        sysOrganization.setId(count.incrementAndGet());

        // Create the SysOrganization
        SysOrganizationDTO sysOrganizationDTO = sysOrganizationMapper.toDto(sysOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSysOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sysOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SysOrganization in the database
        List<SysOrganization> sysOrganizationList = sysOrganizationRepository.findAll();
        assertThat(sysOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSysOrganization() throws Exception {
        // Initialize the database
        sysOrganizationRepository.saveAndFlush(sysOrganization);

        int databaseSizeBeforeDelete = sysOrganizationRepository.findAll().size();

        // Delete the sysOrganization
        restSysOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, sysOrganization.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysOrganization> sysOrganizationList = sysOrganizationRepository.findAll();
        assertThat(sysOrganizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
