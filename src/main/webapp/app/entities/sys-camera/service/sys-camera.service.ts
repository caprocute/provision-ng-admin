import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISysCamera, getSysCameraIdentifier } from '../sys-camera.model';

export type EntityResponseType = HttpResponse<ISysCamera>;
export type EntityArrayResponseType = HttpResponse<ISysCamera[]>;

@Injectable({ providedIn: 'root' })
export class SysCameraService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sys-cameras');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sysCamera: ISysCamera): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysCamera);
    return this.http
      .post<ISysCamera>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sysCamera: ISysCamera): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysCamera);
    return this.http
      .put<ISysCamera>(`${this.resourceUrl}/${getSysCameraIdentifier(sysCamera) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sysCamera: ISysCamera): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysCamera);
    return this.http
      .patch<ISysCamera>(`${this.resourceUrl}/${getSysCameraIdentifier(sysCamera) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISysCamera>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISysCamera[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSysCameraToCollectionIfMissing(
    sysCameraCollection: ISysCamera[],
    ...sysCamerasToCheck: (ISysCamera | null | undefined)[]
  ): ISysCamera[] {
    const sysCameras: ISysCamera[] = sysCamerasToCheck.filter(isPresent);
    if (sysCameras.length > 0) {
      const sysCameraCollectionIdentifiers = sysCameraCollection.map(sysCameraItem => getSysCameraIdentifier(sysCameraItem)!);
      const sysCamerasToAdd = sysCameras.filter(sysCameraItem => {
        const sysCameraIdentifier = getSysCameraIdentifier(sysCameraItem);
        if (sysCameraIdentifier == null || sysCameraCollectionIdentifiers.includes(sysCameraIdentifier)) {
          return false;
        }
        sysCameraCollectionIdentifiers.push(sysCameraIdentifier);
        return true;
      });
      return [...sysCamerasToAdd, ...sysCameraCollection];
    }
    return sysCameraCollection;
  }

  protected convertDateFromClient(sysCamera: ISysCamera): ISysCamera {
    return Object.assign({}, sysCamera, {
      createdDate: sysCamera.createdDate?.isValid() ? sysCamera.createdDate.toJSON() : undefined,
      lastModifiedDate: sysCamera.lastModifiedDate?.isValid() ? sysCamera.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((sysCamera: ISysCamera) => {
        sysCamera.createdDate = sysCamera.createdDate ? dayjs(sysCamera.createdDate) : undefined;
        sysCamera.lastModifiedDate = sysCamera.lastModifiedDate ? dayjs(sysCamera.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
