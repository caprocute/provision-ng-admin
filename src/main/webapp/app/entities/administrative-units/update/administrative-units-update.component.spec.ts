import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AdministrativeUnitsService } from '../service/administrative-units.service';
import { IAdministrativeUnits, AdministrativeUnits } from '../administrative-units.model';

import { AdministrativeUnitsUpdateComponent } from './administrative-units-update.component';

describe('AdministrativeUnits Management Update Component', () => {
  let comp: AdministrativeUnitsUpdateComponent;
  let fixture: ComponentFixture<AdministrativeUnitsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let administrativeUnitsService: AdministrativeUnitsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AdministrativeUnitsUpdateComponent],
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
      .overrideTemplate(AdministrativeUnitsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdministrativeUnitsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    administrativeUnitsService = TestBed.inject(AdministrativeUnitsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const administrativeUnits: IAdministrativeUnits = { id: 456 };

      activatedRoute.data = of({ administrativeUnits });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(administrativeUnits));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdministrativeUnits>>();
      const administrativeUnits = { id: 123 };
      jest.spyOn(administrativeUnitsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ administrativeUnits });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: administrativeUnits }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(administrativeUnitsService.update).toHaveBeenCalledWith(administrativeUnits);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdministrativeUnits>>();
      const administrativeUnits = new AdministrativeUnits();
      jest.spyOn(administrativeUnitsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ administrativeUnits });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: administrativeUnits }));
      saveSubject.complete();

      // THEN
      expect(administrativeUnitsService.create).toHaveBeenCalledWith(administrativeUnits);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AdministrativeUnits>>();
      const administrativeUnits = { id: 123 };
      jest.spyOn(administrativeUnitsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ administrativeUnits });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(administrativeUnitsService.update).toHaveBeenCalledWith(administrativeUnits);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
