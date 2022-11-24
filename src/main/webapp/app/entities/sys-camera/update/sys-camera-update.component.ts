import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISysCamera, SysCamera } from '../sys-camera.model';
import { SysCameraService } from '../service/sys-camera.service';

@Component({
  selector: 'jhi-sys-camera-update',
  templateUrl: './sys-camera-update.component.html',
})
export class SysCameraUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    camCode: [],
    camName: [],
    areaId: [],
    branch: [],
    origin: [],
    description: [],
    isActive: [],
    status: [],
    locationLat: [],
    locationLan: [],
    urlSFTP: [],
    urlLiveStream: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
  });

  constructor(protected sysCameraService: SysCameraService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sysCamera }) => {
      if (sysCamera.id === undefined) {
        const today = dayjs().startOf('day');
        sysCamera.createdDate = today;
        sysCamera.lastModifiedDate = today;
      }

      this.updateForm(sysCamera);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sysCamera = this.createFromForm();
    if (sysCamera.id !== undefined) {
      this.subscribeToSaveResponse(this.sysCameraService.update(sysCamera));
    } else {
      this.subscribeToSaveResponse(this.sysCameraService.create(sysCamera));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysCamera>>): void {
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

  protected updateForm(sysCamera: ISysCamera): void {
    this.editForm.patchValue({
      id: sysCamera.id,
      camCode: sysCamera.camCode,
      camName: sysCamera.camName,
      areaId: sysCamera.areaId,
      branch: sysCamera.branch,
      origin: sysCamera.origin,
      description: sysCamera.description,
      isActive: sysCamera.isActive,
      status: sysCamera.status,
      locationLat: sysCamera.locationLat,
      locationLan: sysCamera.locationLan,
      urlSFTP: sysCamera.urlSFTP,
      urlLiveStream: sysCamera.urlLiveStream,
      createdBy: sysCamera.createdBy,
      createdDate: sysCamera.createdDate ? sysCamera.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: sysCamera.lastModifiedBy,
      lastModifiedDate: sysCamera.lastModifiedDate ? sysCamera.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISysCamera {
    return {
      ...new SysCamera(),
      id: this.editForm.get(['id'])!.value,
      camCode: this.editForm.get(['camCode'])!.value,
      camName: this.editForm.get(['camName'])!.value,
      areaId: this.editForm.get(['areaId'])!.value,
      branch: this.editForm.get(['branch'])!.value,
      origin: this.editForm.get(['origin'])!.value,
      description: this.editForm.get(['description'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      status: this.editForm.get(['status'])!.value,
      locationLat: this.editForm.get(['locationLat'])!.value,
      locationLan: this.editForm.get(['locationLan'])!.value,
      urlSFTP: this.editForm.get(['urlSFTP'])!.value,
      urlLiveStream: this.editForm.get(['urlLiveStream'])!.value,
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
