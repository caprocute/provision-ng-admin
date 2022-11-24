import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdministrativeRegionsComponent } from '../list/administrative-regions.component';
import { AdministrativeRegionsDetailComponent } from '../detail/administrative-regions-detail.component';
import { AdministrativeRegionsUpdateComponent } from '../update/administrative-regions-update.component';
import { AdministrativeRegionsRoutingResolveService } from './administrative-regions-routing-resolve.service';

const administrativeRegionsRoute: Routes = [
  {
    path: '',
    component: AdministrativeRegionsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdministrativeRegionsDetailComponent,
    resolve: {
      administrativeRegions: AdministrativeRegionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdministrativeRegionsUpdateComponent,
    resolve: {
      administrativeRegions: AdministrativeRegionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdministrativeRegionsUpdateComponent,
    resolve: {
      administrativeRegions: AdministrativeRegionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(administrativeRegionsRoute)],
  exports: [RouterModule],
})
export class AdministrativeRegionsRoutingModule {}
