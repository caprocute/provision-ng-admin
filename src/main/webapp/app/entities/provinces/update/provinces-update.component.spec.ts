import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProvincesService } from '../service/provinces.service';
import { IProvinces, Provinces } from '../provinces.model';
import { IAdministrativeRegions } from 'app/entities/administrative-regions/administrative-regions.model';
import { AdministrativeRegionsService } from 'app/entities/administrative-regions/service/administrative-regions.service';
import { IAdministrativeUnits } from 'app/entities/administrative-units/administrative-units.model';
import { AdministrativeUnitsService } from 'app/entities/administrative-units/service/administrative-units.service';

import { ProvincesUpdateComponent } from './provinces-update.component';

describe('Provinces Management Update Component', () => {
  let comp: ProvincesUpdateComponent;
  let fixture: ComponentFixture<ProvincesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let provincesService: ProvincesService;
  let administrativeRegionsService: AdministrativeRegionsService;
  let administrativeUnitsService: AdministrativeUnitsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProvincesUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProvincesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProvincesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    provincesService = TestBed.inject(ProvincesService);
    administrativeRegionsService = TestBed.inject(AdministrativeRegionsService);
    administrativeUnitsService = TestBed.inject(AdministrativeUnitsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AdministrativeRegions query and add missing value', () => {
      const provinces: IProvinces = { code: 'CBA' };
      const administrativeRegions: IAdministrativeRegions = { id: 38738 };
      provinces.administrativeRegions = administrativeRegions;

      const administrativeRegionsCollection: IAdministrativeRegions[] = [{ id: 58417 }];
      jest.spyOn(administrativeRegionsService, 'query').mockReturnValue(of(new HttpResponse({ body: administrativeRegionsCollection })));
      const additionalAdministrativeRegions = [administrativeRegions];
      const expectedCollection: IAdministrativeRegions[] = [...additionalAdministrativeRegions, ...administrativeRegionsCollection];
      jest.spyOn(administrativeRegionsService, 'addAdministrativeRegionsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ provinces });
      comp.ngOnInit();

      expect(administrativeRegionsService.query).toHaveBeenCalled();
      expect(administrativeRegionsService.addAdministrativeRegionsToCollectionIfMissing).toHaveBeenCalledWith(
        administrativeRegionsCollection,
        ...additionalAdministrativeRegions
      );
      expect(comp.administrativeRegionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AdministrativeUnits query and add missing value', () => {
      const provinces: IProvinces = { code: 'CBA' };
      const administrativeUnits: IAdministrativeUnits = { id: 29825 };
      provinces.administrativeUnits = administrativeUnits;

      const administrativeUnitsCollection: IAdministrativeUnits[] = [{ id: 12124 }];
      jest.spyOn(administrativeUnitsService, 'query').mockReturnValue(of(new HttpResponse({ body: administrativeUnitsCollection })));
      const additionalAdministrativeUnits = [administrativeUnits];
      const expectedCollection: IAdministrativeUnits[] = [...additionalAdministrativeUnits, ...administrativeUnitsCollection];
      jest.spyOn(administrativeUnitsService, 'addAdministrativeUnitsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ provinces });
      comp.ngOnInit();

      expect(administrativeUnitsService.query).toHaveBeenCalled();
      expect(administrativeUnitsService.addAdministrativeUnitsToCollectionIfMissing).toHaveBeenCalledWith(
        administrativeUnitsCollection,
        ...additionalAdministrativeUnits
      );
      expect(comp.administrativeUnitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const provinces: IProvinces = { code: 'CBA' };
      const administrativeRegions: IAdministrativeRegions = { id: 29065 };
      provinces.administrativeRegions = administrativeRegions;
      const administrativeUnits: IAdministrativeUnits = { id: 64545 };
      provinces.administrativeUnits = administrativeUnits;

      activatedRoute.data = of({ provinces });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(provinces));
      expect(comp.administrativeRegionsSharedCollection).toContain(administrativeRegions);
      expect(comp.administrativeUnitsSharedCollection).toContain(administrativeUnits);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Provinces>>();
      const provinces = { code: 'ABC' };
      jest.spyOn(provincesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ provinces });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: provinces }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(provincesService.update).toHaveBeenCalledWith(provinces);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Provinces>>();
      const provinces = new Provinces();
      jest.spyOn(provincesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ provinces });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: provinces }));
      saveSubject.complete();

      // THEN
      expect(provincesService.create).toHaveBeenCalledWith(provinces);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Provinces>>();
      const provinces = { code: 'ABC' };
      jest.spyOn(provincesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ provinces });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(provincesService.update).toHaveBeenCalledWith(provinces);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAdministrativeRegionsById', () => {
      it('Should return tracked AdministrativeRegions primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAdministrativeRegionsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAdministrativeUnitsById', () => {
      it('Should return tracked AdministrativeUnits primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAdministrativeUnitsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
