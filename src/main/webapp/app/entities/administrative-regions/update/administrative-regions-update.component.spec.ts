import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdministrativeRegionsService } from '../service/administrative-regions.service';
import { IAdministrativeRegions, AdministrativeRegions } from '../administrative-regions.model';

import { AdministrativeRegionsUpdateComponent } from './administrative-regions-update.component';

describe('AdministrativeRegions Management Update Component', () => {
  let comp: AdministrativeRegionsUpdateComponent;
  let fixture: ComponentFixture<AdministrativeRegionsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let administrativeRegionsService: AdministrativeRegionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdministrativeRegionsUpdateComponent],
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
      .overrideTemplate(AdministrativeRegionsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdministrativeRegionsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    administrativeRegionsService = TestBed.inject(AdministrativeRegionsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const administrativeRegions: IAdministrativeRegions = { id: 456 };

      activatedRoute.data = of({ administrativeRegions });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(administrativeRegions));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdministrativeRegions>>();
      const administrativeRegions = { id: 123 };
      jest.spyOn(administrativeRegionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ administrativeRegions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: administrativeRegions }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(administrativeRegionsService.update).toHaveBeenCalledWith(administrativeRegions);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdministrativeRegions>>();
      const administrativeRegions = new AdministrativeRegions();
      jest.spyOn(administrativeRegionsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ administrativeRegions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: administrativeRegions }));
      saveSubject.complete();

      // THEN
      expect(administrativeRegionsService.create).toHaveBeenCalledWith(administrativeRegions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdministrativeRegions>>();
      const administrativeRegions = { id: 123 };
      jest.spyOn(administrativeRegionsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ administrativeRegions });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(administrativeRegionsService.update).toHaveBeenCalledWith(administrativeRegions);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
