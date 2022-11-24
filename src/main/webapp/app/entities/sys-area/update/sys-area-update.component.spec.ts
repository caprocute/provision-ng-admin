import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SysAreaService } from '../service/sys-area.service';
import { ISysArea, SysArea } from '../sys-area.model';

import { SysAreaUpdateComponent } from './sys-area-update.component';

describe('SysArea Management Update Component', () => {
  let comp: SysAreaUpdateComponent;
  let fixture: ComponentFixture<SysAreaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sysAreaService: SysAreaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SysAreaUpdateComponent],
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
      .overrideTemplate(SysAreaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SysAreaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sysAreaService = TestBed.inject(SysAreaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sysArea: ISysArea = { id: 456 };

      activatedRoute.data = of({ sysArea });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(sysArea));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SysArea>>();
      const sysArea = { id: 123 };
      jest.spyOn(sysAreaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sysArea });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sysArea }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(sysAreaService.update).toHaveBeenCalledWith(sysArea);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SysArea>>();
      const sysArea = new SysArea();
      jest.spyOn(sysAreaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sysArea });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sysArea }));
      saveSubject.complete();

      // THEN
      expect(sysAreaService.create).toHaveBeenCalledWith(sysArea);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SysArea>>();
      const sysArea = { id: 123 };
      jest.spyOn(sysAreaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sysArea });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sysAreaService.update).toHaveBeenCalledWith(sysArea);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
