package com.metasohi.provision.service.impl;

import com.metasohi.provision.domain.AdministrativeUnits;
import com.metasohi.provision.repository.AdministrativeUnitsRepository;
import com.metasohi.provision.service.AdministrativeUnitsService;
import com.metasohi.provision.service.dto.AdministrativeUnitsDTO;
import com.metasohi.provision.service.mapper.AdministrativeUnitsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdministrativeUnits}.
 */
@Service
@Transactional
public class AdministrativeUnitsServiceImpl implements AdministrativeUnitsService {

    private final Logger log = LoggerFactory.getLogger(AdministrativeUnitsServiceImpl.class);

    private final AdministrativeUnitsRepository administrativeUnitsRepository;

    private final AdministrativeUnitsMapper administrativeUnitsMapper;

    public AdministrativeUnitsServiceImpl(
        AdministrativeUnitsRepository administrativeUnitsRepository,
        AdministrativeUnitsMapper administrativeUnitsMapper
    ) {
        this.administrativeUnitsRepository = administrativeUnitsRepository;
        this.administrativeUnitsMapper = administrativeUnitsMapper;
    }

    @Override
    public AdministrativeUnitsDTO save(AdministrativeUnitsDTO administrativeUnitsDTO) {
        log.debug("Request to save AdministrativeUnits : {}", administrativeUnitsDTO);
        AdministrativeUnits administrativeUnits = administrativeUnitsMapper.toEntity(administrativeUnitsDTO);
        administrativeUnits = administrativeUnitsRepository.save(administrativeUnits);
        return administrativeUnitsMapper.toDto(administrativeUnits);
    }

    @Override
    public AdministrativeUnitsDTO update(AdministrativeUnitsDTO administrativeUnitsDTO) {
        log.debug("Request to save AdministrativeUnits : {}", administrativeUnitsDTO);
        AdministrativeUnits administrativeUnits = administrativeUnitsMapper.toEntity(administrativeUnitsDTO);
        administrativeUnits = administrativeUnitsRepository.save(administrativeUnits);
        return administrativeUnitsMapper.toDto(administrativeUnits);
    }

    @Override
    public Optional<AdministrativeUnitsDTO> partialUpdate(AdministrativeUnitsDTO administrativeUnitsDTO) {
        log.debug("Request to partially update AdministrativeUnits : {}", administrativeUnitsDTO);

        return administrativeUnitsRepository
            .findById(administrativeUnitsDTO.getId())
            .map(existingAdministrativeUnits -> {
                administrativeUnitsMapper.partialUpdate(existingAdministrativeUnits, administrativeUnitsDTO);

                return existingAdministrativeUnits;
            })
            .map(administrativeUnitsRepository::save)
            .map(administrativeUnitsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdministrativeUnitsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdministrativeUnits");
        return administrativeUnitsRepository.findAll(pageable).map(administrativeUnitsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdministrativeUnitsDTO> findOne(Long id) {
        log.debug("Request to get AdministrativeUnits : {}", id);
        return administrativeUnitsRepository.findById(id).map(administrativeUnitsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdministrativeUnits : {}", id);
        administrativeUnitsRepository.deleteById(id);
    }
}
