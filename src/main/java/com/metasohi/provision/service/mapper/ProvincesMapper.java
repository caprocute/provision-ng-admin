package com.metasohi.provision.service.mapper;

import com.metasohi.provision.domain.AdministrativeRegions;
import com.metasohi.provision.domain.AdministrativeUnits;
import com.metasohi.provision.domain.Provinces;
import com.metasohi.provision.service.dto.AdministrativeRegionsDTO;
import com.metasohi.provision.service.dto.AdministrativeUnitsDTO;
import com.metasohi.provision.service.dto.ProvincesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Provinces} and its DTO {@link ProvincesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProvincesMapper extends EntityMapper<ProvincesDTO, Provinces> {
    @Mapping(target = "administrativeRegions", source = "administrativeRegions", qualifiedByName = "administrativeRegionsId")
    @Mapping(target = "administrativeUnits", source = "administrativeUnits", qualifiedByName = "administrativeUnitsId")
    ProvincesDTO toDto(Provinces s);

    @Named("administrativeRegionsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdministrativeRegionsDTO toDtoAdministrativeRegionsId(AdministrativeRegions administrativeRegions);

    @Named("administrativeUnitsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdministrativeUnitsDTO toDtoAdministrativeUnitsId(AdministrativeUnits administrativeUnits);
}
