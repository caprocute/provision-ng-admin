package com.metasohi.provision.service.impl;

import com.metasohi.provision.domain.SysArea;
import com.metasohi.provision.repository.SysAreaRepository;
import com.metasohi.provision.service.SysAreaService;
import com.metasohi.provision.service.dto.SysAreaDTO;
import com.metasohi.provision.service.mapper.SysAreaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SysArea}.
 */
@Service
@Transactional
public class SysAreaServiceImpl implements SysAreaService {

    private final Logger log = LoggerFactory.getLogger(SysAreaServiceImpl.class);

    private final SysAreaRepository sysAreaRepository;

    private final SysAreaMapper sysAreaMapper;

    public SysAreaServiceImpl(SysAreaRepository sysAreaRepository, SysAreaMapper sysAreaMapper) {
        this.sysAreaRepository = sysAreaRepository;
        this.sysAreaMapper = sysAreaMapper;
    }

    @Override
    public SysAreaDTO save(SysAreaDTO sysAreaDTO) {
        log.debug("Request to save SysArea : {}", sysAreaDTO);
        SysArea sysArea = sysAreaMapper.toEntity(sysAreaDTO);
        sysArea = sysAreaRepository.save(sysArea);
        return sysAreaMapper.toDto(sysArea);
    }

    @Override
    public SysAreaDTO update(SysAreaDTO sysAreaDTO) {
        log.debug("Request to save SysArea : {}", sysAreaDTO);
        SysArea sysArea = sysAreaMapper.toEntity(sysAreaDTO);
        sysArea = sysAreaRepository.save(sysArea);
        return sysAreaMapper.toDto(sysArea);
    }

    @Override
    public Optional<SysAreaDTO> partialUpdate(SysAreaDTO sysAreaDTO) {
        log.debug("Request to partially update SysArea : {}", sysAreaDTO);

        return sysAreaRepository
            .findById(sysAreaDTO.getId())
            .map(existingSysArea -> {
                sysAreaMapper.partialUpdate(existingSysArea, sysAreaDTO);

                return existingSysArea;
            })
            .map(sysAreaRepository::save)
            .map(sysAreaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SysAreaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysAreas");
        return sysAreaRepository.findAll(pageable).map(sysAreaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SysAreaDTO> findOne(Long id) {
        log.debug("Request to get SysArea : {}", id);
        return sysAreaRepository.findById(id).map(sysAreaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysArea : {}", id);
        sysAreaRepository.deleteById(id);
    }
}
