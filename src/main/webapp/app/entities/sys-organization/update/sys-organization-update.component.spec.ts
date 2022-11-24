import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SysOrganizationService } from '../service/sys-organization.service';
import { ISysOrganization, SysOrganization } from '../sys-organization.model';

import { SysOrganizationUpdateComponent } from './sys-organization-update.component';

describe('SysOrganization Management Update Component', () => {
  let comp: SysOrganizationUpdateComponent;
  let fixture: ComponentFixture<SysOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sysOrganizationService: SysOrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SysOrganizationUpdateComponent],
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
      .overrideTemplate(SysOrganizationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SysOrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sysOrganizationService = TestBed.inject(SysOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sysOrganization: ISysOrganization = { id: 456 };

      activatedRoute.data = of({ sysOrganization });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(sysOrganization));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SysOrganization>>();
      const sysOrganization = { id: 123 };
      jest.spyOn(sysOrganizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sysOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sysOrganization }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(sysOrganizationService.update).toHaveBeenCalledWith(sysOrganization);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SysOrganization>>();
      const sysOrganization = new SysOrganization();
      jest.spyOn(sysOrganizationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sysOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sysOrganization }));
      saveSubject.complete();

      // THEN
      expect(sysOrganizationService.create).toHaveBeenCalledWith(sysOrganization);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SysOrganization>>();
      const sysOrganization = { id: 123 };
      jest.spyOn(sysOrganizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sysOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sysOrganizationService.update).toHaveBeenCalledWith(sysOrganization);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
