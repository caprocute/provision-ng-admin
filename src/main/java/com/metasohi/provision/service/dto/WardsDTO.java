package com.metasohi.provision.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.metasohi.provision.domain.Wards} entity.
 */
public class WardsDTO implements Serializable {

    private String code;

    private String name;

    private String nameEn;

    private String fullName;

    private String fullNameEn;

    private String codeName;

    private String districtCode;

    private Long administrativeUnitId;

    private AdministrativeRegionsDTO administrativeRegions;

    private DistrictsDTO districts;

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

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public Long getAdministrativeUnitId() {
        return administrativeUnitId;
    }

    public void setAdministrativeUnitId(Long administrativeUnitId) {
        this.administrativeUnitId = administrativeUnitId;
    }

    public AdministrativeRegionsDTO getAdministrativeRegions() {
        return administrativeRegions;
    }

    public void setAdministrativeRegions(AdministrativeRegionsDTO administrativeRegions) {
        this.administrativeRegions = administrativeRegions;
    }

    public DistrictsDTO getDistricts() {
        return districts;
    }

    public void setDistricts(DistrictsDTO districts) {
        this.districts = districts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WardsDTO)) {
            return false;
        }

        WardsDTO wardsDTO = (WardsDTO) o;
        if (this.code == null) {
            return false;
        }
        return Objects.equals(this.code, wardsDTO.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WardsDTO{" +
            "code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", fullNameEn='" + getFullNameEn() + "'" +
            ", codeName='" + getCodeName() + "'" +
            ", districtCode='" + getDistrictCode() + "'" +
            ", administrativeUnitId=" + getAdministrativeUnitId() +
            ", administrativeRegions=" + getAdministrativeRegions() +
            ", districts=" + getDistricts() +
            "}";
    }
}
