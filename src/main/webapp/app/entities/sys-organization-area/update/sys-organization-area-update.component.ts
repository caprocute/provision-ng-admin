import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISysOrganizationArea, SysOrganizationArea } from '../sys-organization-area.model';
import { SysOrganizationAreaService } from '../service/sys-organization-area.service';

@Component({
  selector: 'jhi-sys-organization-area-update',
  templateUrl: './sys-organization-area-update.component.html',
})
export class SysOrganizationAreaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    orgId: [],
    areaId: [],
    isActive: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
  });

  constructor(
    protected sysOrganizationAreaService: SysOrganizationAreaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sysOrganizationArea }) => {
      if (sysOrganizationArea.id === undefined) {
        const today = dayjs().startOf('day');
        sysOrganizationArea.createdDate = today;
        sysOrganizationArea.lastModifiedDate = today;
      }

      this.updateForm(sysOrganizationArea);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sysOrganizationArea = this.createFromForm();
    if (sysOrganizationArea.id !== undefined) {
      this.subscribeToSaveResponse(this.sysOrganizationAreaService.update(sysOrganizationArea));
    } else {
      this.subscribeToSaveResponse(this.sysOrganizationAreaService.create(sysOrganizationArea));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysOrganizationArea>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(sysOrganizationArea: ISysOrganizationArea): void {
    this.editForm.patchValue({
      id: sysOrganizationArea.id,
      orgId: sysOrganizationArea.orgId,
      areaId: sysOrganizationArea.areaId,
      isActive: sysOrganizationArea.isActive,
      createdBy: sysOrganizationArea.createdBy,
      createdDate: sysOrganizationArea.createdDate ? sysOrganizationArea.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: sysOrganizationArea.lastModifiedBy,
      lastModifiedDate: sysOrganizationArea.lastModifiedDate ? sysOrganizationArea.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISysOrganizationArea {
    return {
      ...new SysOrganizationArea(),
      id: this.editForm.get(['id'])!.value,
      orgId: this.editForm.get(['orgId'])!.value,
      areaId: this.editForm.get(['areaId'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? dayjs(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
