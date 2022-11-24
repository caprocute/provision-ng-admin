import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IProvinces, Provinces } from '../provinces.model';
import { ProvincesService } from '../service/provinces.service';

import { ProvincesRoutingResolveService } from './provinces-routing-resolve.service';

describe('Provinces routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProvincesRoutingResolveService;
  let service: ProvincesService;
  let resultProvinces: IProvinces | undefined;

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
    routingResolveService = TestBed.inject(ProvincesRoutingResolveService);
    service = TestBed.inject(ProvincesService);
    resultProvinces = undefined;
  });

  describe('resolve', () => {
    it('should return IProvinces returned by find', () => {
      // GIVEN
      service.find = jest.fn(code => of(new HttpResponse({ body: { code } })));
      mockActivatedRouteSnapshot.params = { code: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProvinces = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProvinces).toEqual({ code: 'ABC' });
    });

    it('should return new IProvinces if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProvinces = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProvinces).toEqual(new Provinces());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Provinces })));
      mockActivatedRouteSnapshot.params = { code: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProvinces = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProvinces).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
