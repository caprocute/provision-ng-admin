package com.metasohi.provision.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Wards.
 */
@Entity
@Table(name = "wards")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Wards implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "full_name_en")
    private String fullNameEn;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "district_code")
    private String districtCode;

    @Column(name = "administrative_unit_id")
    private Long administrativeUnitId;

    @Transient
    private boolean isPersisted;

    @ManyToOne
    @JsonIgnoreProperties(value = { "provinces", "wards" }, allowSetters = true)
    private AdministrativeRegions administrativeRegions;

    @ManyToOne
    @JsonIgnoreProperties(value = { "wards", "provinces", "administrativeUnits" }, allowSetters = true)
    private Districts districts;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCode() {
        return this.code;
    }

    public Wards code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Wards name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public Wards nameEn(String nameEn) {
        this.setNameEn(nameEn);
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Wards fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameEn() {
        return this.fullNameEn;
    }

    public Wards fullNameEn(String fullNameEn) {
        this.setFullNameEn(fullNameEn);
        return this;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public Wards codeName(String codeName) {
        this.setCodeName(codeName);
        return this;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getDistrictCode() {
        return this.districtCode;
    }

    public Wards districtCode(String districtCode) {
        this.setDistrictCode(districtCode);
        return this;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public Long getAdministrativeUnitId() {
        return this.administrativeUnitId;
    }

    public Wards administrativeUnitId(Long administrativeUnitId) {
        this.setAdministrativeUnitId(administrativeUnitId);
        return this;
    }

    public void setAdministrativeUnitId(Long administrativeUnitId) {
        this.administrativeUnitId = administrativeUnitId;
    }

    @Override
    public String getId() {
        return this.code;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Wards setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public AdministrativeRegions getAdministrativeRegions() {
        return this.administrativeRegions;
    }

    public void setAdministrativeRegions(AdministrativeRegions administrativeRegions) {
        this.administrativeRegions = administrativeRegions;
    }

    public Wards administrativeRegions(AdministrativeRegions administrativeRegions) {
        this.setAdministrativeRegions(administrativeRegions);
        return this;
    }

    public Districts getDistricts() {
        return this.districts;
    }

    public void setDistricts(Districts districts) {
        this.districts = districts;
    }

    public Wards districts(Districts districts) {
        this.setDistricts(districts);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wards)) {
            return false;
        }
        return code != null && code.equals(((Wards) o).code);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wards{" +
            "code=" + getCode() +
            ", name='" + getName() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", fullNameEn='" + getFullNameEn() + "'" +
            ", codeName='" + getCodeName() + "'" +
            ", districtCode='" + getDistrictCode() + "'" +
            ", administrativeUnitId=" + getAdministrativeUnitId() +
            "}";
    }
}
