package com.metasohi.provision.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Provinces.
 */
@Entity
@Table(name = "provinces")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Provinces implements Serializable, Persistable<String> {

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

    @Column(name = "administrative_unit_id")
    private Long administrativeUnitId;

    @Column(name = "administrative_region_id")
    private Long administrativeRegionId;

    @Transient
    private boolean isPersisted;

    @OneToMany(mappedBy = "provinces")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "wards", "provinces", "administrativeUnits" }, allowSetters = true)
    private Set<Districts> districts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "provinces", "wards" }, allowSetters = true)
    private AdministrativeRegions administrativeRegions;

    @ManyToOne
    @JsonIgnoreProperties(value = { "provinces", "districts" }, allowSetters = true)
    private AdministrativeUnits administrativeUnits;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCode() {
        return this.code;
    }

    public Provinces code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Provinces name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public Provinces nameEn(String nameEn) {
        this.setNameEn(nameEn);
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Provinces fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameEn() {
        return this.fullNameEn;
    }

    public Provinces fullNameEn(String fullNameEn) {
        this.setFullNameEn(fullNameEn);
        return this;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public Provinces codeName(String codeName) {
        this.setCodeName(codeName);
        return this;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Long getAdministrativeUnitId() {
        return this.administrativeUnitId;
    }

    public Provinces administrativeUnitId(Long administrativeUnitId) {
        this.setAdministrativeUnitId(administrativeUnitId);
        return this;
    }

    public void setAdministrativeUnitId(Long administrativeUnitId) {
        this.administrativeUnitId = administrativeUnitId;
    }

    public Long getAdministrativeRegionId() {
        return this.administrativeRegionId;
    }

    public Provinces administrativeRegionId(Long administrativeRegionId) {
        this.setAdministrativeRegionId(administrativeRegionId);
        return this;
    }

    public void setAdministrativeRegionId(Long administrativeRegionId) {
        this.administrativeRegionId = administrativeRegionId;
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

    public Provinces setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Set<Districts> getDistricts() {
        return this.districts;
    }

    public void setDistricts(Set<Districts> districts) {
        if (this.districts != null) {
            this.districts.forEach(i -> i.setProvinces(null));
        }
        if (districts != null) {
            districts.forEach(i -> i.setProvinces(this));
        }
        this.districts = districts;
    }

    public Provinces districts(Set<Districts> districts) {
        this.setDistricts(districts);
        return this;
    }

    public Provinces addDistricts(Districts districts) {
        this.districts.add(districts);
        districts.setProvinces(this);
        return this;
    }

    public Provinces removeDistricts(Districts districts) {
        this.districts.remove(districts);
        districts.setProvinces(null);
        return this;
    }

    public AdministrativeRegions getAdministrativeRegions() {
        return this.administrativeRegions;
    }

    public void setAdministrativeRegions(AdministrativeRegions administrativeRegions) {
        this.administrativeRegions = administrativeRegions;
    }

    public Provinces administrativeRegions(AdministrativeRegions administrativeRegions) {
        this.setAdministrativeRegions(administrativeRegions);
        return this;
    }

    public AdministrativeUnits getAdministrativeUnits() {
        return this.administrativeUnits;
    }

    public void setAdministrativeUnits(AdministrativeUnits administrativeUnits) {
        this.administrativeUnits = administrativeUnits;
    }

    public Provinces administrativeUnits(AdministrativeUnits administrativeUnits) {
        this.setAdministrativeUnits(administrativeUnits);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Provinces)) {
            return false;
        }
        return code != null && code.equals(((Provinces) o).code);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Provinces{" +
            "code=" + getCode() +
            ", name='" + getName() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", fullNameEn='" + getFullNameEn() + "'" +
            ", codeName='" + getCodeName() + "'" +
            ", administrativeUnitId=" + getAdministrativeUnitId() +
            ", administrativeRegionId=" + getAdministrativeRegionId() +
            "}";
    }
}
