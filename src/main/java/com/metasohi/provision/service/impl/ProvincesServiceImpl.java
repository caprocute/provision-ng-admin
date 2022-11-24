package com.metasohi.provision.service.impl;

import com.metasohi.provision.domain.Provinces;
import com.metasohi.provision.repository.ProvincesRepository;
import com.metasohi.provision.service.ProvincesService;
import com.metasohi.provision.service.dto.ProvincesDTO;
import com.metasohi.provision.service.mapper.ProvincesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Provinces}.
 */
@Service
@Transactional
public class ProvincesServiceImpl implements ProvincesService {

    private final Logger log = LoggerFactory.getLogger(ProvincesServiceImpl.class);

    private final ProvincesRepository provincesRepository;

    private final ProvincesMapper provincesMapper;

    public ProvincesServiceImpl(ProvincesRepository provincesRepository, ProvincesMapper provincesMapper) {
        this.provincesRepository = provincesRepository;
        this.provincesMapper = provincesMapper;
    }

    @Override
    public ProvincesDTO save(ProvincesDTO provincesDTO) {
        log.debug("Request to save Provinces : {}", provincesDTO);
        Provinces provinces = provincesMapper.toEntity(provincesDTO);
        provinces = provincesRepository.save(provinces);
        return provincesMapper.toDto(provinces);
    }

    @Override
    public ProvincesDTO update(ProvincesDTO provincesDTO) {
        log.debug("Request to save Provinces : {}", provincesDTO);
        Provinces provinces = provincesMapper.toEntity(provincesDTO);
        provinces.setIsPersisted();
        provinces = provincesRepository.save(provinces);
        return provincesMapper.toDto(provinces);
    }

    @Override
    public Optional<ProvincesDTO> partialUpdate(ProvincesDTO provincesDTO) {
        log.debug("Request to partially update Provinces : {}", provincesDTO);

        return provincesRepository
            .findById(provincesDTO.getCode())
            .map(existingProvinces -> {
                provincesMapper.partialUpdate(existingProvinces, provincesDTO);

                return existingProvinces;
            })
            .map(provincesRepository::save)
            .map(provincesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProvincesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Provinces");
        return provincesRepository.findAll(pageable).map(provincesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProvincesDTO> findOne(String id) {
        log.debug("Request to get Provinces : {}", id);
        return provincesRepository.findById(id).map(provincesMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Provinces : {}", id);
        provincesRepository.deleteById(id);
    }
}
