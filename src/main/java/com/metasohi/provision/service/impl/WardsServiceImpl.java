package com.metasohi.provision.service.impl;

import com.metasohi.provision.domain.Wards;
import com.metasohi.provision.repository.WardsRepository;
import com.metasohi.provision.service.WardsService;
import com.metasohi.provision.service.dto.WardsDTO;
import com.metasohi.provision.service.mapper.WardsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Wards}.
 */
@Service
@Transactional
public class WardsServiceImpl implements WardsService {

    private final Logger log = LoggerFactory.getLogger(WardsServiceImpl.class);

    private final WardsRepository wardsRepository;

    private final WardsMapper wardsMapper;

    public WardsServiceImpl(WardsRepository wardsRepository, WardsMapper wardsMapper) {
        this.wardsRepository = wardsRepository;
        this.wardsMapper = wardsMapper;
    }

    @Override
    public WardsDTO save(WardsDTO wardsDTO) {
        log.debug("Request to save Wards : {}", wardsDTO);
        Wards wards = wardsMapper.toEntity(wardsDTO);
        wards = wardsRepository.save(wards);
        return wardsMapper.toDto(wards);
    }

    @Override
    public WardsDTO update(WardsDTO wardsDTO) {
        log.debug("Request to save Wards : {}", wardsDTO);
        Wards wards = wardsMapper.toEntity(wardsDTO);
        wards.setIsPersisted();
        wards = wardsRepository.save(wards);
        return wardsMapper.toDto(wards);
    }

    @Override
    public Optional<WardsDTO> partialUpdate(WardsDTO wardsDTO) {
        log.debug("Request to partially update Wards : {}", wardsDTO);

        return wardsRepository
            .findById(wardsDTO.getCode())
            .map(existingWards -> {
                wardsMapper.partialUpdate(existingWards, wardsDTO);

                return existingWards;
            })
            .map(wardsRepository::save)
            .map(wardsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WardsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wards");
        return wardsRepository.findAll(pageable).map(wardsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WardsDTO> findOne(String id) {
        log.debug("Request to get Wards : {}", id);
        return wardsRepository.findById(id).map(wardsMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Wards : {}", id);
        wardsRepository.deleteById(id);
    }
}
