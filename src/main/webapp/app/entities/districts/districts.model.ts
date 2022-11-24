import { IWards } from 'app/entities/wards/wards.model';
import { IProvinces } from 'app/entities/provinces/provinces.model';
import { IAdministrativeUnits } from 'app/entities/administrative-units/administrative-units.model';

export interface IDistricts {
  code?: string;
  name?: string | null;
  nameEn?: string | null;
  fullName?: string | null;
  fullNameEn?: string | null;
  codeName?: string | null;
  provinceCode?: string | null;
  administrativeUnitId?: number | null;
  wards?: IWards[] | null;
  provinces?: IProvinces | null;
  administrativeUnits?: IAdministrativeUnits | null;
}

export class Districts implements IDistricts {
  constructor(
    public code?: string,
    public name?: string | null,
    public nameEn?: string | null,
    public fullName?: string | null,
    public fullNameEn?: string | null,
    public codeName?: string | null,
    public provinceCode?: string | null,
    public administrativeUnitId?: number | null,
    public wards?: IWards[] | null,
    public provinces?: IProvinces | null,
    public administrativeUnits?: IAdministrativeUnits | null
  ) {}
}

export function getDistrictsIdentifier(districts: IDistricts): string | undefined {
  return districts.code;
}
