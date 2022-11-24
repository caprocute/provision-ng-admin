import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProvinces, Provinces } from '../provinces.model';
import { ProvincesService } from '../service/provinces.service';
import { IAdministrativeRegions } from 'app/entities/administrative-regions/administrative-regions.model';
import { AdministrativeRegionsService } from 'app/entities/administrative-regions/service/administrative-regions.service';
import { IAdministrativeUnits } from 'app/entities/administrative-units/administrative-units.model';
import { AdministrativeUnitsService } from 'app/entities/administrative-units/service/administrative-units.service';

@Component({
  selector: 'jhi-provinces-update',
  templateUrl: './provinces-update.component.html',
})
export class ProvincesUpdateComponent implements OnInit {
  isSaving = false;

  administrativeRegionsSharedCollection: IAdministrativeRegions[] = [];
  administrativeUnitsSharedCollection: IAdministrativeUnits[] = [];

  editForm = this.fb.group({
    code: [],
    name: [],
    nameEn: [],
    fullName: [],
    fullNameEn: [],
    codeName: [],
    administrativeUnitId: [],
    administrativeRegionId: [],
    administrativeRegions: [],
    administrativeUnits: [],
  });

  constructor(
    protected provincesService: ProvincesService,
    protected administrativeRegionsService: AdministrativeRegionsService,
    protected administrativeUnitsService: AdministrativeUnitsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ provinces }) => {
      this.updateForm(provinces);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const provinces = this.createFromForm();
    if (provinces.code !== undefined) {
      this.subscribeToSaveResponse(this.provincesService.update(provinces));
    } else {
      this.subscribeToSaveResponse(this.provincesService.create(provinces));
    }
  }

  trackAdministrativeRegionsById(_index: number, item: IAdministrativeRegions): number {
    return item.id!;
  }

  trackAdministrativeUnitsById(_index: number, item: IAdministrativeUnits): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProvinces>>): void {
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

  protected updateForm(provinces: IProvinces): void {
    this.editForm.patchValue({
      code: provinces.code,
      name: provinces.name,
      nameEn: provinces.nameEn,
      fullName: provinces.fullName,
      fullNameEn: provinces.fullNameEn,
      codeName: provinces.codeName,
      administrativeUnitId: provinces.administrativeUnitId,
      administrativeRegionId: provinces.administrativeRegionId,
      administrativeRegions: provinces.administrativeRegions,
      administrativeUnits: provinces.administrativeUnits,
    });

    this.administrativeRegionsSharedCollection = this.administrativeRegionsService.addAdministrativeRegionsToCollectionIfMissing(
      this.administrativeRegionsSharedCollection,
      provinces.administrativeRegions
    );
    this.administrativeUnitsSharedCollection = this.administrativeUnitsService.addAdministrativeUnitsToCollectionIfMissing(
      this.administrativeUnitsSharedCollection,
      provinces.administrativeUnits
    );
  }

  protected loadRelationshipsOptions(): void {
    this.administrativeRegionsService
      .query()
      .pipe(map((res: HttpResponse<IAdministrativeRegions[]>) => res.body ?? []))
      .pipe(
        map((administrativeRegions: IAdministrativeRegions[]) =>
          this.administrativeRegionsService.addAdministrativeRegionsToCollectionIfMissing(
            administrativeRegions,
            this.editForm.get('administrativeRegions')!.value
          )
        )
      )
      .subscribe((administrativeRegions: IAdministrativeRegions[]) => (this.administrativeRegionsSharedCollection = administrativeRegions));

    this.administrativeUnitsService
      .query()
      .pipe(map((res: HttpResponse<IAdministrativeUnits[]>) => res.body ?? []))
      .pipe(
        map((administrativeUnits: IAdministrativeUnits[]) =>
          this.administrativeUnitsService.addAdministrativeUnitsToCollectionIfMissing(
            administrativeUnits,
            this.editForm.get('administrativeUnits')!.value
          )
        )
      )
      .subscribe((administrativeUnits: IAdministrativeUnits[]) => (this.administrativeUnitsSharedCollection = administrativeUnits));
  }

  protected createFromForm(): IProvinces {
    return {
      ...new Provinces(),
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      fullNameEn: this.editForm.get(['fullNameEn'])!.value,
      codeName: this.editForm.get(['codeName'])!.value,
      administrativeUnitId: this.editForm.get(['administrativeUnitId'])!.value,
      administrativeRegionId: this.editForm.get(['administrativeRegionId'])!.value,
      administrativeRegions: this.editForm.get(['administrativeRegions'])!.value,
      administrativeUnits: this.editForm.get(['administrativeUnits'])!.value,
    };
  }
}
