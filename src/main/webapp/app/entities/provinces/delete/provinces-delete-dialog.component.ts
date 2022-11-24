import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProvinces } from '../provinces.model';
import { ProvincesService } from '../service/provinces.service';

@Component({
  templateUrl: './provinces-delete-dialog.component.html',
})
export class ProvincesDeleteDialogComponent {
  provinces?: IProvinces;

  constructor(protected provincesService: ProvincesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.provincesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
