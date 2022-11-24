import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISysOrganization, SysOrganization } from '../sys-organization.model';
import { SysOrganizationService } from '../service/sys-organization.service';

@Injectable({ providedIn: 'root' })
export class SysOrganizationRoutingResolveService implements Resolve<ISysOrganization> {
  constructor(protected service: SysOrganizationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISysOrganization> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sysOrganization: HttpResponse<SysOrganization>) => {
          if (sysOrganization.body) {
            return of(sysOrganization.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SysOrganization());
  }
}
