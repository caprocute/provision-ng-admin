import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DistrictsComponent } from './list/districts.component';
import { DistrictsDetailComponent } from './detail/districts-detail.component';
import { DistrictsUpdateComponent } from './update/districts-update.component';
import { DistrictsDeleteDialogComponent } from './delete/districts-delete-dialog.component';
import { DistrictsRoutingModule } from './route/districts-routing.module';

@NgModule({
  imports: [SharedModule, DistrictsRoutingModule],
  declarations: [DistrictsComponent, DistrictsDetailComponent, DistrictsUpdateComponent, DistrictsDeleteDialogComponent],
  entryComponents: [DistrictsDeleteDialogComponent],
})
export class DistrictsModule {}
