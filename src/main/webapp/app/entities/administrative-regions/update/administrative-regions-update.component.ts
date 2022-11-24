import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAdministrativeRegions, AdministrativeRegions } from '../administrative-regions.model';
import { AdministrativeRegionsService } from '../service/administrative-regions.service';

@Component({
  selector: 'jhi-administrative-regions-update',
  templateUrl: './administrative-regions-update.component.html',
})
export class AdministrativeRegionsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    nameEn: [],
    codeName: [],
    codeNameEn: [],
  });

  constructor(
    protected administrativeRegionsService: AdministrativeRegionsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ administrativeRegions }) => {
      this.updateForm(administrativeRegions);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const administrativeRegions = this.createFromForm();
    if (administrativeRegions.id !== undefined) {
      this.subscribeToSaveResponse(this.administrativeRegionsService.update(administrativeRegions));
    } else {
      this.subscribeToSaveResponse(this.administrativeRegionsService.create(administrativeRegions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdministrativeRegions>>): void {
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

  protected updateForm(administrativeRegions: IAdministrativeRegions): void {
    this.editForm.patchValue({
      id: administrativeRegions.id,
      name: administrativeRegions.name,
      nameEn: administrativeRegions.nameEn,
      codeName: administrativeRegions.codeName,
      codeNameEn: administrativeRegions.codeNameEn,
    });
  }

  protected createFromForm(): IAdministrativeRegions {
    return {
      ...new AdministrativeRegions(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
      codeName: this.editForm.get(['codeName'])!.value,
      codeNameEn: this.editForm.get(['codeNameEn'])!.value,
    };
  }
}
