import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISysOrganization, getSysOrganizationIdentifier } from '../sys-organization.model';

export type EntityResponseType = HttpResponse<ISysOrganization>;
export type EntityArrayResponseType = HttpResponse<ISysOrganization[]>;

@Injectable({ providedIn: 'root' })
export class SysOrganizationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sys-organizations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sysOrganization: ISysOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysOrganization);
    return this.http
      .post<ISysOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sysOrganization: ISysOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysOrganization);
    return this.http
      .put<ISysOrganization>(`${this.resourceUrl}/${getSysOrganizationIdentifier(sysOrganization) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sysOrganization: ISysOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysOrganization);
    return this.http
      .patch<ISysOrganization>(`${this.resourceUrl}/${getSysOrganizationIdentifier(sysOrganization) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISysOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISysOrganization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSysOrganizationToCollectionIfMissing(
    sysOrganizationCollection: ISysOrganization[],
    ...sysOrganizationsToCheck: (ISysOrganization | null | undefined)[]
  ): ISysOrganization[] {
    const sysOrganizations: ISysOrganization[] = sysOrganizationsToCheck.filter(isPresent);
    if (sysOrganizations.length > 0) {
      const sysOrganizationCollectionIdentifiers = sysOrganizationCollection.map(
        sysOrganizationItem => getSysOrganizationIdentifier(sysOrganizationItem)!
      );
      const sysOrganizationsToAdd = sysOrganizations.filter(sysOrganizationItem => {
        const sysOrganizationIdentifier = getSysOrganizationIdentifier(sysOrganizationItem);
        if (sysOrganizationIdentifier == null || sysOrganizationCollectionIdentifiers.includes(sysOrganizationIdentifier)) {
          return false;
        }
        sysOrganizationCollectionIdentifiers.push(sysOrganizationIdentifier);
        return true;
      });
      return [...sysOrganizationsToAdd, ...sysOrganizationCollection];
    }
    return sysOrganizationCollection;
  }

  protected convertDateFromClient(sysOrganization: ISysOrganization): ISysOrganization {
    return Object.assign({}, sysOrganization, {
      createdDate: sysOrganization.createdDate?.isValid() ? sysOrganization.createdDate.toJSON() : undefined,
      lastModifiedDate: sysOrganization.lastModifiedDate?.isValid() ? sysOrganization.lastModifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? dayjs(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sysOrganization: ISysOrganization) => {
        sysOrganization.createdDate = sysOrganization.createdDate ? dayjs(sysOrganization.createdDate) : undefined;
        sysOrganization.lastModifiedDate = sysOrganization.lastModifiedDate ? dayjs(sysOrganization.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
