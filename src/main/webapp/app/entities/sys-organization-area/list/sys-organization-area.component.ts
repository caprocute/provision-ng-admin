import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISysOrganizationArea } from '../sys-organization-area.model';
import { SysOrganizationAreaService } from '../service/sys-organization-area.service';
import { SysOrganizationAreaDeleteDialogComponent } from '../delete/sys-organization-area-delete-dialog.component';

@Component({
  selector: 'jhi-sys-organization-area',
  templateUrl: './sys-organization-area.component.html',
})
export class SysOrganizationAreaComponent implements OnInit {
  sysOrganizationAreas?: ISysOrganizationArea[];
  isLoading = false;

  constructor(protected sysOrganizationAreaService: SysOrganizationAreaService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.sysOrganizationAreaService.query().subscribe({
      next: (res: HttpResponse<ISysOrganizationArea[]>) => {
        this.isLoading = false;
        this.sysOrganizationAreas = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ISysOrganizationArea): number {
    return item.id!;
  }

  delete(sysOrganizationArea: ISysOrganizationArea): void {
    const modalRef = this.modalService.open(SysOrganizationAreaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sysOrganizationArea = sysOrganizationArea;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
