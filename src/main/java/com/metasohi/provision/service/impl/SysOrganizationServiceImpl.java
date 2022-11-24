package com.metasohi.provision.service.impl;

import com.metasohi.provision.domain.SysOrganization;
import com.metasohi.provision.repository.SysOrganizationRepository;
import com.metasohi.provision.service.SysOrganizationService;
import com.metasohi.provision.service.dto.SysOrganizationDTO;
import com.metasohi.provision.service.mapper.SysOrganizationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SysOrganization}.
 */
@Service
@Transactional
public class SysOrganizationServiceImpl implements SysOrganizationService {

    private final Logger log = LoggerFactory.getLogger(SysOrganizationServiceImpl.class);

    private final SysOrganizationRepository sysOrganizationRepository;

    private final SysOrganizationMapper sysOrganizationMapper;

    public SysOrganizationServiceImpl(SysOrganizationRepository sysOrganizationRepository, SysOrganizationMapper sysOrganizationMapper) {
        this.sysOrganizationRepository = sysOrganizationRepository;
        this.sysOrganizationMapper = sysOrganizationMapper;
    }

    @Override
    public SysOrganizationDTO save(SysOrganizationDTO sysOrganizationDTO) {
        log.debug("Request to save SysOrganization : {}", sysOrganizationDTO);
        SysOrganization sysOrganization = sysOrganizationMapper.toEntity(sysOrganizationDTO);
        sysOrganization = sysOrganizationRepository.save(sysOrganization);
        return sysOrganizationMapper.toDto(sysOrganization);
    }

    @Override
    public SysOrganizationDTO update(SysOrganizationDTO sysOrganizationDTO) {
        log.debug("Request to save SysOrganization : {}", sysOrganizationDTO);
        SysOrganization sysOrganization = sysOrganizationMapper.toEntity(sysOrganizationDTO);
        sysOrganization = sysOrganizationRepository.save(sysOrganization);
        return sysOrganizationMapper.toDto(sysOrganization);
    }

    @Override
    public Optional<SysOrganizationDTO> partialUpdate(SysOrganizationDTO sysOrganizationDTO) {
        log.debug("Request to partially update SysOrganization : {}", sysOrganizationDTO);

        return sysOrganizationRepository
            .findById(sysOrganizationDTO.getId())
            .map(existingSysOrganization -> {
                sysOrganizationMapper.partialUpdate(existingSysOrganization, sysOrganizationDTO);

                return existingSysOrganization;
            })
            .map(sysOrganizationRepository::save)
            .map(sysOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SysOrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysOrganizations");
        return sysOrganizationRepository.findAll(pageable).map(sysOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SysOrganizationDTO> findOne(Long id) {
        log.debug("Request to get SysOrganization : {}", id);
        return sysOrganizationRepository.findById(id).map(sysOrganizationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysOrganization : {}", id);
        sysOrganizationRepository.deleteById(id);
    }
}
