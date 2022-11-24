import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WardsComponent } from '../list/wards.component';
import { WardsDetailComponent } from '../detail/wards-detail.component';
import { WardsUpdateComponent } from '../update/wards-update.component';
import { WardsRoutingResolveService } from './wards-routing-resolve.service';

const wardsRoute: Routes = [
  {
    path: '',
    component: WardsComponent,
    data: {
      defaultSort: 'code,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':code/view',
    component: WardsDetailComponent,
    resolve: {
      wards: WardsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WardsUpdateComponent,
    resolve: {
      wards: WardsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':code/edit',
    component: WardsUpdateComponent,
    resolve: {
      wards: WardsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(wardsRoute)],
  exports: [RouterModule],
})
export class WardsRoutingModule {}
