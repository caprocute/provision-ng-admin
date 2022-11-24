import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SysOrganizationComponent } from '../list/sys-organization.component';
import { SysOrganizationDetailComponent } from '../detail/sys-organization-detail.component';
import { SysOrganizationUpdateComponent } from '../update/sys-organization-update.component';
import { SysOrganizationRoutingResolveService } from './sys-organization-routing-resolve.service';

const sysOrganizationRoute: Routes = [
  {
    path: '',
    component: SysOrganizationComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SysOrganizationDetailComponent,
    resolve: {
      sysOrganization: SysOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SysOrganizationUpdateComponent,
    resolve: {
      sysOrganization: SysOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SysOrganizationUpdateComponent,
    resolve: {
      sysOrganization: SysOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sysOrganizationRoute)],
  exports: [RouterModule],
})
export class SysOrganizationRoutingModule {}
