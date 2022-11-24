import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DistrictsService } from '../service/districts.service';
import { IDistricts, Districts } from '../districts.model';
import { IProvinces } from 'app/entities/provinces/provinces.model';
import { ProvincesService } from 'app/entities/provinces/service/provinces.service';
import { IAdministrativeUnits } from 'app/entities/administrative-units/administrative-units.model';
import { AdministrativeUnitsService } from 'app/entities/administrative-units/service/administrative-units.service';

import { DistrictsUpdateComponent } from './districts-update.component';

describe('Districts Management Update Component', () => {
  let comp: DistrictsUpdateComponent;
  let fixture: ComponentFixture<DistrictsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let districtsService: DistrictsService;
  let provincesService: ProvincesService;
  let administrativeUnitsService: AdministrativeUnitsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DistrictsUpdateComponent],
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
      .overrideTemplate(DistrictsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DistrictsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    districtsService = TestBed.inject(DistrictsService);
    provincesService = TestBed.inject(ProvincesService);
    administrativeUnitsService = TestBed.inject(AdministrativeUnitsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Provinces query and add missing value', () => {
      const districts: IDistricts = { code: 'CBA' };
      const provinces: IProvinces = { code: '05fc73e1-0ba1-43f8-957c-4c8a4ba716ed' };
      districts.provinces = provinces;

      const provincesCollection: IProvinces[] = [{ code: 'eb049212-bade-45f3-9dfa-7779703b0438' }];
      jest.spyOn(provincesService, 'query').mockReturnValue(of(new HttpResponse({ body: provincesCollection })));
      const additionalProvinces = [provinces];
      const expectedCollection: IProvinces[] = [...additionalProvinces, ...provincesCollection];
      jest.spyOn(provincesService, 'addProvincesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ districts });
      comp.ngOnInit();

      expect(provincesService.query).toHaveBeenCalled();
      expect(provincesService.addProvincesToCollectionIfMissing).toHaveBeenCalledWith(provincesCollection, ...additionalProvinces);
      expect(comp.provincesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AdministrativeUnits query and add missing value', () => {
      const districts: IDistricts = { code: 'CBA' };
      const administrativeUnits: IAdministrativeUnits = { id: 28548 };
      districts.administrativeUnits = administrativeUnits;

      const administrativeUnitsCollection: IAdministrativeUnits[] = [{ id: 73252 }];
      jest.spyOn(administrativeUnitsService, 'query').mockReturnValue(of(new HttpResponse({ body: administrativeUnitsCollection })));
      const additionalAdministrativeUnits = [administrativeUnits];
      const expectedCollection: IAdministrativeUnits[] = [...additionalAdministrativeUnits, ...administrativeUnitsCollection];
      jest.spyOn(administrativeUnitsService, 'addAdministrativeUnitsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ districts });
      comp.ngOnInit();

      expect(administrativeUnitsService.query).toHaveBeenCalled();
      expect(administrativeUnitsService.addAdministrativeUnitsToCollectionIfMissing).toHaveBeenCalledWith(
        administrativeUnitsCollection,
        ...additionalAdministrativeUnits
      );
      expect(comp.administrativeUnitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const districts: IDistricts = { code: 'CBA' };
      const provinces: IProvinces = { code: '95d515f7-c687-4363-bd8a-5a91a40ab4a0' };
      districts.provinces = provinces;
      const administrativeUnits: IAdministrativeUnits = { id: 47548 };
      districts.administrativeUnits = administrativeUnits;

      activatedRoute.data = of({ districts });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(districts));
      expect(comp.provincesSharedCollection).toContain(provinces);
      expect(comp.administrativeUnitsSharedCollection).toContain(administrativeUnits);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Districts>>();
      const districts = { code: 'ABC' };
      jest.spyOn(districtsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ districts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: districts }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(districtsService.update).toHaveBeenCalledWith(districts);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Districts>>();
      const districts = new Districts();
      jest.spyOn(districtsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ districts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: districts }));
      saveSubject.complete();

      // THEN
      expect(districtsService.create).toHaveBeenCalledWith(districts);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Districts>>();
      const districts = { code: 'ABC' };
      jest.spyOn(districtsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ districts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(districtsService.update).toHaveBeenCalledWith(districts);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProvincesByCode', () => {
      it('Should return tracked Provinces primary key', () => {
        const entity = { code: 'ABC' };
        const trackResult = comp.trackProvincesByCode(0, entity);
        expect(trackResult).toEqual(entity.code);
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
