import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DistrictsDetailComponent } from './districts-detail.component';

describe('Districts Management Detail Component', () => {
  let comp: DistrictsDetailComponent;
  let fixture: ComponentFixture<DistrictsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DistrictsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ districts: { code: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(DistrictsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DistrictsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load districts on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.districts).toEqual(expect.objectContaining({ code: 'ABC' }));
    });
  });
});
