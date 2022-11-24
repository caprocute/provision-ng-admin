import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISysArea, SysArea } from '../sys-area.model';
import { SysAreaService } from '../service/sys-area.service';

@Injectable({ providedIn: 'root' })
export class SysAreaRoutingResolveService implements Resolve<ISysArea> {
  constructor(protected service: SysAreaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISysArea> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sysArea: HttpResponse<SysArea>) => {
          if (sysArea.body) {
            return of(sysArea.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SysArea());
  }
}
