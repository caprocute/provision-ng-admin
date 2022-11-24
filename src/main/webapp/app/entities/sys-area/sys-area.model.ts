import dayjs from 'dayjs/esm';

export interface ISysArea {
  id?: number;
  areaCode?: string | null;
  areaName?: string | null;
  provinceCode?: string | null;
  districtCode?: string | null;
  wardCode?: string | null;
  description?: string | null;
  isActive?: boolean | null;
  status?: boolean | null;
  locationLat?: number | null;
  locationLan?: number | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class SysArea implements ISysArea {
  constructor(
    public id?: number,
    public areaCode?: string | null,
    public areaName?: string | null,
    public provinceCode?: string | null,
    public districtCode?: string | null,
    public wardCode?: string | null,
    public description?: string | null,
    public isActive?: boolean | null,
    public status?: boolean | null,
    public locationLat?: number | null,
    public locationLan?: number | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {
    this.isActive = this.isActive ?? false;
    this.status = this.status ?? false;
  }
}

export function getSysAreaIdentifier(sysArea: ISysArea): number | undefined {
  return sysArea.id;
}
