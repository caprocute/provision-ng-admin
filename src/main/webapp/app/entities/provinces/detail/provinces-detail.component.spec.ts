import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvincesDetailComponent } from './provinces-detail.component';

describe('Provinces Management Detail Component', () => {
  let comp: ProvincesDetailComponent;
  let fixture: ComponentFixture<ProvincesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProvincesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ provinces: { code: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ProvincesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProvincesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load provinces on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.provinces).toEqual(expect.objectContaining({ code: 'ABC' }));
    });
  });
});
