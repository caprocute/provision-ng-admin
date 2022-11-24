import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWards, getWardsIdentifier } from '../wards.model';

export type EntityResponseType = HttpResponse<IWards>;
export type EntityArrayResponseType = HttpResponse<IWards[]>;

@Injectable({ providedIn: 'root' })
export class WardsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/wards');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(wards: IWards): Observable<EntityResponseType> {
    return this.http.post<IWards>(this.resourceUrl, wards, { observe: 'response' });
  }

  update(wards: IWards): Observable<EntityResponseType> {
    return this.http.put<IWards>(`${this.resourceUrl}/${getWardsIdentifier(wards) as string}`, wards, { observe: 'response' });
  }

  partialUpdate(wards: IWards): Observable<EntityResponseType> {
    return this.http.patch<IWards>(`${this.resourceUrl}/${getWardsIdentifier(wards) as string}`, wards, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IWards>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWards[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addWardsToCollectionIfMissing(wardsCollection: IWards[], ...wardsToCheck: (IWards | null | undefined)[]): IWards[] {
    const wards: IWards[] = wardsToCheck.filter(isPresent);
    if (wards.length > 0) {
      const wardsCollectionIdentifiers = wardsCollection.map(wardsItem => getWardsIdentifier(wardsItem)!);
      const wardsToAdd = wards.filter(wardsItem => {
        const wardsIdentifier = getWardsIdentifier(wardsItem);
        if (wardsIdentifier == null || wardsCollectionIdentifiers.includes(wardsIdentifier)) {
          return false;
        }
        wardsCollectionIdentifiers.push(wardsIdentifier);
        return true;
      });
      return [...wardsToAdd, ...wardsCollection];
    }
    return wardsCollection;
  }
}
