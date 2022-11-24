import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISysArea, SysArea } from '../sys-area.model';

import { SysAreaService } from './sys-area.service';

describe('SysArea Service', () => {
  let service: SysAreaService;
  let httpMock: HttpTestingController;
  let elemDefault: ISysArea;
  let expectedResult: ISysArea | ISysArea[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SysAreaService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      areaCode: 'AAAAAAA',
      areaName: 'AAAAAAA',
      provinceCode: 'AAAAAAA',
      districtCode: 'AAAAAAA',
      wardCode: 'AAAAAAA',
      description: 'AAAAAAA',
      isActive: false,
      status: false,
      locationLat: 0,
      locationLan: 0,
      createdBy: 'AAAAAAA',
      createdDate: currentDate,
      lastModifiedBy: 'AAAAAAA',
      lastModifiedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a SysArea', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new SysArea()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SysArea', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          areaCode: 'BBBBBB',
          areaName: 'BBBBBB',
          provinceCode: 'BBBBBB',
          districtCode: 'BBBBBB',
          wardCode: 'BBBBBB',
          description: 'BBBBBB',
          isActive: true,
          status: true,
          locationLat: 1,
          locationLan: 1,
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SysArea', () => {
      const patchObject = Object.assign(
        {
          districtCode: 'BBBBBB',
          wardCode: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new SysArea()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SysArea', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          areaCode: 'BBBBBB',
          areaName: 'BBBBBB',
          provinceCode: 'BBBBBB',
          districtCode: 'BBBBBB',
          wardCode: 'BBBBBB',
          description: 'BBBBBB',
          isActive: true,
          status: true,
          locationLat: 1,
          locationLan: 1,
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a SysArea', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSysAreaToCollectionIfMissing', () => {
      it('should add a SysArea to an empty array', () => {
        const sysArea: ISysArea = { id: 123 };
        expectedResult = service.addSysAreaToCollectionIfMissing([], sysArea);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sysArea);
      });

      it('should not add a SysArea to an array that contains it', () => {
        const sysArea: ISysArea = { id: 123 };
        const sysAreaCollection: ISysArea[] = [
          {
            ...sysArea,
          },
          { id: 456 },
        ];
        expectedResult = service.addSysAreaToCollectionIfMissing(sysAreaCollection, sysArea);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SysArea to an array that doesn't contain it", () => {
        const sysArea: ISysArea = { id: 123 };
        const sysAreaCollection: ISysArea[] = [{ id: 456 }];
        expectedResult = service.addSysAreaToCollectionIfMissing(sysAreaCollection, sysArea);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sysArea);
      });

      it('should add only unique SysArea to an array', () => {
        const sysAreaArray: ISysArea[] = [{ id: 123 }, { id: 456 }, { id: 87098 }];
        const sysAreaCollection: ISysArea[] = [{ id: 123 }];
        expectedResult = service.addSysAreaToCollectionIfMissing(sysAreaCollection, ...sysAreaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sysArea: ISysArea = { id: 123 };
        const sysArea2: ISysArea = { id: 456 };
        expectedResult = service.addSysAreaToCollectionIfMissing([], sysArea, sysArea2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sysArea);
        expect(expectedResult).toContain(sysArea2);
      });

      it('should accept null and undefined values', () => {
        const sysArea: ISysArea = { id: 123 };
        expectedResult = service.addSysAreaToCollectionIfMissing([], null, sysArea, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sysArea);
      });

      it('should return initial array if no SysArea is added', () => {
        const sysAreaCollection: ISysArea[] = [{ id: 123 }];
        expectedResult = service.addSysAreaToCollectionIfMissing(sysAreaCollection, undefined, null);
        expect(expectedResult).toEqual(sysAreaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
