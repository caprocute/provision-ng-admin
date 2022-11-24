import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AdministrativeUnitsComponent } from './list/administrative-units.component';
import { AdministrativeUnitsDetailComponent } from './detail/administrative-units-detail.component';
import { AdministrativeUnitsUpdateComponent } from './update/administrative-units-update.component';
import { AdministrativeUnitsDeleteDialogComponent } from './delete/administrative-units-delete-dialog.component';
import { AdministrativeUnitsRoutingModule } from './route/administrative-units-routing.module';

@NgModule({
  imports: [SharedModule, AdministrativeUnitsRoutingModule],
  declarations: [
    AdministrativeUnitsComponent,
    AdministrativeUnitsDetailComponent,
    AdministrativeUnitsUpdateComponent,
    AdministrativeUnitsDeleteDialogComponent,
  ],
  entryComponents: [AdministrativeUnitsDeleteDialogComponent],
})
export class AdministrativeUnitsModule {}
