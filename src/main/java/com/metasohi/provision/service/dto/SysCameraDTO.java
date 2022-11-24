package com.metasohi.provision.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.metasohi.provision.domain.SysCamera} entity.
 */
public class SysCameraDTO implements Serializable {

    private Long id;

    private String camCode;

    private String camName;

    private Long areaId;

    private String branch;

    private String origin;

    private String description;

    private Boolean isActive;

    private Boolean status;

    private Float locationLat;

    private Float locationLan;

    private String urlSFTP;

    private String urlLiveStream;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCamCode() {
        return camCode;
    }

    public void setCamCode(String camCode) {
        this.camCode = camCode;
    }

    public String getCamName() {
        return camName;
    }

    public void setCamName(String camName) {
        this.camName = camName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Float getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(Float locationLat) {
        this.locationLat = locationLat;
    }

    public Float getLocationLan() {
        return locationLan;
    }

    public void setLocationLan(Float locationLan) {
        this.locationLan = locationLan;
    }

    public String getUrlSFTP() {
        return urlSFTP;
    }

    public void setUrlSFTP(String urlSFTP) {
        this.urlSFTP = urlSFTP;
    }

    public String getUrlLiveStream() {
        return urlLiveStream;
    }

    public void setUrlLiveStream(String urlLiveStream) {
        this.urlLiveStream = urlLiveStream;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysCameraDTO)) {
            return false;
        }

        SysCameraDTO sysCameraDTO = (SysCameraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sysCameraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysCameraDTO{" +
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
