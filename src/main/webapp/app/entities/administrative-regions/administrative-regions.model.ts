import { IProvinces } from 'app/entities/provinces/provinces.model';
import { IWards } from 'app/entities/wards/wards.model';

export interface IAdministrativeRegions {
  id?: number;
  name?: string | null;
  nameEn?: string | null;
  codeName?: string | null;
  codeNameEn?: string | null;
  provinces?: IProvinces[] | null;
  wards?: IWards[] | null;
}

export class AdministrativeRegions implements IAdministrativeRegions {
  constructor(
    public id?: number,
    public name?: string | null,
    public nameEn?: string | null,
    public codeName?: string | null,
    public codeNameEn?: string | null,
    public provinces?: IProvinces[] | null,
    public wards?: IWards[] | null
  ) {}
}

export function getAdministrativeRegionsIdentifier(administrativeRegions: IAdministrativeRegions): number | undefined {
  return administrativeRegions.id;
}
