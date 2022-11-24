package com.metasohi.provision.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdministrativeRegions.
 */
@Entity
@Table(name = "administrative_regions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdministrativeRegions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "code_name_en")
    private String codeNameEn;

    @OneToMany(mappedBy = "administrativeRegions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "districts", "administrativeRegions", "administrativeUnits" }, allowSetters = true)
    private Set<Provinces> provinces = new HashSet<>();

    @OneToMany(mappedBy = "administrativeRegions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "administrativeRegions", "districts" }, allowSetters = true)
    private Set<Wards> wards = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdministrativeRegions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AdministrativeRegions name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public AdministrativeRegions nameEn(String nameEn) {
        this.setNameEn(nameEn);
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public AdministrativeRegions codeName(String codeName) {
        this.setCodeName(codeName);
        return this;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeNameEn() {
        return this.codeNameEn;
    }

    public AdministrativeRegions codeNameEn(String codeNameEn) {
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
            this.provinces.forEach(i -> i.setAdministrativeRegions(null));
        }
        if (provinces != null) {
            provinces.forEach(i -> i.setAdministrativeRegions(this));
        }
        this.provinces = provinces;
    }

    public AdministrativeRegions provinces(Set<Provinces> provinces) {
        this.setProvinces(provinces);
        return this;
    }

    public AdministrativeRegions addProvinces(Provinces provinces) {
        this.provinces.add(provinces);
        provinces.setAdministrativeRegions(this);
        return this;
    }

    public AdministrativeRegions removeProvinces(Provinces provinces) {
        this.provinces.remove(provinces);
        provinces.setAdministrativeRegions(null);
        return this;
    }

    public Set<Wards> getWards() {
        return this.wards;
    }

    public void setWards(Set<Wards> wards) {
        if (this.wards != null) {
            this.wards.forEach(i -> i.setAdministrativeRegions(null));
        }
        if (wards != null) {
            wards.forEach(i -> i.setAdministrativeRegions(this));
        }
        this.wards = wards;
    }

    public AdministrativeRegions wards(Set<Wards> wards) {
        this.setWards(wards);
        return this;
    }

    public AdministrativeRegions addWards(Wards wards) {
        this.wards.add(wards);
        wards.setAdministrativeRegions(this);
        return this;
    }

    public AdministrativeRegions removeWards(Wards wards) {
        this.wards.remove(wards);
        wards.setAdministrativeRegions(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdministrativeRegions)) {
            return false;
        }
        return id != null && id.equals(((AdministrativeRegions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministrativeRegions{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", codeName='" + getCodeName() + "'" +
            ", codeNameEn='" + getCodeNameEn() + "'" +
            "}";
    }
}
