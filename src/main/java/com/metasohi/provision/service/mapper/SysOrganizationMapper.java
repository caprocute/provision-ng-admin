package com.metasohi.provision.service.mapper;

import com.metasohi.provision.domain.SysOrganization;
import com.metasohi.provision.service.dto.SysOrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysOrganization} and its DTO {@link SysOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface SysOrganizationMapper extends EntityMapper<SysOrganizationDTO, SysOrganization> {}
