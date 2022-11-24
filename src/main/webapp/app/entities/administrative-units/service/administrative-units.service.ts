import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdministrativeUnits, getAdministrativeUnitsIdentifier } from '../administrative-units.model';

export type EntityResponseType = HttpResponse<IAdministrativeUnits>;
export type EntityArrayResponseType = HttpResponse<IAdministrativeUnits[]>;

@Injectable({ providedIn: 'root' })
export class AdministrativeUnitsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/administrative-units');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(administrativeUnits: IAdministrativeUnits): Observable<EntityResponseType> {
    return this.http.post<IAdministrativeUnits>(this.resourceUrl, administrativeUnits, { observe: 'response' });
  }

  update(administrativeUnits: IAdministrativeUnits): Observable<EntityResponseType> {
    return this.http.put<IAdministrativeUnits>(
      `${this.resourceUrl}/${getAdministrativeUnitsIdentifier(administrativeUnits) as number}`,
      administrativeUnits,
      { observe: 'response' }
    );
  }

  partialUpdate(administrativeUnits: IAdministrativeUnits): Observable<EntityResponseType> {
    return this.http.patch<IAdministrativeUnits>(
      `${this.resourceUrl}/${getAdministrativeUnitsIdentifier(administrativeUnits) as number}`,
      administrativeUnits,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdministrativeUnits>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdministrativeUnits[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdministrativeUnitsToCollectionIfMissing(
    administrativeUnitsCollection: IAdministrativeUnits[],
    ...administrativeUnitsToCheck: (IAdministrativeUnits | null | undefined)[]
  ): IAdministrativeUnits[] {
    const administrativeUnits: IAdministrativeUnits[] = administrativeUnitsToCheck.filter(isPresent);
    if (administrativeUnits.length > 0) {
      const administrativeUnitsCollectionIdentifiers = administrativeUnitsCollection.map(
        administrativeUnitsItem => getAdministrativeUnitsIdentifier(administrativeUnitsItem)!
      );
      const administrativeUnitsToAdd = administrativeUnits.filter(administrativeUnitsItem => {
        const administrativeUnitsIdentifier = getAdministrativeUnitsIdentifier(administrativeUnitsItem);
        if (administrativeUnitsIdentifier == null || administrativeUnitsCollectionIdentifiers.includes(administrativeUnitsIdentifier)) {
          return false;
        }
        administrativeUnitsCollectionIdentifiers.push(administrativeUnitsIdentifier);
        return true;
      });
      return [...administrativeUnitsToAdd, ...administrativeUnitsCollection];
    }
    return administrativeUnitsCollection;
  }
}
