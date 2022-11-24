import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DistrictsComponent } from '../list/districts.component';
import { DistrictsDetailComponent } from '../detail/districts-detail.component';
import { DistrictsUpdateComponent } from '../update/districts-update.component';
import { DistrictsRoutingResolveService } from './districts-routing-resolve.service';

const districtsRoute: Routes = [
  {
    path: '',
    component: DistrictsComponent,
    data: {
      defaultSort: 'code,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':code/view',
    component: DistrictsDetailComponent,
    resolve: {
      districts: DistrictsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DistrictsUpdateComponent,
    resolve: {
      districts: DistrictsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':code/edit',
    component: DistrictsUpdateComponent,
    resolve: {
      districts: DistrictsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(districtsRoute)],
  exports: [RouterModule],
})
export class DistrictsRoutingModule {}
