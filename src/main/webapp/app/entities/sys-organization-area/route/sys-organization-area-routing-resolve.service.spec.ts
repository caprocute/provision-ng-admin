import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISysOrganizationArea, SysOrganizationArea } from '../sys-organization-area.model';
import { SysOrganizationAreaService } from '../service/sys-organization-area.service';

import { SysOrganizationAreaRoutingResolveService } from './sys-organization-area-routing-resolve.service';

describe('SysOrganizationArea routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SysOrganizationAreaRoutingResolveService;
  let service: SysOrganizationAreaService;
  let resultSysOrganizationArea: ISysOrganizationArea | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(SysOrganizationAreaRoutingResolveService);
    service = TestBed.inject(SysOrganizationAreaService);
    resultSysOrganizationArea = undefined;
  });

  describe('resolve', () => {
    it('should return ISysOrganizationArea returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSysOrganizationArea = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSysOrganizationArea).toEqual({ id: 123 });
    });

    it('should return new ISysOrganizationArea if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSysOrganizationArea = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSysOrganizationArea).toEqual(new SysOrganizationArea());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SysOrganizationArea })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSysOrganizationArea = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSysOrganizationArea).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
