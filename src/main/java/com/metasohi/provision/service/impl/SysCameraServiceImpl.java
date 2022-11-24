package com.metasohi.provision.service.impl;

import com.metasohi.provision.domain.SysCamera;
import com.metasohi.provision.repository.SysCameraRepository;
import com.metasohi.provision.service.SysCameraService;
import com.metasohi.provision.service.dto.SysCameraDTO;
import com.metasohi.provision.service.mapper.SysCameraMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SysCamera}.
 */
@Service
@Transactional
public class SysCameraServiceImpl implements SysCameraService {

    private final Logger log = LoggerFactory.getLogger(SysCameraServiceImpl.class);

    private final SysCameraRepository sysCameraRepository;

    private final SysCameraMapper sysCameraMapper;

    public SysCameraServiceImpl(SysCameraRepository sysCameraRepository, SysCameraMapper sysCameraMapper) {
        this.sysCameraRepository = sysCameraRepository;
        this.sysCameraMapper = sysCameraMapper;
    }

    @Override
    public SysCameraDTO save(SysCameraDTO sysCameraDTO) {
        log.debug("Request to save SysCamera : {}", sysCameraDTO);
        SysCamera sysCamera = sysCameraMapper.toEntity(sysCameraDTO);
        sysCamera = sysCameraRepository.save(sysCamera);
        return sysCameraMapper.toDto(sysCamera);
    }

    @Override
    public SysCameraDTO update(SysCameraDTO sysCameraDTO) {
        log.debug("Request to save SysCamera : {}", sysCameraDTO);
        SysCamera sysCamera = sysCameraMapper.toEntity(sysCameraDTO);
        sysCamera = sysCameraRepository.save(sysCamera);
        return sysCameraMapper.toDto(sysCamera);
    }

    @Override
    public Optional<SysCameraDTO> partialUpdate(SysCameraDTO sysCameraDTO) {
        log.debug("Request to partially update SysCamera : {}", sysCameraDTO);

        return sysCameraRepository
            .findById(sysCameraDTO.getId())
            .map(existingSysCamera -> {
                sysCameraMapper.partialUpdate(existingSysCamera, sysCameraDTO);

                return existingSysCamera;
            })
            .map(sysCameraRepository::save)
            .map(sysCameraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SysCameraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysCameras");
        return sysCameraRepository.findAll(pageable).map(sysCameraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SysCameraDTO> findOne(Long id) {
        log.debug("Request to get SysCamera : {}", id);
        return sysCameraRepository.findById(id).map(sysCameraMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysCamera : {}", id);
        sysCameraRepository.deleteById(id);
    }
}
