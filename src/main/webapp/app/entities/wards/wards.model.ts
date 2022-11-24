import { IAdministrativeRegions } from 'app/entities/administrative-regions/administrative-regions.model';
import { IDistricts } from 'app/entities/districts/districts.model';

export interface IWards {
  code?: string;
  name?: string | null;
  nameEn?: string | null;
  fullName?: string | null;
  fullNameEn?: string | null;
  codeName?: string | null;
  districtCode?: string | null;
  administrativeUnitId?: number | null;
  administrativeRegions?: IAdministrativeRegions | null;
  districts?: IDistricts | null;
}

export class Wards implements IWards {
  constructor(
    public code?: string,
    public name?: string | null,
    public nameEn?: string | null,
    public fullName?: string | null,
    public fullNameEn?: string | null,
    public codeName?: string | null,
    public districtCode?: string | null,
    public administrativeUnitId?: number | null,
    public administrativeRegions?: IAdministrativeRegions | null,
    public districts?: IDistricts | null
  ) {}
}

export function getWardsIdentifier(wards: IWards): string | undefined {
  return wards.code;
}
