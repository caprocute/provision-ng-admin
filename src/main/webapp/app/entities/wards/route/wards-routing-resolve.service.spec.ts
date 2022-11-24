import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IWards, Wards } from '../wards.model';
import { WardsService } from '../service/wards.service';

import { WardsRoutingResolveService } from './wards-routing-resolve.service';

describe('Wards routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: WardsRoutingResolveService;
  let service: WardsService;
  let resultWards: IWards | undefined;

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
    routingResolveService = TestBed.inject(WardsRoutingResolveService);
    service = TestBed.inject(WardsService);
    resultWards = undefined;
  });

  describe('resolve', () => {
    it('should return IWards returned by find', () => {
      // GIVEN
      service.find = jest.fn(code => of(new HttpResponse({ body: { code } })));
      mockActivatedRouteSnapshot.params = { code: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultWards = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultWards).toEqual({ code: 'ABC' });
    });

    it('should return new IWards if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultWards = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultWards).toEqual(new Wards());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Wards })));
      mockActivatedRouteSnapshot.params = { code: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultWards = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultWards).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
