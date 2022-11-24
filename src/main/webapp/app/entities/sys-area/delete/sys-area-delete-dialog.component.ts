import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISysArea } from '../sys-area.model';
import { SysAreaService } from '../service/sys-area.service';

@Component({
  templateUrl: './sys-area-delete-dialog.component.html',
})
export class SysAreaDeleteDialogComponent {
  sysArea?: ISysArea;

  constructor(protected sysAreaService: SysAreaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sysAreaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
