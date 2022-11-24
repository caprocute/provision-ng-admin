import dayjs from 'dayjs/esm';

export interface ISysOrganization {
  id?: number;
  parentId?: number | null;
  sysOrgCode?: string | null;
  sysOrgName?: string | null;
  sysOrgPath?: string | null;
  sysOrgFullPath?: string | null;
  description?: string | null;
  address?: string | null;
  type?: number | null;
  tenantId?: number | null;
  sysOrgType?: number | null;
  isActive?: boolean | null;
  status?: boolean | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class SysOrganization implements ISysOrganization {
  constructor(
    public id?: number,
    public parentId?: number | null,
    public sysOrgCode?: string | null,
    public sysOrgName?: string | null,
    public sysOrgPath?: string | null,
    public sysOrgFullPath?: string | null,
    public description?: string | null,
    public address?: string | null,
    public type?: number | null,
    public tenantId?: number | null,
    public sysOrgType?: number | null,
    public isActive?: boolean | null,
    public status?: boolean | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {
    this.isActive = this.isActive ?? false;
    this.status = this.status ?? false;
  }
}

export function getSysOrganizationIdentifier(sysOrganization: ISysOrganization): number | undefined {
  return sysOrganization.id;
}
