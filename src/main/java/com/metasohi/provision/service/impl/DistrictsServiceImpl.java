package com.metasohi.provision.service.impl;

import com.metasohi.provision.domain.Districts;
import com.metasohi.provision.repository.DistrictsRepository;
import com.metasohi.provision.service.DistrictsService;
import com.metasohi.provision.service.dto.DistrictsDTO;
import com.metasohi.provision.service.mapper.DistrictsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Districts}.
 */
@Service
@Transactional
public class DistrictsServiceImpl implements DistrictsService {

    private final Logger log = LoggerFactory.getLogger(DistrictsServiceImpl.class);

    private final DistrictsRepository districtsRepository;

    private final DistrictsMapper districtsMapper;

    public DistrictsServiceImpl(DistrictsRepository districtsRepository, DistrictsMapper districtsMapper) {
        this.districtsRepository = districtsRepository;
        this.districtsMapper = districtsMapper;
    }

    @Override
    public DistrictsDTO save(DistrictsDTO districtsDTO) {
        log.debug("Request to save Districts : {}", districtsDTO);
        Districts districts = districtsMapper.toEntity(districtsDTO);
        districts = districtsRepository.save(districts);
        return districtsMapper.toDto(districts);
    }

    @Override
    public DistrictsDTO update(DistrictsDTO districtsDTO) {
        log.debug("Request to save Districts : {}", districtsDTO);
        Districts districts = districtsMapper.toEntity(districtsDTO);
        districts.setIsPersisted();
        districts = districtsRepository.save(districts);
        return districtsMapper.toDto(districts);
    }

    @Override
    public Optional<DistrictsDTO> partialUpdate(DistrictsDTO districtsDTO) {
        log.debug("Request to partially update Districts : {}", districtsDTO);

        return districtsRepository
            .findById(districtsDTO.getCode())
            .map(existingDistricts -> {
                districtsMapper.partialUpdate(existingDistricts, districtsDTO);

                return existingDistricts;
            })
            .map(districtsRepository::save)
            .map(districtsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DistrictsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Districts");
        return districtsRepository.findAll(pageable).map(districtsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DistrictsDTO> findOne(String id) {
        log.debug("Request to get Districts : {}", id);
        return districtsRepository.findById(id).map(districtsMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Districts : {}", id);
        districtsRepository.deleteById(id);
    }
}
