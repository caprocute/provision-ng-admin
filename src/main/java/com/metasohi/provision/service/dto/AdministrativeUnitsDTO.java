package com.metasohi.provision.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.metasohi.provision.domain.AdministrativeUnits} entity.
 */
public class AdministrativeUnitsDTO implements Serializable {

    private Long id;

    private String fullName;

    private String fullNameEn;

    private String shortName;

    private String shortNameEn;

    private String codeName;

    private String codeNameEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortNameEn() {
        return shortNameEn;
    }

    public void setShortNameEn(String shortNameEn) {
        this.shortNameEn = shortNameEn;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeNameEn() {
        return codeNameEn;
    }

    public void setCodeNameEn(String codeNameEn) {
        this.codeNameEn = codeNameEn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdministrativeUnitsDTO)) {
            return false;
        }

        AdministrativeUnitsDTO administrativeUnitsDTO = (AdministrativeUnitsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, administrativeUnitsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministrativeUnitsDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", fullNameEn='" + getFullNameEn() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", shortNameEn='" + getShortNameEn() + "'" +
            ", codeName='" + getCodeName() + "'" +
            ", codeNameEn='" + getCodeNameEn() + "'" +
            "}";
    }
}
