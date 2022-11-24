import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdministrativeUnitsComponent } from '../list/administrative-units.component';
import { AdministrativeUnitsDetailComponent } from '../detail/administrative-units-detail.component';
import { AdministrativeUnitsUpdateComponent } from '../update/administrative-units-update.component';
import { AdministrativeUnitsRoutingResolveService } from './administrative-units-routing-resolve.service';

const administrativeUnitsRoute: Routes = [
  {
    path: '',
    component: AdministrativeUnitsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdministrativeUnitsDetailComponent,
    resolve: {
      administrativeUnits: AdministrativeUnitsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdministrativeUnitsUpdateComponent,
    resolve: {
      administrativeUnits: AdministrativeUnitsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdministrativeUnitsUpdateComponent,
    resolve: {
      administrativeUnits: AdministrativeUnitsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(administrativeUnitsRoute)],
  exports: [RouterModule],
})
export class AdministrativeUnitsRoutingModule {}
