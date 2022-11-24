import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdministrativeRegions, getAdministrativeRegionsIdentifier } from '../administrative-regions.model';

export type EntityResponseType = HttpResponse<IAdministrativeRegions>;
export type EntityArrayResponseType = HttpResponse<IAdministrativeRegions[]>;

@Injectable({ providedIn: 'root' })
export class AdministrativeRegionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/administrative-regions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(administrativeRegions: IAdministrativeRegions): Observable<EntityResponseType> {
    return this.http.post<IAdministrativeRegions>(this.resourceUrl, administrativeRegions, { observe: 'response' });
  }

  update(administrativeRegions: IAdministrativeRegions): Observable<EntityResponseType> {
    return this.http.put<IAdministrativeRegions>(
      `${this.resourceUrl}/${getAdministrativeRegionsIdentifier(administrativeRegions) as number}`,
      administrativeRegions,
      { observe: 'response' }
    );
  }

  partialUpdate(administrativeRegions: IAdministrativeRegions): Observable<EntityResponseType> {
    return this.http.patch<IAdministrativeRegions>(
      `${this.resourceUrl}/${getAdministrativeRegionsIdentifier(administrativeRegions) as number}`,
      administrativeRegions,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdministrativeRegions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdministrativeRegions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAdministrativeRegionsToCollectionIfMissing(
    administrativeRegionsCollection: IAdministrativeRegions[],
    ...administrativeRegionsToCheck: (IAdministrativeRegions | null | undefined)[]
  ): IAdministrativeRegions[] {
    const administrativeRegions: IAdministrativeRegions[] = administrativeRegionsToCheck.filter(isPresent);
    if (administrativeRegions.length > 0) {
      const administrativeRegionsCollectionIdentifiers = administrativeRegionsCollection.map(
        administrativeRegionsItem => getAdministrativeRegionsIdentifier(administrativeRegionsItem)!
      );
      const administrativeRegionsToAdd = administrativeRegions.filter(administrativeRegionsItem => {
        const administrativeRegionsIdentifier = getAdministrativeRegionsIdentifier(administrativeRegionsItem);
        if (
          administrativeRegionsIdentifier == null ||
          administrativeRegionsCollectionIdentifiers.includes(administrativeRegionsIdentifier)
        ) {
          return false;
        }
        administrativeRegionsCollectionIdentifiers.push(administrativeRegionsIdentifier);
        return true;
      });
      return [...administrativeRegionsToAdd, ...administrativeRegionsCollection];
    }
    return administrativeRegionsCollection;
  }
}
