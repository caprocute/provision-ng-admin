import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SysAreaDetailComponent } from './sys-area-detail.component';

describe('SysArea Management Detail Component', () => {
  let comp: SysAreaDetailComponent;
  let fixture: ComponentFixture<SysAreaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SysAreaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sysArea: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SysAreaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SysAreaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sysArea on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sysArea).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
