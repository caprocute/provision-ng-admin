package com.metasohi.provision.service.mapper;

import com.metasohi.provision.domain.AdministrativeRegions;
import com.metasohi.provision.domain.Districts;
import com.metasohi.provision.domain.Wards;
import com.metasohi.provision.service.dto.AdministrativeRegionsDTO;
import com.metasohi.provision.service.dto.DistrictsDTO;
import com.metasohi.provision.service.dto.WardsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wards} and its DTO {@link WardsDTO}.
 */
@Mapper(componentModel = "spring")
public interface WardsMapper extends EntityMapper<WardsDTO, Wards> {
    @Mapping(target = "administrativeRegions", source = "administrativeRegions", qualifiedByName = "administrativeRegionsId")
    @Mapping(target = "districts", source = "districts", qualifiedByName = "districtsCode")
    WardsDTO toDto(Wards s);

    @Named("administrativeRegionsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdministrativeRegionsDTO toDtoAdministrativeRegionsId(AdministrativeRegions administrativeRegions);

    @Named("districtsCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "code", source = "code")
    DistrictsDTO toDtoDistrictsCode(Districts districts);
}
