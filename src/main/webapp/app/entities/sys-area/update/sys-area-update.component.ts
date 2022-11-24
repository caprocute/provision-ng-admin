import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISysArea, SysArea } from '../sys-area.model';
import { SysAreaService } from '../service/sys-area.service';

@Component({
  selector: 'jhi-sys-area-update',
  templateUrl: './sys-area-update.component.html',
})
export class SysAreaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    areaCode: [],
    areaName: [],
    provinceCode: [],
    districtCode: [],
    wardCode: [],
    description: [],
    isActive: [],
    status: [],
    locationLat: [],
    locationLan: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
  });

  constructor(protected sysAreaService: SysAreaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sysArea }) => {
      if (sysArea.id === undefined) {
        const today = dayjs().startOf('day');
        sysArea.createdDate = today;
        sysArea.lastModifiedDate = today;
      }

      this.updateForm(sysArea);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sysArea = this.createFromForm();
    if (sysArea.id !== undefined) {
      this.subscribeToSaveResponse(this.sysAreaService.update(sysArea));
    } else {
      this.subscribeToSaveResponse(this.sysAreaService.create(sysArea));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysArea>>): void {
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

  protected updateForm(sysArea: ISysArea): void {
    this.editForm.patchValue({
      id: sysArea.id,
      areaCode: sysArea.areaCode,
      areaName: sysArea.areaName,
      provinceCode: sysArea.provinceCode,
      districtCode: sysArea.districtCode,
      wardCode: sysArea.wardCode,
      description: sysArea.description,
      isActive: sysArea.isActive,
      status: sysArea.status,
      locationLat: sysArea.locationLat,
      locationLan: sysArea.locationLan,
      createdBy: sysArea.createdBy,
      createdDate: sysArea.createdDate ? sysArea.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: sysArea.lastModifiedBy,
      lastModifiedDate: sysArea.lastModifiedDate ? sysArea.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISysArea {
    return {
      ...new SysArea(),
      id: this.editForm.get(['id'])!.value,
      areaCode: this.editForm.get(['areaCode'])!.value,
      areaName: this.editForm.get(['areaName'])!.value,
      provinceCode: this.editForm.get(['provinceCode'])!.value,
      districtCode: this.editForm.get(['districtCode'])!.value,
      wardCode: this.editForm.get(['wardCode'])!.value,
      description: this.editForm.get(['description'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      status: this.editForm.get(['status'])!.value,
      locationLat: this.editForm.get(['locationLat'])!.value,
      locationLan: this.editForm.get(['locationLan'])!.value,
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
