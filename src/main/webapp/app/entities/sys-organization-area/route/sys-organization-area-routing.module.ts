import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SysOrganizationAreaComponent } from '../list/sys-organization-area.component';
import { SysOrganizationAreaDetailComponent } from '../detail/sys-organization-area-detail.component';
import { SysOrganizationAreaUpdateComponent } from '../update/sys-organization-area-update.component';
import { SysOrganizationAreaRoutingResolveService } from './sys-organization-area-routing-resolve.service';

const sysOrganizationAreaRoute: Routes = [
  {
    path: '',
    component: SysOrganizationAreaComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SysOrganizationAreaDetailComponent,
    resolve: {
      sysOrganizationArea: SysOrganizationAreaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SysOrganizationAreaUpdateComponent,
    resolve: {
      sysOrganizationArea: SysOrganizationAreaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SysOrganizationAreaUpdateComponent,
    resolve: {
      sysOrganizationArea: SysOrganizationAreaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sysOrganizationAreaRoute)],
  exports: [RouterModule],
})
export class SysOrganizationAreaRoutingModule {}
