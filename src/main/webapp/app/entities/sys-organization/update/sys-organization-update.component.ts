import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISysOrganization, SysOrganization } from '../sys-organization.model';
import { SysOrganizationService } from '../service/sys-organization.service';

@Component({
  selector: 'jhi-sys-organization-update',
  templateUrl: './sys-organization-update.component.html',
})
export class SysOrganizationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    parentId: [],
    sysOrgCode: [],
    sysOrgName: [],
    sysOrgPath: [],
    sysOrgFullPath: [],
    description: [],
    address: [],
    type: [],
    tenantId: [],
    sysOrgType: [],
    isActive: [],
    status: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
  });

  constructor(
    protected sysOrganizationService: SysOrganizationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sysOrganization }) => {
      if (sysOrganization.id === undefined) {
        const today = dayjs().startOf('day');
        sysOrganization.createdDate = today;
        sysOrganization.lastModifiedDate = today;
      }

      this.updateForm(sysOrganization);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sysOrganization = this.createFromForm();
    if (sysOrganization.id !== undefined) {
      this.subscribeToSaveResponse(this.sysOrganizationService.update(sysOrganization));
    } else {
      this.subscribeToSaveResponse(this.sysOrganizationService.create(sysOrganization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysOrganization>>): void {
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

  protected updateForm(sysOrganization: ISysOrganization): void {
    this.editForm.patchValue({
      id: sysOrganization.id,
      parentId: sysOrganization.parentId,
      sysOrgCode: sysOrganization.sysOrgCode,
      sysOrgName: sysOrganization.sysOrgName,
      sysOrgPath: sysOrganization.sysOrgPath,
      sysOrgFullPath: sysOrganization.sysOrgFullPath,
      description: sysOrganization.description,
      address: sysOrganization.address,
      type: sysOrganization.type,
      tenantId: sysOrganization.tenantId,
      sysOrgType: sysOrganization.sysOrgType,
      isActive: sysOrganization.isActive,
      status: sysOrganization.status,
      createdBy: sysOrganization.createdBy,
      createdDate: sysOrganization.createdDate ? sysOrganization.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: sysOrganization.lastModifiedBy,
      lastModifiedDate: sysOrganization.lastModifiedDate ? sysOrganization.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISysOrganization {
    return {
      ...new SysOrganization(),
      id: this.editForm.get(['id'])!.value,
      parentId: this.editForm.get(['parentId'])!.value,
      sysOrgCode: this.editForm.get(['sysOrgCode'])!.value,
      sysOrgName: this.editForm.get(['sysOrgName'])!.value,
      sysOrgPath: this.editForm.get(['sysOrgPath'])!.value,
      sysOrgFullPath: this.editForm.get(['sysOrgFullPath'])!.value,
      description: this.editForm.get(['description'])!.value,
      address: this.editForm.get(['address'])!.value,
      type: this.editForm.get(['type'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
      sysOrgType: this.editForm.get(['sysOrgType'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      status: this.editForm.get(['status'])!.value,
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
