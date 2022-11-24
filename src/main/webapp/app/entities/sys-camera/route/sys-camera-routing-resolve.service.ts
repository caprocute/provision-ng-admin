import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISysCamera, SysCamera } from '../sys-camera.model';
import { SysCameraService } from '../service/sys-camera.service';

@Injectable({ providedIn: 'root' })
export class SysCameraRoutingResolveService implements Resolve<ISysCamera> {
  constructor(protected service: SysCameraService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISysCamera> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sysCamera: HttpResponse<SysCamera>) => {
          if (sysCamera.body) {
            return of(sysCamera.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SysCamera());
  }
}
