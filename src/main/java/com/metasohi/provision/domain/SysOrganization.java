package com.metasohi.provision.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SysOrganization.
 */
@Entity
@Table(name = "sys_organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "sys_org_code")
    private String sysOrgCode;

    @Column(name = "sys_org_name")
    private String sysOrgName;

    @Column(name = "sys_org_path")
    private String sysOrgPath;

    @Column(name = "sys_org_full_path")
    private String sysOrgFullPath;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "type")
    private Long type;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "sys_org_type")
    private Long sysOrgType;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "status")
    private Boolean status;

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

    public SysOrganization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public SysOrganization parentId(Long parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getSysOrgCode() {
        return this.sysOrgCode;
    }

    public SysOrganization sysOrgCode(String sysOrgCode) {
        this.setSysOrgCode(sysOrgCode);
        return this;
    }

    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    public String getSysOrgName() {
        return this.sysOrgName;
    }

    public SysOrganization sysOrgName(String sysOrgName) {
        this.setSysOrgName(sysOrgName);
        return this;
    }

    public void setSysOrgName(String sysOrgName) {
        this.sysOrgName = sysOrgName;
    }

    public String getSysOrgPath() {
        return this.sysOrgPath;
    }

    public SysOrganization sysOrgPath(String sysOrgPath) {
        this.setSysOrgPath(sysOrgPath);
        return this;
    }

    public void setSysOrgPath(String sysOrgPath) {
        this.sysOrgPath = sysOrgPath;
    }

    public String getSysOrgFullPath() {
        return this.sysOrgFullPath;
    }

    public SysOrganization sysOrgFullPath(String sysOrgFullPath) {
        this.setSysOrgFullPath(sysOrgFullPath);
        return this;
    }

    public void setSysOrgFullPath(String sysOrgFullPath) {
        this.sysOrgFullPath = sysOrgFullPath;
    }

    public String getDescription() {
        return this.description;
    }

    public SysOrganization description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return this.address;
    }

    public SysOrganization address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getType() {
        return this.type;
    }

    public SysOrganization type(Long type) {
        this.setType(type);
        return this;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getTenantId() {
        return this.tenantId;
    }

    public SysOrganization tenantId(Long tenantId) {
        this.setTenantId(tenantId);
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getSysOrgType() {
        return this.sysOrgType;
    }

    public SysOrganization sysOrgType(Long sysOrgType) {
        this.setSysOrgType(sysOrgType);
        return this;
    }

    public void setSysOrgType(Long sysOrgType) {
        this.sysOrgType = sysOrgType;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public SysOrganization isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public SysOrganization status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public SysOrganization createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public SysOrganization createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public SysOrganization lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public SysOrganization lastModifiedDate(Instant lastModifiedDate) {
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
        if (!(o instanceof SysOrganization)) {
            return false;
        }
        return id != null && id.equals(((SysOrganization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysOrganization{" +
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
