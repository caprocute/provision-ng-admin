import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WardsDetailComponent } from './wards-detail.component';

describe('Wards Management Detail Component', () => {
  let comp: WardsDetailComponent;
  let fixture: ComponentFixture<WardsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WardsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ wards: { code: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(WardsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(WardsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load wards on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.wards).toEqual(expect.objectContaining({ code: 'ABC' }));
    });
  });
});
