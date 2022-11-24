import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SysCameraService } from '../service/sys-camera.service';
import { ISysCamera, SysCamera } from '../sys-camera.model';

import { SysCameraUpdateComponent } from './sys-camera-update.component';

describe('SysCamera Management Update Component', () => {
  let comp: SysCameraUpdateComponent;
  let fixture: ComponentFixture<SysCameraUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sysCameraService: SysCameraService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SysCameraUpdateComponent],
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
      .overrideTemplate(SysCameraUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SysCameraUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sysCameraService = TestBed.inject(SysCameraService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sysCamera: ISysCamera = { id: 456 };

      activatedRoute.data = of({ sysCamera });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(sysCamera));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SysCamera>>();
      const sysCamera = { id: 123 };
      jest.spyOn(sysCameraService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sysCamera });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sysCamera }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(sysCameraService.update).toHaveBeenCalledWith(sysCamera);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SysCamera>>();
      const sysCamera = new SysCamera();
      jest.spyOn(sysCameraService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sysCamera });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sysCamera }));
      saveSubject.complete();

      // THEN
      expect(sysCameraService.create).toHaveBeenCalledWith(sysCamera);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SysCamera>>();
      const sysCamera = { id: 123 };
      jest.spyOn(sysCameraService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sysCamera });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sysCameraService.update).toHaveBeenCalledWith(sysCamera);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
