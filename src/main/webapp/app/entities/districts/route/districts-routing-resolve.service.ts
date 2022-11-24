import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDistricts, Districts } from '../districts.model';
import { DistrictsService } from '../service/districts.service';

@Injectable({ providedIn: 'root' })
export class DistrictsRoutingResolveService implements Resolve<IDistricts> {
  constructor(protected service: DistrictsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDistricts> | Observable<never> {
    const id = route.params['code'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((districts: HttpResponse<Districts>) => {
          if (districts.body) {
            return of(districts.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Districts());
  }
}
