import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISysOrganization } from '../sys-organization.model';
import { SysOrganizationService } from '../service/sys-organization.service';

@Component({
  templateUrl: './sys-organization-delete-dialog.component.html',
})
export class SysOrganizationDeleteDialogComponent {
  sysOrganization?: ISysOrganization;

  constructor(protected sysOrganizationService: SysOrganizationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sysOrganizationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
