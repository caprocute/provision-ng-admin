import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDistricts, getDistrictsIdentifier } from '../districts.model';

export type EntityResponseType = HttpResponse<IDistricts>;
export type EntityArrayResponseType = HttpResponse<IDistricts[]>;

@Injectable({ providedIn: 'root' })
export class DistrictsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/districts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(districts: IDistricts): Observable<EntityResponseType> {
    return this.http.post<IDistricts>(this.resourceUrl, districts, { observe: 'response' });
  }

  update(districts: IDistricts): Observable<EntityResponseType> {
    return this.http.put<IDistricts>(`${this.resourceUrl}/${getDistrictsIdentifier(districts) as string}`, districts, {
      observe: 'response',
    });
  }

  partialUpdate(districts: IDistricts): Observable<EntityResponseType> {
    return this.http.patch<IDistricts>(`${this.resourceUrl}/${getDistrictsIdentifier(districts) as string}`, districts, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IDistricts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDistricts[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDistrictsToCollectionIfMissing(
    districtsCollection: IDistricts[],
    ...districtsToCheck: (IDistricts | null | undefined)[]
  ): IDistricts[] {
    const districts: IDistricts[] = districtsToCheck.filter(isPresent);
    if (districts.length > 0) {
      const districtsCollectionIdentifiers = districtsCollection.map(districtsItem => getDistrictsIdentifier(districtsItem)!);
      const districtsToAdd = districts.filter(districtsItem => {
        const districtsIdentifier = getDistrictsIdentifier(districtsItem);
        if (districtsIdentifier == null || districtsCollectionIdentifiers.includes(districtsIdentifier)) {
          return false;
        }
        districtsCollectionIdentifiers.push(districtsIdentifier);
        return true;
      });
      return [...districtsToAdd, ...districtsCollection];
    }
    return districtsCollection;
  }
}
