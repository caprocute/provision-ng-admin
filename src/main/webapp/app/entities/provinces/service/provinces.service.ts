import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProvinces, getProvincesIdentifier } from '../provinces.model';

export type EntityResponseType = HttpResponse<IProvinces>;
export type EntityArrayResponseType = HttpResponse<IProvinces[]>;

@Injectable({ providedIn: 'root' })
export class ProvincesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/provinces');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(provinces: IProvinces): Observable<EntityResponseType> {
    return this.http.post<IProvinces>(this.resourceUrl, provinces, { observe: 'response' });
  }

  update(provinces: IProvinces): Observable<EntityResponseType> {
    return this.http.put<IProvinces>(`${this.resourceUrl}/${getProvincesIdentifier(provinces) as string}`, provinces, {
      observe: 'response',
    });
  }

  partialUpdate(provinces: IProvinces): Observable<EntityResponseType> {
    return this.http.patch<IProvinces>(`${this.resourceUrl}/${getProvincesIdentifier(provinces) as string}`, provinces, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IProvinces>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProvinces[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProvincesToCollectionIfMissing(
    provincesCollection: IProvinces[],
    ...provincesToCheck: (IProvinces | null | undefined)[]
  ): IProvinces[] {
    const provinces: IProvinces[] = provincesToCheck.filter(isPresent);
    if (provinces.length > 0) {
      const provincesCollectionIdentifiers = provincesCollection.map(provincesItem => getProvincesIdentifier(provincesItem)!);
      const provincesToAdd = provinces.filter(provincesItem => {
        const provincesIdentifier = getProvincesIdentifier(provincesItem);
        if (provincesIdentifier == null || provincesCollectionIdentifiers.includes(provincesIdentifier)) {
          return false;
        }
        provincesCollectionIdentifiers.push(provincesIdentifier);
        return true;
      });
      return [...provincesToAdd, ...provincesCollection];
    }
    return provincesCollection;
  }
}
