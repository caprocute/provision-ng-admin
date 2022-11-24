import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAdministrativeRegions, AdministrativeRegions } from '../administrative-regions.model';
import { AdministrativeRegionsService } from '../service/administrative-regions.service';

import { AdministrativeRegionsRoutingResolveService } from './administrative-regions-routing-resolve.service';

describe('AdministrativeRegions routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AdministrativeRegionsRoutingResolveService;
  let service: AdministrativeRegionsService;
  let resultAdministrativeRegions: IAdministrativeRegions | undefined;

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
    routingResolveService = TestBed.inject(AdministrativeRegionsRoutingResolveService);
    service = TestBed.inject(AdministrativeRegionsService);
    resultAdministrativeRegions = undefined;
  });

  describe('resolve', () => {
    it('should return IAdministrativeRegions returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdministrativeRegions = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdministrativeRegions).toEqual({ id: 123 });
    });

    it('should return new IAdministrativeRegions if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdministrativeRegions = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAdministrativeRegions).toEqual(new AdministrativeRegions());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AdministrativeRegions })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAdministrativeRegions = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAdministrativeRegions).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
