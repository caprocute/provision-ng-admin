import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProvincesComponent } from '../list/provinces.component';
import { ProvincesDetailComponent } from '../detail/provinces-detail.component';
import { ProvincesUpdateComponent } from '../update/provinces-update.component';
import { ProvincesRoutingResolveService } from './provinces-routing-resolve.service';

const provincesRoute: Routes = [
  {
    path: '',
    component: ProvincesComponent,
    data: {
      defaultSort: 'code,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':code/view',
    component: ProvincesDetailComponent,
    resolve: {
      provinces: ProvincesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProvincesUpdateComponent,
    resolve: {
      provinces: ProvincesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':code/edit',
    component: ProvincesUpdateComponent,
    resolve: {
      provinces: ProvincesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(provincesRoute)],
  exports: [RouterModule],
})
export class ProvincesRoutingModule {}
