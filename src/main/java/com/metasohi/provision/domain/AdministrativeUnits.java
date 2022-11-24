package com.metasohi.provision.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdministrativeUnits.
 */
@Entity
@Table(name = "administrative_units")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdministrativeUnits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "full_name_en")
    private String fullNameEn;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "short_name_en")
    private String shortNameEn;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "code_name_en")
    private String codeNameEn;

    @OneToMany(mappedBy = "administrativeUnits")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "districts", "administrativeRegions", "administrativeUnits" }, allowSetters = true)
    private Set<Provinces> provinces = new HashSet<>();

    @OneToMany(mappedBy = "administrativeUnits")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "wards", "provinces", "administrativeUnits" }, allowSetters = true)
    private Set<Districts> districts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdministrativeUnits id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public AdministrativeUnits fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameEn() {
        return this.fullNameEn;
    }

    public AdministrativeUnits fullNameEn(String fullNameEn) {
        this.setFullNameEn(fullNameEn);
        return this;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getShortName() {
        return this.shortName;
    }

    public AdministrativeUnits shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortNameEn() {
        return this.shortNameEn;
    }

    public AdministrativeUnits shortNameEn(String shortNameEn) {
        this.setShortNameEn(shortNameEn);
        return this;
    }

    public void setShortNameEn(String shortNameEn) {
        this.shortNameEn = shortNameEn;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public AdministrativeUnits codeName(String codeName) {
        this.setCodeName(codeName);
        return this;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeNameEn() {
        return this.codeNameEn;
    }

    public AdministrativeUnits codeNameEn(String codeNameEn) {
        this.setCodeNameEn(codeNameEn);
        return this;
    }

    public void setCodeNameEn(String codeNameEn) {
        this.codeNameEn = codeNameEn;
    }

    public Set<Provinces> getProvinces() {
        return this.provinces;
    }

    public void setProvinces(Set<Provinces> provinces) {
        if (this.provinces != null) {
            this.provinces.forEach(i -> i.setAdministrativeUnits(null));
        }
        if (provinces != null) {
            provinces.forEach(i -> i.setAdministrativeUnits(this));
        }
        this.provinces = provinces;
    }

    public AdministrativeUnits provinces(Set<Provinces> provinces) {
        this.setProvinces(provinces);
        return this;
    }

    public AdministrativeUnits addProvinces(Provinces provinces) {
        this.provinces.add(provinces);
        provinces.setAdministrativeUnits(this);
        return this;
    }

    public AdministrativeUnits removeProvinces(Provinces provinces) {
        this.provinces.remove(provinces);
        provinces.setAdministrativeUnits(null);
        return this;
    }

    public Set<Districts> getDistricts() {
        return this.districts;
    }

    public void setDistricts(Set<Districts> districts) {
        if (this.districts != null) {
            this.districts.forEach(i -> i.setAdministrativeUnits(null));
        }
        if (districts != null) {
            districts.forEach(i -> i.setAdministrativeUnits(this));
        }
        this.districts = districts;
    }

    public AdministrativeUnits districts(Set<Districts> districts) {
        this.setDistricts(districts);
        return this;
    }

    public AdministrativeUnits addDistricts(Districts districts) {
        this.districts.add(districts);
        districts.setAdministrativeUnits(this);
        return this;
    }

    public AdministrativeUnits removeDistricts(Districts districts) {
        this.districts.remove(districts);
        districts.setAdministrativeUnits(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdministrativeUnits)) {
            return false;
        }
        return id != null && id.equals(((AdministrativeUnits) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministrativeUnits{" +
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
