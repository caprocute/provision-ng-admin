import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SysOrganizationDetailComponent } from './sys-organization-detail.component';

describe('SysOrganization Management Detail Component', () => {
  let comp: SysOrganizationDetailComponent;
  let fixture: ComponentFixture<SysOrganizationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SysOrganizationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sysOrganization: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SysOrganizationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SysOrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sysOrganization on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sysOrganization).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
