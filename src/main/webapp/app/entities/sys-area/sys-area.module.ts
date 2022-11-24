import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SysAreaComponent } from './list/sys-area.component';
import { SysAreaDetailComponent } from './detail/sys-area-detail.component';
import { SysAreaUpdateComponent } from './update/sys-area-update.component';
import { SysAreaDeleteDialogComponent } from './delete/sys-area-delete-dialog.component';
import { SysAreaRoutingModule } from './route/sys-area-routing.module';

@NgModule({
  imports: [SharedModule, SysAreaRoutingModule],
  declarations: [SysAreaComponent, SysAreaDetailComponent, SysAreaUpdateComponent, SysAreaDeleteDialogComponent],
  entryComponents: [SysAreaDeleteDialogComponent],
})
export class SysAreaModule {}
