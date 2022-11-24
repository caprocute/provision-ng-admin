import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SysCameraComponent } from '../list/sys-camera.component';
import { SysCameraDetailComponent } from '../detail/sys-camera-detail.component';
import { SysCameraUpdateComponent } from '../update/sys-camera-update.component';
import { SysCameraRoutingResolveService } from './sys-camera-routing-resolve.service';

const sysCameraRoute: Routes = [
  {
    path: '',
    component: SysCameraComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SysCameraDetailComponent,
    resolve: {
      sysCamera: SysCameraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SysCameraUpdateComponent,
    resolve: {
      sysCamera: SysCameraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SysCameraUpdateComponent,
    resolve: {
      sysCamera: SysCameraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sysCameraRoute)],
  exports: [RouterModule],
})
export class SysCameraRoutingModule {}
