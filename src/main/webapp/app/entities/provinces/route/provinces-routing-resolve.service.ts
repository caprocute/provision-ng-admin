import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProvinces, Provinces } from '../provinces.model';
import { ProvincesService } from '../service/provinces.service';

@Injectable({ providedIn: 'root' })
export class ProvincesRoutingResolveService implements Resolve<IProvinces> {
  constructor(protected service: ProvincesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProvinces> | Observable<never> {
    const id = route.params['code'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((provinces: HttpResponse<Provinces>) => {
          if (provinces.body) {
            return of(provinces.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Provinces());
  }
}
