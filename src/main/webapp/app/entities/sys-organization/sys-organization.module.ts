import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SysOrganizationComponent } from './list/sys-organization.component';
import { SysOrganizationDetailComponent } from './detail/sys-organization-detail.component';
import { SysOrganizationUpdateComponent } from './update/sys-organization-update.component';
import { SysOrganizationDeleteDialogComponent } from './delete/sys-organization-delete-dialog.component';
import { SysOrganizationRoutingModule } from './route/sys-organization-routing.module';

@NgModule({
  imports: [SharedModule, SysOrganizationRoutingModule],
  declarations: [
    SysOrganizationComponent,
    SysOrganizationDetailComponent,
    SysOrganizationUpdateComponent,
    SysOrganizationDeleteDialogComponent,
  ],
  entryComponents: [SysOrganizationDeleteDialogComponent],
})
export class SysOrganizationModule {}
