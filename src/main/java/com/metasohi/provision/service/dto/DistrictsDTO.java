package com.metasohi.provision.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.metasohi.provision.domain.Districts} entity.
 */
public class DistrictsDTO implements Serializable {

    private String code;

    private String name;

    private String nameEn;

    private String fullName;

    private String fullNameEn;

    private String codeName;

    private String provinceCode;

    private Long administrativeUnitId;

    private ProvincesDTO provinces;

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

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Long getAdministrativeUnitId() {
        return administrativeUnitId;
    }

    public void setAdministrativeUnitId(Long administrativeUnitId) {
        this.administrativeUnitId = administrativeUnitId;
    }

    public ProvincesDTO getProvinces() {
        return provinces;
    }

    public void setProvinces(ProvincesDTO provinces) {
        this.provinces = provinces;
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
        if (!(o instanceof DistrictsDTO)) {
            return false;
        }

        DistrictsDTO districtsDTO = (DistrictsDTO) o;
        if (this.code == null) {
            return false;
        }
        return Objects.equals(this.code, districtsDTO.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictsDTO{" +
            "code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", fullNameEn='" + getFullNameEn() + "'" +
            ", codeName='" + getCodeName() + "'" +
            ", provinceCode='" + getProvinceCode() + "'" +
            ", administrativeUnitId=" + getAdministrativeUnitId() +
            ", provinces=" + getProvinces() +
            ", administrativeUnits=" + getAdministrativeUnits() +
            "}";
    }
}
