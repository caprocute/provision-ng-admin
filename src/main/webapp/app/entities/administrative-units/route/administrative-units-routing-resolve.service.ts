import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdministrativeUnits, AdministrativeUnits } from '../administrative-units.model';
import { AdministrativeUnitsService } from '../service/administrative-units.service';

@Injectable({ providedIn: 'root' })
export class AdministrativeUnitsRoutingResolveService implements Resolve<IAdministrativeUnits> {
  constructor(protected service: AdministrativeUnitsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdministrativeUnits> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((administrativeUnits: HttpResponse<AdministrativeUnits>) => {
          if (administrativeUnits.body) {
            return of(administrativeUnits.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdministrativeUnits());
  }
}
