package com.metasohi.provision.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.metasohi.provision.domain.SysOrganization} entity.
 */
public class SysOrganizationDTO implements Serializable {

    private Long id;

    private Long parentId;

    private String sysOrgCode;

    private String sysOrgName;

    private String sysOrgPath;

    private String sysOrgFullPath;

    private String description;

    private String address;

    private Long type;

    private Long tenantId;

    private Long sysOrgType;

    private Boolean isActive;

    private Boolean status;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getSysOrgCode() {
        return sysOrgCode;
    }

    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    public String getSysOrgName() {
        return sysOrgName;
    }

    public void setSysOrgName(String sysOrgName) {
        this.sysOrgName = sysOrgName;
    }

    public String getSysOrgPath() {
        return sysOrgPath;
    }

    public void setSysOrgPath(String sysOrgPath) {
        this.sysOrgPath = sysOrgPath;
    }

    public String getSysOrgFullPath() {
        return sysOrgFullPath;
    }

    public void setSysOrgFullPath(String sysOrgFullPath) {
        this.sysOrgFullPath = sysOrgFullPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getSysOrgType() {
        return sysOrgType;
    }

    public void setSysOrgType(Long sysOrgType) {
        this.sysOrgType = sysOrgType;
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
        if (!(o instanceof SysOrganizationDTO)) {
            return false;
        }

        SysOrganizationDTO sysOrganizationDTO = (SysOrganizationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sysOrganizationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysOrganizationDTO{" +
            "id=" + getId() +
            ", parentId=" + getParentId() +
            ", sysOrgCode='" + getSysOrgCode() + "'" +
            ", sysOrgName='" + getSysOrgName() + "'" +
            ", sysOrgPath='" + getSysOrgPath() + "'" +
            ", sysOrgFullPath='" + getSysOrgFullPath() + "'" +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", type=" + getType() +
            ", tenantId=" + getTenantId() +
            ", sysOrgType=" + getSysOrgType() +
            ", isActive='" + getIsActive() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
