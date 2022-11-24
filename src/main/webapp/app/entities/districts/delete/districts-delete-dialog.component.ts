import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDistricts } from '../districts.model';
import { DistrictsService } from '../service/districts.service';

@Component({
  templateUrl: './districts-delete-dialog.component.html',
})
export class DistrictsDeleteDialogComponent {
  districts?: IDistricts;

  constructor(protected districtsService: DistrictsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.districtsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
