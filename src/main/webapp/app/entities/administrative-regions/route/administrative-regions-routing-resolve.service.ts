import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdministrativeRegions, AdministrativeRegions } from '../administrative-regions.model';
import { AdministrativeRegionsService } from '../service/administrative-regions.service';

@Injectable({ providedIn: 'root' })
export class AdministrativeRegionsRoutingResolveService implements Resolve<IAdministrativeRegions> {
  constructor(protected service: AdministrativeRegionsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdministrativeRegions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((administrativeRegions: HttpResponse<AdministrativeRegions>) => {
          if (administrativeRegions.body) {
            return of(administrativeRegions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdministrativeRegions());
  }
}
