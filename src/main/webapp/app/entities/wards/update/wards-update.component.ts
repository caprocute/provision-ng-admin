import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IWards, Wards } from '../wards.model';
import { WardsService } from '../service/wards.service';
import { IAdministrativeRegions } from 'app/entities/administrative-regions/administrative-regions.model';
import { AdministrativeRegionsService } from 'app/entities/administrative-regions/service/administrative-regions.service';
import { IDistricts } from 'app/entities/districts/districts.model';
import { DistrictsService } from 'app/entities/districts/service/districts.service';

@Component({
  selector: 'jhi-wards-update',
  templateUrl: './wards-update.component.html',
})
export class WardsUpdateComponent implements OnInit {
  isSaving = false;

  administrativeRegionsSharedCollection: IAdministrativeRegions[] = [];
  districtsSharedCollection: IDistricts[] = [];

  editForm = this.fb.group({
    code: [],
    name: [],
    nameEn: [],
    fullName: [],
    fullNameEn: [],
    codeName: [],
    districtCode: [],
    administrativeUnitId: [],
    administrativeRegions: [],
    districts: [],
  });

  constructor(
    protected wardsService: WardsService,
    protected administrativeRegionsService: AdministrativeRegionsService,
    protected districtsService: DistrictsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wards }) => {
      this.updateForm(wards);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wards = this.createFromForm();
    if (wards.code !== undefined) {
      this.subscribeToSaveResponse(this.wardsService.update(wards));
    } else {
      this.subscribeToSaveResponse(this.wardsService.create(wards));
    }
  }

  trackAdministrativeRegionsById(_index: number, item: IAdministrativeRegions): number {
    return item.id!;
  }

  trackDistrictsByCode(_index: number, item: IDistricts): string {
    return item.code!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWards>>): void {
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

  protected updateForm(wards: IWards): void {
    this.editForm.patchValue({
      code: wards.code,
      name: wards.name,
      nameEn: wards.nameEn,
      fullName: wards.fullName,
      fullNameEn: wards.fullNameEn,
      codeName: wards.codeName,
      districtCode: wards.districtCode,
      administrativeUnitId: wards.administrativeUnitId,
      administrativeRegions: wards.administrativeRegions,
      districts: wards.districts,
    });

    this.administrativeRegionsSharedCollection = this.administrativeRegionsService.addAdministrativeRegionsToCollectionIfMissing(
      this.administrativeRegionsSharedCollection,
      wards.administrativeRegions
    );
    this.districtsSharedCollection = this.districtsService.addDistrictsToCollectionIfMissing(
      this.districtsSharedCollection,
      wards.districts
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

    this.districtsService
      .query()
      .pipe(map((res: HttpResponse<IDistricts[]>) => res.body ?? []))
      .pipe(
        map((districts: IDistricts[]) =>
          this.districtsService.addDistrictsToCollectionIfMissing(districts, this.editForm.get('districts')!.value)
        )
      )
      .subscribe((districts: IDistricts[]) => (this.districtsSharedCollection = districts));
  }

  protected createFromForm(): IWards {
    return {
      ...new Wards(),
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      fullNameEn: this.editForm.get(['fullNameEn'])!.value,
      codeName: this.editForm.get(['codeName'])!.value,
      districtCode: this.editForm.get(['districtCode'])!.value,
      administrativeUnitId: this.editForm.get(['administrativeUnitId'])!.value,
      administrativeRegions: this.editForm.get(['administrativeRegions'])!.value,
      districts: this.editForm.get(['districts'])!.value,
    };
  }
}
