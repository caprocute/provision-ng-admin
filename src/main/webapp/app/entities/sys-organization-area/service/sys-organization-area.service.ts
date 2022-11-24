import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISysOrganizationArea, getSysOrganizationAreaIdentifier } from '../sys-organization-area.model';

export type EntityResponseType = HttpResponse<ISysOrganizationArea>;
export type EntityArrayResponseType = HttpResponse<ISysOrganizationArea[]>;

@Injectable({ providedIn: 'root' })
export class SysOrganizationAreaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sys-organization-areas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sysOrganizationArea: ISysOrganizationArea): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysOrganizationArea);
    return this.http
      .post<ISysOrganizationArea>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sysOrganizationArea: ISysOrganizationArea): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysOrganizationArea);
    return this.http
      .put<ISysOrganizationArea>(`${this.resourceUrl}/${getSysOrganizationAreaIdentifier(sysOrganizationArea) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sysOrganizationArea: ISysOrganizationArea): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysOrganizationArea);
    return this.http
      .patch<ISysOrganizationArea>(`${this.resourceUrl}/${getSysOrganizationAreaIdentifier(sysOrganizationArea) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISysOrganizationArea>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISysOrganizationArea[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSysOrganizationAreaToCollectionIfMissing(
    sysOrganizationAreaCollection: ISysOrganizationArea[],
    ...sysOrganizationAreasToCheck: (ISysOrganizationArea | null | undefined)[]
  ): ISysOrganizationArea[] {
    const sysOrganizationAreas: ISysOrganizationArea[] = sysOrganizationAreasToCheck.filter(isPresent);
    if (sysOrganizationAreas.length > 0) {
      const sysOrganizationAreaCollectionIdentifiers = sysOrganizationAreaCollection.map(
        sysOrganizationAreaItem => getSysOrganizationAreaIdentifier(sysOrganizationAreaItem)!
      );
      const sysOrganizationAreasToAdd = sysOrganizationAreas.filter(sysOrganizationAreaItem => {
        const sysOrganizationAreaIdentifier = getSysOrganizationAreaIdentifier(sysOrganizationAreaItem);
        if (sysOrganizationAreaIdentifier == null || sysOrganizationAreaCollectionIdentifiers.includes(sysOrganizationAreaIdentifier)) {
          return false;
        }
        sysOrganizationAreaCollectionIdentifiers.push(sysOrganizationAreaIdentifier);
        return true;
      });
      return [...sysOrganizationAreasToAdd, ...sysOrganizationAreaCollection];
    }
    return sysOrganizationAreaCollection;
  }

  protected convertDateFromClient(sysOrganizationArea: ISysOrganizationArea): ISysOrganizationArea {
    return Object.assign({}, sysOrganizationArea, {
      createdDate: sysOrganizationArea.createdDate?.isValid() ? sysOrganizationArea.createdDate.toJSON() : undefined,
      lastModifiedDate: sysOrganizationArea.lastModifiedDate?.isValid() ? sysOrganizationArea.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((sysOrganizationArea: ISysOrganizationArea) => {
        sysOrganizationArea.createdDate = sysOrganizationArea.createdDate ? dayjs(sysOrganizationArea.createdDate) : undefined;
        sysOrganizationArea.lastModifiedDate = sysOrganizationArea.lastModifiedDate
          ? dayjs(sysOrganizationArea.lastModifiedDate)
          : undefined;
      });
    }
    return res;
  }
}
