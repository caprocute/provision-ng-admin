import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WardsComponent } from './list/wards.component';
import { WardsDetailComponent } from './detail/wards-detail.component';
import { WardsUpdateComponent } from './update/wards-update.component';
import { WardsDeleteDialogComponent } from './delete/wards-delete-dialog.component';
import { WardsRoutingModule } from './route/wards-routing.module';

@NgModule({
  imports: [SharedModule, WardsRoutingModule],
  declarations: [WardsComponent, WardsDetailComponent, WardsUpdateComponent, WardsDeleteDialogComponent],
  entryComponents: [WardsDeleteDialogComponent],
})
export class WardsModule {}
