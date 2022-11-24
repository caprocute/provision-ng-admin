import dayjs from 'dayjs/esm';

export interface ISysOrganizationArea {
  id?: number;
  orgId?: number | null;
  areaId?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class SysOrganizationArea implements ISysOrganizationArea {
  constructor(
    public id?: number,
    public orgId?: number | null,
    public areaId?: string | null,
    public isActive?: boolean | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {
    this.isActive = this.isActive ?? false;
  }
}

export function getSysOrganizationAreaIdentifier(sysOrganizationArea: ISysOrganizationArea): number | undefined {
  return sysOrganizationArea.id;
}
