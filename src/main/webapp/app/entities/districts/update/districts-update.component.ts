import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDistricts, Districts } from '../districts.model';
import { DistrictsService } from '../service/districts.service';
import { IProvinces } from 'app/entities/provinces/provinces.model';
import { ProvincesService } from 'app/entities/provinces/service/provinces.service';
import { IAdministrativeUnits } from 'app/entities/administrative-units/administrative-units.model';
import { AdministrativeUnitsService } from 'app/entities/administrative-units/service/administrative-units.service';

@Component({
  selector: 'jhi-districts-update',
  templateUrl: './districts-update.component.html',
})
export class DistrictsUpdateComponent implements OnInit {
  isSaving = false;

  provincesSharedCollection: IProvinces[] = [];
  administrativeUnitsSharedCollection: IAdministrativeUnits[] = [];

  editForm = this.fb.group({
    code: [],
    name: [],
    nameEn: [],
    fullName: [],
    fullNameEn: [],
    codeName: [],
    provinceCode: [],
    administrativeUnitId: [],
    provinces: [],
    administrativeUnits: [],
  });

  constructor(
    protected districtsService: DistrictsService,
    protected provincesService: ProvincesService,
    protected administrativeUnitsService: AdministrativeUnitsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ districts }) => {
      this.updateForm(districts);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const districts = this.createFromForm();
    if (districts.code !== undefined) {
      this.subscribeToSaveResponse(this.districtsService.update(districts));
    } else {
      this.subscribeToSaveResponse(this.districtsService.create(districts));
    }
  }

  trackProvincesByCode(_index: number, item: IProvinces): string {
    return item.code!;
  }

  trackAdministrativeUnitsById(_index: number, item: IAdministrativeUnits): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistricts>>): void {
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

  protected updateForm(districts: IDistricts): void {
    this.editForm.patchValue({
      code: districts.code,
      name: districts.name,
      nameEn: districts.nameEn,
      fullName: districts.fullName,
      fullNameEn: districts.fullNameEn,
      codeName: districts.codeName,
      provinceCode: districts.provinceCode,
      administrativeUnitId: districts.administrativeUnitId,
      provinces: districts.provinces,
      administrativeUnits: districts.administrativeUnits,
    });

    this.provincesSharedCollection = this.provincesService.addProvincesToCollectionIfMissing(
      this.provincesSharedCollection,
      districts.provinces
    );
    this.administrativeUnitsSharedCollection = this.administrativeUnitsService.addAdministrativeUnitsToCollectionIfMissing(
      this.administrativeUnitsSharedCollection,
      districts.administrativeUnits
    );
  }

  protected loadRelationshipsOptions(): void {
    this.provincesService
      .query()
      .pipe(map((res: HttpResponse<IProvinces[]>) => res.body ?? []))
      .pipe(
        map((provinces: IProvinces[]) =>
          this.provincesService.addProvincesToCollectionIfMissing(provinces, this.editForm.get('provinces')!.value)
        )
      )
      .subscribe((provinces: IProvinces[]) => (this.provincesSharedCollection = provinces));

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

  protected createFromForm(): IDistricts {
    return {
      ...new Districts(),
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      nameEn: this.editForm.get(['nameEn'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      fullNameEn: this.editForm.get(['fullNameEn'])!.value,
      codeName: this.editForm.get(['codeName'])!.value,
      provinceCode: this.editForm.get(['provinceCode'])!.value,
      administrativeUnitId: this.editForm.get(['administrativeUnitId'])!.value,
      provinces: this.editForm.get(['provinces'])!.value,
      administrativeUnits: this.editForm.get(['administrativeUnits'])!.value,
    };
  }
}
