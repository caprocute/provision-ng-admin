import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SysOrganizationAreaService } from '../service/sys-organization-area.service';
import { ISysOrganizationArea, SysOrganizationArea } from '../sys-organization-area.model';

import { SysOrganizationAreaUpdateComponent } from './sys-organization-area-update.component';

describe('SysOrganizationArea Management Update Component', () => {
  let comp: SysOrganizationAreaUpdateComponent;
  let fixture: ComponentFixture<SysOrganizationAreaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sysOrganizationAreaService: SysOrganizationAreaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SysOrganizationAreaUpdateComponent],
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
      .overrideTemplate(SysOrganizationAreaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SysOrganizationAreaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sysOrganizationAreaService = TestBed.inject(SysOrganizationAreaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sysOrganizationArea: ISysOrganizationArea = { id: 456 };

      activatedRoute.data = of({ sysOrganizationArea });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(sysOrganizationArea));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SysOrganizationArea>>();
      const sysOrganizationArea = { id: 123 };
      jest.spyOn(sysOrganizationAreaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sysOrganizationArea });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sysOrganizationArea }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(sysOrganizationAreaService.update).toHaveBeenCalledWith(sysOrganizationArea);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SysOrganizationArea>>();
      const sysOrganizationArea = new SysOrganizationArea();
      jest.spyOn(sysOrganizationAreaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sysOrganizationArea });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sysOrganizationArea }));
      saveSubject.complete();

      // THEN
      expect(sysOrganizationAreaService.create).toHaveBeenCalledWith(sysOrganizationArea);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SysOrganizationArea>>();
      const sysOrganizationArea = { id: 123 };
      jest.spyOn(sysOrganizationAreaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sysOrganizationArea });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sysOrganizationAreaService.update).toHaveBeenCalledWith(sysOrganizationArea);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
