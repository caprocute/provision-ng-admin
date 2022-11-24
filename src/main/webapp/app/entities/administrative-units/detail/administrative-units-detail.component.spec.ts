import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdministrativeUnitsDetailComponent } from './administrative-units-detail.component';

describe('AdministrativeUnits Management Detail Component', () => {
  let comp: AdministrativeUnitsDetailComponent;
  let fixture: ComponentFixture<AdministrativeUnitsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdministrativeUnitsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ administrativeUnits: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AdministrativeUnitsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AdministrativeUnitsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load administrativeUnits on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.administrativeUnits).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
