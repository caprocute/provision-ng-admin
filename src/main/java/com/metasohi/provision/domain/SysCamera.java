package com.metasohi.provision.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SysCamera.
 */
@Entity
@Table(name = "sys_camera")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysCamera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cam_code")
    private String camCode;

    @Column(name = "cam_name")
    private String camName;

    @Column(name = "area_id")
    private Long areaId;

    @Column(name = "branch")
    private String branch;

    @Column(name = "origin")
    private String origin;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "location_lat")
    private Float locationLat;

    @Column(name = "location_lan")
    private Float locationLan;

    @Column(name = "url_sftp")
    private String urlSFTP;

    @Column(name = "url_live_stream")
    private String urlLiveStream;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SysCamera id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCamCode() {
        return this.camCode;
    }

    public SysCamera camCode(String camCode) {
        this.setCamCode(camCode);
        return this;
    }

    public void setCamCode(String camCode) {
        this.camCode = camCode;
    }

    public String getCamName() {
        return this.camName;
    }

    public SysCamera camName(String camName) {
        this.setCamName(camName);
        return this;
    }

    public void setCamName(String camName) {
        this.camName = camName;
    }

    public Long getAreaId() {
        return this.areaId;
    }

    public SysCamera areaId(Long areaId) {
        this.setAreaId(areaId);
        return this;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getBranch() {
        return this.branch;
    }

    public SysCamera branch(String branch) {
        this.setBranch(branch);
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getOrigin() {
        return this.origin;
    }

    public SysCamera origin(String origin) {
        this.setOrigin(origin);
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return this.description;
    }

    public SysCamera description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public SysCamera isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public SysCamera status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Float getLocationLat() {
        return this.locationLat;
    }

    public SysCamera locationLat(Float locationLat) {
        this.setLocationLat(locationLat);
        return this;
    }

    public void setLocationLat(Float locationLat) {
        this.locationLat = locationLat;
    }

    public Float getLocationLan() {
        return this.locationLan;
    }

    public SysCamera locationLan(Float locationLan) {
        this.setLocationLan(locationLan);
        return this;
    }

    public void setLocationLan(Float locationLan) {
        this.locationLan = locationLan;
    }

    public String getUrlSFTP() {
        return this.urlSFTP;
    }

    public SysCamera urlSFTP(String urlSFTP) {
        this.setUrlSFTP(urlSFTP);
        return this;
    }

    public void setUrlSFTP(String urlSFTP) {
        this.urlSFTP = urlSFTP;
    }

    public String getUrlLiveStream() {
        return this.urlLiveStream;
    }

    public SysCamera urlLiveStream(String urlLiveStream) {
        this.setUrlLiveStream(urlLiveStream);
        return this;
    }

    public void setUrlLiveStream(String urlLiveStream) {
        this.urlLiveStream = urlLiveStream;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public SysCamera createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public SysCamera createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public SysCamera lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public SysCamera lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysCamera)) {
            return false;
        }
        return id != null && id.equals(((SysCamera) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysCamera{" +
            "id=" + getId() +
            ", camCode='" + getCamCode() + "'" +
            ", camName='" + getCamName() + "'" +
            ", areaId=" + getAreaId() +
            ", branch='" + getBranch() + "'" +
            ", origin='" + getOrigin() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", status='" + getStatus() + "'" +
            ", locationLat=" + getLocationLat() +
            ", locationLan=" + getLocationLan() +
            ", urlSFTP='" + getUrlSFTP() + "'" +
            ", urlLiveStream='" + getUrlLiveStream() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
