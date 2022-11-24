import dayjs from 'dayjs/esm';

export interface ISysCamera {
  id?: number;
  camCode?: string | null;
  camName?: string | null;
  areaId?: number | null;
  branch?: string | null;
  origin?: string | null;
  description?: string | null;
  isActive?: boolean | null;
  status?: boolean | null;
  locationLat?: number | null;
  locationLan?: number | null;
  urlSFTP?: string | null;
  urlLiveStream?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class SysCamera implements ISysCamera {
  constructor(
    public id?: number,
    public camCode?: string | null,
    public camName?: string | null,
    public areaId?: number | null,
    public branch?: string | null,
    public origin?: string | null,
    public description?: string | null,
    public isActive?: boolean | null,
    public status?: boolean | null,
    public locationLat?: number | null,
    public locationLan?: number | null,
    public urlSFTP?: string | null,
    public urlLiveStream?: string | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {
    this.isActive = this.isActive ?? false;
    this.status = this.status ?? false;
  }
}

export function getSysCameraIdentifier(sysCamera: ISysCamera): number | undefined {
  return sysCamera.id;
}
