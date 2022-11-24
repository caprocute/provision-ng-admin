package com.metasohi.provision.service.mapper;

import com.metasohi.provision.domain.AdministrativeUnits;
import com.metasohi.provision.service.dto.AdministrativeUnitsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdministrativeUnits} and its DTO {@link AdministrativeUnitsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdministrativeUnitsMapper extends EntityMapper<AdministrativeUnitsDTO, AdministrativeUnits> {}
