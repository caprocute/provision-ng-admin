import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISysOrganizationArea, SysOrganizationArea } from '../sys-organization-area.model';

import { SysOrganizationAreaService } from './sys-organization-area.service';

describe('SysOrganizationArea Service', () => {
  let service: SysOrganizationAreaService;
  let httpMock: HttpTestingController;
  let elemDefault: ISysOrganizationArea;
  let expectedResult: ISysOrganizationArea | ISysOrganizationArea[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SysOrganizationAreaService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      orgId: 0,
      areaId: 'AAAAAAA',
      isActive: false,
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

    it('should create a SysOrganizationArea', () => {
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

      service.create(new SysOrganizationArea()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SysOrganizationArea', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          orgId: 1,
          areaId: 'BBBBBB',
          isActive: true,
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

    it('should partial update a SysOrganizationArea', () => {
      const patchObject = Object.assign(
        {
          orgId: 1,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new SysOrganizationArea()
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

    it('should return a list of SysOrganizationArea', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          orgId: 1,
          areaId: 'BBBBBB',
          isActive: true,
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

    it('should delete a SysOrganizationArea', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSysOrganizationAreaToCollectionIfMissing', () => {
      it('should add a SysOrganizationArea to an empty array', () => {
        const sysOrganizationArea: ISysOrganizationArea = { id: 123 };
        expectedResult = service.addSysOrganizationAreaToCollectionIfMissing([], sysOrganizationArea);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sysOrganizationArea);
      });

      it('should not add a SysOrganizationArea to an array that contains it', () => {
        const sysOrganizationArea: ISysOrganizationArea = { id: 123 };
        const sysOrganizationAreaCollection: ISysOrganizationArea[] = [
          {
            ...sysOrganizationArea,
          },
          { id: 456 },
        ];
        expectedResult = service.addSysOrganizationAreaToCollectionIfMissing(sysOrganizationAreaCollection, sysOrganizationArea);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SysOrganizationArea to an array that doesn't contain it", () => {
        const sysOrganizationArea: ISysOrganizationArea = { id: 123 };
        const sysOrganizationAreaCollection: ISysOrganizationArea[] = [{ id: 456 }];
        expectedResult = service.addSysOrganizationAreaToCollectionIfMissing(sysOrganizationAreaCollection, sysOrganizationArea);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sysOrganizationArea);
      });

      it('should add only unique SysOrganizationArea to an array', () => {
        const sysOrganizationAreaArray: ISysOrganizationArea[] = [{ id: 123 }, { id: 456 }, { id: 36287 }];
        const sysOrganizationAreaCollection: ISysOrganizationArea[] = [{ id: 123 }];
        expectedResult = service.addSysOrganizationAreaToCollectionIfMissing(sysOrganizationAreaCollection, ...sysOrganizationAreaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sysOrganizationArea: ISysOrganizationArea = { id: 123 };
        const sysOrganizationArea2: ISysOrganizationArea = { id: 456 };
        expectedResult = service.addSysOrganizationAreaToCollectionIfMissing([], sysOrganizationArea, sysOrganizationArea2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sysOrganizationArea);
        expect(expectedResult).toContain(sysOrganizationArea2);
      });

      it('should accept null and undefined values', () => {
        const sysOrganizationArea: ISysOrganizationArea = { id: 123 };
        expectedResult = service.addSysOrganizationAreaToCollectionIfMissing([], null, sysOrganizationArea, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sysOrganizationArea);
      });

      it('should return initial array if no SysOrganizationArea is added', () => {
        const sysOrganizationAreaCollection: ISysOrganizationArea[] = [{ id: 123 }];
        expectedResult = service.addSysOrganizationAreaToCollectionIfMissing(sysOrganizationAreaCollection, undefined, null);
        expect(expectedResult).toEqual(sysOrganizationAreaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
