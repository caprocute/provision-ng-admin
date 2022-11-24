import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAdministrativeUnits, AdministrativeUnits } from '../administrative-units.model';
import { AdministrativeUnitsService } from '../service/administrative-units.service';

@Component({
  selector: 'jhi-administrative-units-update',
  templateUrl: './administrative-units-update.component.html',
})
export class AdministrativeUnitsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fullName: [],
    fullNameEn: [],
    shortName: [],
    shortNameEn: [],
    codeName: [],
    codeNameEn: [],
  });

  constructor(
    protected administrativeUnitsService: AdministrativeUnitsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ administrativeUnits }) => {
      this.updateForm(administrativeUnits);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const administrativeUnits = this.createFromForm();
    if (administrativeUnits.id !== undefined) {
      this.subscribeToSaveResponse(this.administrativeUnitsService.update(administrativeUnits));
    } else {
      this.subscribeToSaveResponse(this.administrativeUnitsService.create(administrativeUnits));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdministrativeUnits>>): void {
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

  protected updateForm(administrativeUnits: IAdministrativeUnits): void {
    this.editForm.patchValue({
      id: administrativeUnits.id,
      fullName: administrativeUnits.fullName,
      fullNameEn: administrativeUnits.fullNameEn,
      shortName: administrativeUnits.shortName,
      shortNameEn: administrativeUnits.shortNameEn,
      codeName: administrativeUnits.codeName,
      codeNameEn: administrativeUnits.codeNameEn,
    });
  }

  protected createFromForm(): IAdministrativeUnits {
    return {
      ...new AdministrativeUnits(),
      id: this.editForm.get(['id'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      fullNameEn: this.editForm.get(['fullNameEn'])!.value,
      shortName: this.editForm.get(['shortName'])!.value,
      shortNameEn: this.editForm.get(['shortNameEn'])!.value,
      codeName: this.editForm.get(['codeName'])!.value,
      codeNameEn: this.editForm.get(['codeNameEn'])!.value,
    };
  }
}
