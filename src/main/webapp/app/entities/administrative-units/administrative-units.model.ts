import { IProvinces } from 'app/entities/provinces/provinces.model';
import { IDistricts } from 'app/entities/districts/districts.model';

export interface IAdministrativeUnits {
  id?: number;
  fullName?: string | null;
  fullNameEn?: string | null;
  shortName?: string | null;
  shortNameEn?: string | null;
  codeName?: string | null;
  codeNameEn?: string | null;
  provinces?: IProvinces[] | null;
  districts?: IDistricts[] | null;
}

export class AdministrativeUnits implements IAdministrativeUnits {
  constructor(
    public id?: number,
    public fullName?: string | null,
    public fullNameEn?: string | null,
    public shortName?: string | null,
    public shortNameEn?: string | null,
    public codeName?: string | null,
    public codeNameEn?: string | null,
    public provinces?: IProvinces[] | null,
    public districts?: IDistricts[] | null
  ) {}
}

export function getAdministrativeUnitsIdentifier(administrativeUnits: IAdministrativeUnits): number | undefined {
  return administrativeUnits.id;
}
