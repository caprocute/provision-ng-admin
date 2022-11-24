import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SysAreaComponent } from '../list/sys-area.component';
import { SysAreaDetailComponent } from '../detail/sys-area-detail.component';
import { SysAreaUpdateComponent } from '../update/sys-area-update.component';
import { SysAreaRoutingResolveService } from './sys-area-routing-resolve.service';

const sysAreaRoute: Routes = [
  {
    path: '',
    component: SysAreaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SysAreaDetailComponent,
    resolve: {
      sysArea: SysAreaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SysAreaUpdateComponent,
    resolve: {
      sysArea: SysAreaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SysAreaUpdateComponent,
    resolve: {
      sysArea: SysAreaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sysAreaRoute)],
  exports: [RouterModule],
})
export class SysAreaRoutingModule {}
