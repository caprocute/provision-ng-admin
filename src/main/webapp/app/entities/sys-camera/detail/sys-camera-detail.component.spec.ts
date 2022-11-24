import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SysCameraDetailComponent } from './sys-camera-detail.component';

describe('SysCamera Management Detail Component', () => {
  let comp: SysCameraDetailComponent;
  let fixture: ComponentFixture<SysCameraDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SysCameraDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sysCamera: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SysCameraDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SysCameraDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sysCamera on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sysCamera).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
