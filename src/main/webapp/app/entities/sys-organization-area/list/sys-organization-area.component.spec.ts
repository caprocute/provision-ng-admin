import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SysOrganizationAreaService } from '../service/sys-organization-area.service';

import { SysOrganizationAreaComponent } from './sys-organization-area.component';

describe('SysOrganizationArea Management Component', () => {
  let comp: SysOrganizationAreaComponent;
  let fixture: ComponentFixture<SysOrganizationAreaComponent>;
  let service: SysOrganizationAreaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SysOrganizationAreaComponent],
    })
      .overrideTemplate(SysOrganizationAreaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SysOrganizationAreaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SysOrganizationAreaService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.sysOrganizationAreas?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
