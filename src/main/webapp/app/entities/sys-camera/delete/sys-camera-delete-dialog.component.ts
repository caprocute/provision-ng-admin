import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISysCamera } from '../sys-camera.model';
import { SysCameraService } from '../service/sys-camera.service';

@Component({
  templateUrl: './sys-camera-delete-dialog.component.html',
})
export class SysCameraDeleteDialogComponent {
  sysCamera?: ISysCamera;

  constructor(protected sysCameraService: SysCameraService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sysCameraService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
