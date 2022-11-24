import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdministrativeUnits } from '../administrative-units.model';
import { AdministrativeUnitsService } from '../service/administrative-units.service';

@Component({
  templateUrl: './administrative-units-delete-dialog.component.html',
})
export class AdministrativeUnitsDeleteDialogComponent {
  administrativeUnits?: IAdministrativeUnits;

  constructor(protected administrativeUnitsService: AdministrativeUnitsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.administrativeUnitsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
