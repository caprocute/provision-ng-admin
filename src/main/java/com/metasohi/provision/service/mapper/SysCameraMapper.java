package com.metasohi.provision.service.mapper;

import com.metasohi.provision.domain.SysCamera;
import com.metasohi.provision.service.dto.SysCameraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysCamera} and its DTO {@link SysCameraDTO}.
 */
@Mapper(componentModel = "spring")
public interface SysCameraMapper extends EntityMapper<SysCameraDTO, SysCamera> {}
