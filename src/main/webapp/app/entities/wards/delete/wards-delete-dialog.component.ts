import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IWards } from '../wards.model';
import { WardsService } from '../service/wards.service';

@Component({
  templateUrl: './wards-delete-dialog.component.html',
})
export class WardsDeleteDialogComponent {
  wards?: IWards;

  constructor(protected wardsService: WardsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.wardsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
