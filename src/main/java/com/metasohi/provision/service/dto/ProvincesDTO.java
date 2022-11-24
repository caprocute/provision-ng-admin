package com.metasohi.provision.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.metasohi.provision.domain.Provinces} entity.
 */
public class ProvincesDTO implements Serializable {

    private String code;

    private String name;

    private String nameEn;

    private String fullName;

    private String fullNameEn;

    private String codeName;

    private Long administrativeUnitId;

    private Long administrativeRegionId;

    private AdministrativeRegionsDTO administrativeRegions;

    private AdministrativeUnitsDTO administrativeUnits;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameEn() {
        return fullNameEn;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Long getAdministrativeUnitId() {
        return administrativeUnitId;
    }

    public void setAdministrativeUnitId(Long administrativeUnitId) {
        this.administrativeUnitId = administrativeUnitId;
    }

    public Long getAdministrativeRegionId() {
        return administrativeRegionId;
    }

    public void setAdministrativeRegionId(Long administrativeRegionId) {
        this.administrativeRegionId = administrativeRegionId;
    }

    public AdministrativeRegionsDTO getAdministrativeRegions() {
        return administrativeRegions;
    }

    public void setAdministrativeRegions(AdministrativeRegionsDTO administrativeRegions) {
        this.administrativeRegions = administrativeRegions;
    }

    public AdministrativeUnitsDTO getAdministrativeUnits() {
        return administrativeUnits;
    }

    public void setAdministrativeUnits(AdministrativeUnitsDTO administrativeUnits) {
        this.administrativeUnits = administrativeUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProvincesDTO)) {
            return false;
        }

        ProvincesDTO provincesDTO = (ProvincesDTO) o;
        if (this.code == null) {
            return false;
        }
        return Objects.equals(this.code, provincesDTO.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProvincesDTO{" +
            "code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", fullNameEn='" + getFullNameEn() + "'" +
            ", codeName='" + getCodeName() + "'" +
            ", administrativeUnitId=" + getAdministrativeUnitId() +
            ", administrativeRegionId=" + getAdministrativeRegionId() +
            ", administrativeRegions=" + getAdministrativeRegions() +
            ", administrativeUnits=" + getAdministrativeUnits() +
            "}";
    }
}
