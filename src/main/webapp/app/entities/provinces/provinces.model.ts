import { IDistricts } from 'app/entities/districts/districts.model';
import { IAdministrativeRegions } from 'app/entities/administrative-regions/administrative-regions.model';
import { IAdministrativeUnits } from 'app/entities/administrative-units/administrative-units.model';

export interface IProvinces {
  code?: string;
  name?: string | null;
  nameEn?: string | null;
  fullName?: string | null;
  fullNameEn?: string | null;
  codeName?: string | null;
  administrativeUnitId?: number | null;
  administrativeRegionId?: number | null;
  districts?: IDistricts[] | null;
  administrativeRegions?: IAdministrativeRegions | null;
  administrativeUnits?: IAdministrativeUnits | null;
}

export class Provinces implements IProvinces {
  constructor(
    public code?: string,
    public name?: string | null,
    public nameEn?: string | null,
    public fullName?: string | null,
    public fullNameEn?: string | null,
    public codeName?: string | null,
    public administrativeUnitId?: number | null,
    public administrativeRegionId?: number | null,
    public districts?: IDistricts[] | null,
    public administrativeRegions?: IAdministrativeRegions | null,
    public administrativeUnits?: IAdministrativeUnits | null
  ) {}
}

export function getProvincesIdentifier(provinces: IProvinces): string | undefined {
  return provinces.code;
}
