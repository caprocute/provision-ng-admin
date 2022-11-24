import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SysOrganizationAreaComponent } from './list/sys-organization-area.component';
import { SysOrganizationAreaDetailComponent } from './detail/sys-organization-area-detail.component';
import { SysOrganizationAreaUpdateComponent } from './update/sys-organization-area-update.component';
import { SysOrganizationAreaDeleteDialogComponent } from './delete/sys-organization-area-delete-dialog.component';
import { SysOrganizationAreaRoutingModule } from './route/sys-organization-area-routing.module';

@NgModule({
  imports: [SharedModule, SysOrganizationAreaRoutingModule],
  declarations: [
    SysOrganizationAreaComponent,
    SysOrganizationAreaDetailComponent,
    SysOrganizationAreaUpdateComponent,
    SysOrganizationAreaDeleteDialogComponent,
  ],
  entryComponents: [SysOrganizationAreaDeleteDialogComponent],
})
export class SysOrganizationAreaModule {}
