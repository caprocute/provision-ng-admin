import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWards, Wards } from '../wards.model';
import { WardsService } from '../service/wards.service';

@Injectable({ providedIn: 'root' })
export class WardsRoutingResolveService implements Resolve<IWards> {
  constructor(protected service: WardsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWards> | Observable<never> {
    const id = route.params['code'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((wards: HttpResponse<Wards>) => {
          if (wards.body) {
            return of(wards.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Wards());
  }
}
