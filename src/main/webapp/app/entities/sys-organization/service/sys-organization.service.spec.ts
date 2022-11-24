import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISysOrganization, SysOrganization } from '../sys-organization.model';

import { SysOrganizationService } from './sys-organization.service';

describe('SysOrganization Service', () => {
  let service: SysOrganizationService;
  let httpMock: HttpTestingController;
  let elemDefault: ISysOrganization;
  let expectedResult: ISysOrganization | ISysOrganization[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SysOrganizationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      parentId: 0,
      sysOrgCode: 'AAAAAAA',
      sysOrgName: 'AAAAAAA',
      sysOrgPath: 'AAAAAAA',
      sysOrgFullPath: 'AAAAAAA',
      description: 'AAAAAAA',
      address: 'AAAAAAA',
      type: 0,
      tenantId: 0,
      sysOrgType: 0,
      isActive: false,
      status: false,
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

    it('should create a SysOrganization', () => {
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

      service.create(new SysOrganization()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SysOrganization', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          parentId: 1,
          sysOrgCode: 'BBBBBB',
          sysOrgName: 'BBBBBB',
          sysOrgPath: 'BBBBBB',
          sysOrgFullPath: 'BBBBBB',
          description: 'BBBBBB',
          address: 'BBBBBB',
          type: 1,
          tenantId: 1,
          sysOrgType: 1,
          isActive: true,
          status: true,
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

    it('should partial update a SysOrganization', () => {
      const patchObject = Object.assign(
        {
          sysOrgPath: 'BBBBBB',
          description: 'BBBBBB',
          tenantId: 1,
          sysOrgType: 1,
          createdBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new SysOrganization()
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

    it('should return a list of SysOrganization', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          parentId: 1,
          sysOrgCode: 'BBBBBB',
          sysOrgName: 'BBBBBB',
          sysOrgPath: 'BBBBBB',
          sysOrgFullPath: 'BBBBBB',
          description: 'BBBBBB',
          address: 'BBBBBB',
          type: 1,
          tenantId: 1,
          sysOrgType: 1,
          isActive: true,
          status: true,
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

    it('should delete a SysOrganization', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSysOrganizationToCollectionIfMissing', () => {
      it('should add a SysOrganization to an empty array', () => {
        const sysOrganization: ISysOrganization = { id: 123 };
        expectedResult = service.addSysOrganizationToCollectionIfMissing([], sysOrganization);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sysOrganization);
      });

      it('should not add a SysOrganization to an array that contains it', () => {
        const sysOrganization: ISysOrganization = { id: 123 };
        const sysOrganizationCollection: ISysOrganization[] = [
          {
            ...sysOrganization,
          },
          { id: 456 },
        ];
        expectedResult = service.addSysOrganizationToCollectionIfMissing(sysOrganizationCollection, sysOrganization);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SysOrganization to an array that doesn't contain it", () => {
        const sysOrganization: ISysOrganization = { id: 123 };
        const sysOrganizationCollection: ISysOrganization[] = [{ id: 456 }];
        expectedResult = service.addSysOrganizationToCollectionIfMissing(sysOrganizationCollection, sysOrganization);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sysOrganization);
      });

      it('should add only unique SysOrganization to an array', () => {
        const sysOrganizationArray: ISysOrganization[] = [{ id: 123 }, { id: 456 }, { id: 6348 }];
        const sysOrganizationCollection: ISysOrganization[] = [{ id: 123 }];
        expectedResult = service.addSysOrganizationToCollectionIfMissing(sysOrganizationCollection, ...sysOrganizationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sysOrganization: ISysOrganization = { id: 123 };
        const sysOrganization2: ISysOrganization = { id: 456 };
        expectedResult = service.addSysOrganizationToCollectionIfMissing([], sysOrganization, sysOrganization2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sysOrganization);
        expect(expectedResult).toContain(sysOrganization2);
      });

      it('should accept null and undefined values', () => {
        const sysOrganization: ISysOrganization = { id: 123 };
        expectedResult = service.addSysOrganizationToCollectionIfMissing([], null, sysOrganization, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sysOrganization);
      });

      it('should return initial array if no SysOrganization is added', () => {
        const sysOrganizationCollection: ISysOrganization[] = [{ id: 123 }];
        expectedResult = service.addSysOrganizationToCollectionIfMissing(sysOrganizationCollection, undefined, null);
        expect(expectedResult).toEqual(sysOrganizationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
