import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SysCameraComponent } from './list/sys-camera.component';
import { SysCameraDetailComponent } from './detail/sys-camera-detail.component';
import { SysCameraUpdateComponent } from './update/sys-camera-update.component';
import { SysCameraDeleteDialogComponent } from './delete/sys-camera-delete-dialog.component';
import { SysCameraRoutingModule } from './route/sys-camera-routing.module';

@NgModule({
  imports: [SharedModule, SysCameraRoutingModule],
  declarations: [SysCameraComponent, SysCameraDetailComponent, SysCameraUpdateComponent, SysCameraDeleteDialogComponent],
  entryComponents: [SysCameraDeleteDialogComponent],
})
export class SysCameraModule {}
