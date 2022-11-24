import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISysOrganizationArea, SysOrganizationArea } from '../sys-organization-area.model';
import { SysOrganizationAreaService } from '../service/sys-organization-area.service';

@Injectable({ providedIn: 'root' })
export class SysOrganizationAreaRoutingResolveService implements Resolve<ISysOrganizationArea> {
  constructor(protected service: SysOrganizationAreaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISysOrganizationArea> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sysOrganizationArea: HttpResponse<SysOrganizationArea>) => {
          if (sysOrganizationArea.body) {
            return of(sysOrganizationArea.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SysOrganizationArea());
  }
}
