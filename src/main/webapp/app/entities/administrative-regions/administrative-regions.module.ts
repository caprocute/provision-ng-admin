import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdministrativeRegionsComponent } from './list/administrative-regions.component';
import { AdministrativeRegionsDetailComponent } from './detail/administrative-regions-detail.component';
import { AdministrativeRegionsUpdateComponent } from './update/administrative-regions-update.component';
import { AdministrativeRegionsDeleteDialogComponent } from './delete/administrative-regions-delete-dialog.component';
import { AdministrativeRegionsRoutingModule } from './route/administrative-regions-routing.module';

@NgModule({
  imports: [SharedModule, AdministrativeRegionsRoutingModule],
  declarations: [
    AdministrativeRegionsComponent,
    AdministrativeRegionsDetailComponent,
    AdministrativeRegionsUpdateComponent,
    AdministrativeRegionsDeleteDialogComponent,
  ],
  entryComponents: [AdministrativeRegionsDeleteDialogComponent],
})
export class AdministrativeRegionsModule {}
