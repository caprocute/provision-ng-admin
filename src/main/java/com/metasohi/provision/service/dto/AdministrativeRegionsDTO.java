package com.metasohi.provision.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.metasohi.provision.domain.AdministrativeRegions} entity.
 */
public class AdministrativeRegionsDTO implements Serializable {

    private Long id;

    private String name;

    private String nameEn;

    private String codeName;

    private String codeNameEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof AdministrativeRegionsDTO)) {
            return false;
        }

        AdministrativeRegionsDTO administrativeRegionsDTO = (AdministrativeRegionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, administrativeRegionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministrativeRegionsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", codeName='" + getCodeName() + "'" +
            ", codeNameEn='" + getCodeNameEn() + "'" +
            "}";
    }
}
