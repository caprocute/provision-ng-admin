package com.metasohi.provision.service.mapper;

import com.metasohi.provision.domain.AdministrativeRegions;
import com.metasohi.provision.service.dto.AdministrativeRegionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdministrativeRegions} and its DTO {@link AdministrativeRegionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdministrativeRegionsMapper extends EntityMapper<AdministrativeRegionsDTO, AdministrativeRegions> {}
