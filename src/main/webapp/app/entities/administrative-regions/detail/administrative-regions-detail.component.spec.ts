import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdministrativeRegionsDetailComponent } from './administrative-regions-detail.component';

describe('AdministrativeRegions Management Detail Component', () => {
  let comp: AdministrativeRegionsDetailComponent;
  let fixture: ComponentFixture<AdministrativeRegionsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdministrativeRegionsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ administrativeRegions: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdministrativeRegionsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdministrativeRegionsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load administrativeRegions on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.administrativeRegions).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
