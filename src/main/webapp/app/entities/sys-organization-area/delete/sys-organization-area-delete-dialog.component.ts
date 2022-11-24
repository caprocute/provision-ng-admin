import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISysOrganizationArea } from '../sys-organization-area.model';
import { SysOrganizationAreaService } from '../service/sys-organization-area.service';

@Component({
  templateUrl: './sys-organization-area-delete-dialog.component.html',
})
export class SysOrganizationAreaDeleteDialogComponent {
  sysOrganizationArea?: ISysOrganizationArea;

  constructor(protected sysOrganizationAreaService: SysOrganizationAreaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sysOrganizationAreaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
