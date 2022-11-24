import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProvincesComponent } from './list/provinces.component';
import { ProvincesDetailComponent } from './detail/provinces-detail.component';
import { ProvincesUpdateComponent } from './update/provinces-update.component';
import { ProvincesDeleteDialogComponent } from './delete/provinces-delete-dialog.component';
import { ProvincesRoutingModule } from './route/provinces-routing.module';

@NgModule({
  imports: [SharedModule, ProvincesRoutingModule],
  declarations: [ProvincesComponent, ProvincesDetailComponent, ProvincesUpdateComponent, ProvincesDeleteDialogComponent],
  entryComponents: [ProvincesDeleteDialogComponent],
})
export class ProvincesModule {}
