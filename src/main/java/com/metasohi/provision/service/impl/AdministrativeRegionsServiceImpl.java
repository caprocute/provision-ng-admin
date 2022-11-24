package com.metasohi.provision.service.impl;

import com.metasohi.provision.domain.AdministrativeRegions;
import com.metasohi.provision.repository.AdministrativeRegionsRepository;
import com.metasohi.provision.service.AdministrativeRegionsService;
import com.metasohi.provision.service.dto.AdministrativeRegionsDTO;
import com.metasohi.provision.service.mapper.AdministrativeRegionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdministrativeRegions}.
 */
@Service
@Transactional
public class AdministrativeRegionsServiceImpl implements AdministrativeRegionsService {

    private final Logger log = LoggerFactory.getLogger(AdministrativeRegionsServiceImpl.class);

    private final AdministrativeRegionsRepository administrativeRegionsRepository;

    private final AdministrativeRegionsMapper administrativeRegionsMapper;

    public AdministrativeRegionsServiceImpl(
        AdministrativeRegionsRepository administrativeRegionsRepository,
        AdministrativeRegionsMapper administrativeRegionsMapper
    ) {
        this.administrativeRegionsRepository = administrativeRegionsRepository;
        this.administrativeRegionsMapper = administrativeRegionsMapper;
    }

    @Override
    public AdministrativeRegionsDTO save(AdministrativeRegionsDTO administrativeRegionsDTO) {
        log.debug("Request to save AdministrativeRegions : {}", administrativeRegionsDTO);
        AdministrativeRegions administrativeRegions = administrativeRegionsMapper.toEntity(administrativeRegionsDTO);
        administrativeRegions = administrativeRegionsRepository.save(administrativeRegions);
        return administrativeRegionsMapper.toDto(administrativeRegions);
    }

    @Override
    public AdministrativeRegionsDTO update(AdministrativeRegionsDTO administrativeRegionsDTO) {
        log.debug("Request to save AdministrativeRegions : {}", administrativeRegionsDTO);
        AdministrativeRegions administrativeRegions = administrativeRegionsMapper.toEntity(administrativeRegionsDTO);
        administrativeRegions = administrativeRegionsRepository.save(administrativeRegions);
        return administrativeRegionsMapper.toDto(administrativeRegions);
    }

    @Override
    public Optional<AdministrativeRegionsDTO> partialUpdate(AdministrativeRegionsDTO administrativeRegionsDTO) {
        log.debug("Request to partially update AdministrativeRegions : {}", administrativeRegionsDTO);

        return administrativeRegionsRepository
            .findById(administrativeRegionsDTO.getId())
            .map(existingAdministrativeRegions -> {
                administrativeRegionsMapper.partialUpdate(existingAdministrativeRegions, administrativeRegionsDTO);

                return existingAdministrativeRegions;
            })
            .map(administrativeRegionsRepository::save)
            .map(administrativeRegionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdministrativeRegionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdministrativeRegions");
        return administrativeRegionsRepository.findAll(pageable).map(administrativeRegionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdministrativeRegionsDTO> findOne(Long id) {
        log.debug("Request to get AdministrativeRegions : {}", id);
        return administrativeRegionsRepository.findById(id).map(administrativeRegionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdministrativeRegions : {}", id);
        administrativeRegionsRepository.deleteById(id);
    }
}
