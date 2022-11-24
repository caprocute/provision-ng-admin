import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdministrativeRegions } from '../administrative-regions.model';
import { AdministrativeRegionsService } from '../service/administrative-regions.service';

@Component({
  templateUrl: './administrative-regions-delete-dialog.component.html',
})
export class AdministrativeRegionsDeleteDialogComponent {
  administrativeRegions?: IAdministrativeRegions;

  constructor(protected administrativeRegionsService: AdministrativeRegionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.administrativeRegionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
