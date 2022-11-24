import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { WardsService } from '../service/wards.service';
import { IWards, Wards } from '../wards.model';
import { IAdministrativeRegions } from 'app/entities/administrative-regions/administrative-regions.model';
import { AdministrativeRegionsService } from 'app/entities/administrative-regions/service/administrative-regions.service';
import { IDistricts } from 'app/entities/districts/districts.model';
import { DistrictsService } from 'app/entities/districts/service/districts.service';

import { WardsUpdateComponent } from './wards-update.component';

describe('Wards Management Update Component', () => {
  let comp: WardsUpdateComponent;
  let fixture: ComponentFixture<WardsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let wardsService: WardsService;
  let administrativeRegionsService: AdministrativeRegionsService;
  let districtsService: DistrictsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [WardsUpdateComponent],
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
      .overrideTemplate(WardsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WardsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    wardsService = TestBed.inject(WardsService);
    administrativeRegionsService = TestBed.inject(AdministrativeRegionsService);
    districtsService = TestBed.inject(DistrictsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AdministrativeRegions query and add missing value', () => {
      const wards: IWards = { code: 'CBA' };
      const administrativeRegions: IAdministrativeRegions = { id: 44534 };
      wards.administrativeRegions = administrativeRegions;

      const administrativeRegionsCollection: IAdministrativeRegions[] = [{ id: 90808 }];
      jest.spyOn(administrativeRegionsService, 'query').mockReturnValue(of(new HttpResponse({ body: administrativeRegionsCollection })));
      const additionalAdministrativeRegions = [administrativeRegions];
      const expectedCollection: IAdministrativeRegions[] = [...additionalAdministrativeRegions, ...administrativeRegionsCollection];
      jest.spyOn(administrativeRegionsService, 'addAdministrativeRegionsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ wards });
      comp.ngOnInit();

      expect(administrativeRegionsService.query).toHaveBeenCalled();
      expect(administrativeRegionsService.addAdministrativeRegionsToCollectionIfMissing).toHaveBeenCalledWith(
        administrativeRegionsCollection,
        ...additionalAdministrativeRegions
      );
      expect(comp.administrativeRegionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Districts query and add missing value', () => {
      const wards: IWards = { code: 'CBA' };
      const districts: IDistricts = { code: '163726c1-e0ff-4780-ab4d-d21c801cc435' };
      wards.districts = districts;

      const districtsCollection: IDistricts[] = [{ code: '81985af2-50d2-433f-9531-3b5ae471a655' }];
      jest.spyOn(districtsService, 'query').mockReturnValue(of(new HttpResponse({ body: districtsCollection })));
      const additionalDistricts = [districts];
      const expectedCollection: IDistricts[] = [...additionalDistricts, ...districtsCollection];
      jest.spyOn(districtsService, 'addDistrictsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ wards });
      comp.ngOnInit();

      expect(districtsService.query).toHaveBeenCalled();
      expect(districtsService.addDistrictsToCollectionIfMissing).toHaveBeenCalledWith(districtsCollection, ...additionalDistricts);
      expect(comp.districtsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const wards: IWards = { code: 'CBA' };
      const administrativeRegions: IAdministrativeRegions = { id: 71435 };
      wards.administrativeRegions = administrativeRegions;
      const districts: IDistricts = { code: '45678b6f-316a-4c25-9f1e-ec21b0bb2a37' };
      wards.districts = districts;

      activatedRoute.data = of({ wards });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(wards));
      expect(comp.administrativeRegionsSharedCollection).toContain(administrativeRegions);
      expect(comp.districtsSharedCollection).toContain(districts);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Wards>>();
      const wards = { code: 'ABC' };
      jest.spyOn(wardsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ wards });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: wards }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(wardsService.update).toHaveBeenCalledWith(wards);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Wards>>();
      const wards = new Wards();
      jest.spyOn(wardsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ wards });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: wards }));
      saveSubject.complete();

      // THEN
      expect(wardsService.create).toHaveBeenCalledWith(wards);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Wards>>();
      const wards = { code: 'ABC' };
      jest.spyOn(wardsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ wards });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(wardsService.update).toHaveBeenCalledWith(wards);
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

    describe('trackDistrictsByCode', () => {
      it('Should return tracked Districts primary key', () => {
        const entity = { code: 'ABC' };
        const trackResult = comp.trackDistrictsByCode(0, entity);
        expect(trackResult).toEqual(entity.code);
      });
    });
  });
});
