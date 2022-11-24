import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISysArea, getSysAreaIdentifier } from '../sys-area.model';

export type EntityResponseType = HttpResponse<ISysArea>;
export type EntityArrayResponseType = HttpResponse<ISysArea[]>;

@Injectable({ providedIn: 'root' })
export class SysAreaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sys-areas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sysArea: ISysArea): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysArea);
    return this.http
      .post<ISysArea>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sysArea: ISysArea): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysArea);
    return this.http
      .put<ISysArea>(`${this.resourceUrl}/${getSysAreaIdentifier(sysArea) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sysArea: ISysArea): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysArea);
    return this.http
      .patch<ISysArea>(`${this.resourceUrl}/${getSysAreaIdentifier(sysArea) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISysArea>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISysArea[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSysAreaToCollectionIfMissing(sysAreaCollection: ISysArea[], ...sysAreasToCheck: (ISysArea | null | undefined)[]): ISysArea[] {
    const sysAreas: ISysArea[] = sysAreasToCheck.filter(isPresent);
    if (sysAreas.length > 0) {
      const sysAreaCollectionIdentifiers = sysAreaCollection.map(sysAreaItem => getSysAreaIdentifier(sysAreaItem)!);
      const sysAreasToAdd = sysAreas.filter(sysAreaItem => {
        const sysAreaIdentifier = getSysAreaIdentifier(sysAreaItem);
        if (sysAreaIdentifier == null || sysAreaCollectionIdentifiers.includes(sysAreaIdentifier)) {
          return false;
        }
        sysAreaCollectionIdentifiers.push(sysAreaIdentifier);
        return true;
      });
      return [...sysAreasToAdd, ...sysAreaCollection];
    }
    return sysAreaCollection;
  }

  protected convertDateFromClient(sysArea: ISysArea): ISysArea {
    return Object.assign({}, sysArea, {
      createdDate: sysArea.createdDate?.isValid() ? sysArea.createdDate.toJSON() : undefined,
      lastModifiedDate: sysArea.lastModifiedDate?.isValid() ? sysArea.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((sysArea: ISysArea) => {
        sysArea.createdDate = sysArea.createdDate ? dayjs(sysArea.createdDate) : undefined;
        sysArea.lastModifiedDate = sysArea.lastModifiedDate ? dayjs(sysArea.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
