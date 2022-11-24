import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SysOrganizationAreaDetailComponent } from './sys-organization-area-detail.component';

describe('SysOrganizationArea Management Detail Component', () => {
  let comp: SysOrganizationAreaDetailComponent;
  let fixture: ComponentFixture<SysOrganizationAreaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SysOrganizationAreaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sysOrganizationArea: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SysOrganizationAreaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SysOrganizationAreaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sysOrganizationArea on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sysOrganizationArea).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
