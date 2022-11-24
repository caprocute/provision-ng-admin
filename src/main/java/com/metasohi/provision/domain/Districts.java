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
 * A Districts.
 */
@Entity
@Table(name = "districts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Districts implements Serializable, Persistable<String> {

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

    @Column(name = "province_code")
    private String provinceCode;

    @Column(name = "administrative_unit_id")
    private Long administrativeUnitId;

    @Transient
    private boolean isPersisted;

    @OneToMany(mappedBy = "districts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "administrativeRegions", "districts" }, allowSetters = true)
    private Set<Wards> wards = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "districts", "administrativeRegions", "administrativeUnits" }, allowSetters = true)
    private Provinces provinces;

    @ManyToOne
    @JsonIgnoreProperties(value = { "provinces", "districts" }, allowSetters = true)
    private AdministrativeUnits administrativeUnits;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCode() {
        return this.code;
    }

    public Districts code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Districts name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public Districts nameEn(String nameEn) {
        this.setNameEn(nameEn);
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Districts fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameEn() {
        return this.fullNameEn;
    }

    public Districts fullNameEn(String fullNameEn) {
        this.setFullNameEn(fullNameEn);
        return this;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public Districts codeName(String codeName) {
        this.setCodeName(codeName);
        return this;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getProvinceCode() {
        return this.provinceCode;
    }

    public Districts provinceCode(String provinceCode) {
        this.setProvinceCode(provinceCode);
        return this;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Long getAdministrativeUnitId() {
        return this.administrativeUnitId;
    }

    public Districts administrativeUnitId(Long administrativeUnitId) {
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

    public Districts setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Set<Wards> getWards() {
        return this.wards;
    }

    public void setWards(Set<Wards> wards) {
        if (this.wards != null) {
            this.wards.forEach(i -> i.setDistricts(null));
        }
        if (wards != null) {
            wards.forEach(i -> i.setDistricts(this));
        }
        this.wards = wards;
    }

    public Districts wards(Set<Wards> wards) {
        this.setWards(wards);
        return this;
    }

    public Districts addWards(Wards wards) {
        this.wards.add(wards);
        wards.setDistricts(this);
        return this;
    }

    public Districts removeWards(Wards wards) {
        this.wards.remove(wards);
        wards.setDistricts(null);
        return this;
    }

    public Provinces getProvinces() {
        return this.provinces;
    }

    public void setProvinces(Provinces provinces) {
        this.provinces = provinces;
    }

    public Districts provinces(Provinces provinces) {
        this.setProvinces(provinces);
        return this;
    }

    public AdministrativeUnits getAdministrativeUnits() {
        return this.administrativeUnits;
    }

    public void setAdministrativeUnits(AdministrativeUnits administrativeUnits) {
        this.administrativeUnits = administrativeUnits;
    }

    public Districts administrativeUnits(AdministrativeUnits administrativeUnits) {
        this.setAdministrativeUnits(administrativeUnits);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Districts)) {
            return false;
        }
        return code != null && code.equals(((Districts) o).code);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Districts{" +
            "code=" + getCode() +
            ", name='" + getName() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", fullNameEn='" + getFullNameEn() + "'" +
            ", codeName='" + getCodeName() + "'" +
            ", provinceCode='" + getProvinceCode() + "'" +
            ", administrativeUnitId=" + getAdministrativeUnitId() +
            "}";
    }
}
