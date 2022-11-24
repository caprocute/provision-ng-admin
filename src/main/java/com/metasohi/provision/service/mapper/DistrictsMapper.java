package com.metasohi.provision.service.mapper;

import com.metasohi.provision.domain.AdministrativeUnits;
import com.metasohi.provision.domain.Districts;
import com.metasohi.provision.domain.Provinces;
import com.metasohi.provision.service.dto.AdministrativeUnitsDTO;
import com.metasohi.provision.service.dto.DistrictsDTO;
import com.metasohi.provision.service.dto.ProvincesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Districts} and its DTO {@link DistrictsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictsMapper extends EntityMapper<DistrictsDTO, Districts> {
    @Mapping(target = "provinces", source = "provinces", qualifiedByName = "provincesCode")
    @Mapping(target = "administrativeUnits", source = "administrativeUnits", qualifiedByName = "administrativeUnitsId")
    DistrictsDTO toDto(Districts s);

    @Named("provincesCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "code", source = "code")
    ProvincesDTO toDtoProvincesCode(Provinces provinces);

    @Named("administrativeUnitsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdministrativeUnitsDTO toDtoAdministrativeUnitsId(AdministrativeUnits administrativeUnits);
}
