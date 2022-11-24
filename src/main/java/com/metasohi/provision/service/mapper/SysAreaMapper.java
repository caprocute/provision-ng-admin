package com.metasohi.provision.service.mapper;

import com.metasohi.provision.domain.SysArea;
import com.metasohi.provision.service.dto.SysAreaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysArea} and its DTO {@link SysAreaDTO}.
 */
@Mapper(componentModel = "spring")
public interface SysAreaMapper extends EntityMapper<SysAreaDTO, SysArea> {}
