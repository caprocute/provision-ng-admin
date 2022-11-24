import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISysCamera, SysCamera } from '../sys-camera.model';

import { SysCameraService } from './sys-camera.service';

describe('SysCamera Service', () => {
  let service: SysCameraService;
  let httpMock: HttpTestingController;
  let elemDefault: ISysCamera;
  let expectedResult: ISysCamera | ISysCamera[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SysCameraService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      camCode: 'AAAAAAA',
      camName: 'AAAAAAA',
      areaId: 0,
      branch: 'AAAAAAA',
      origin: 'AAAAAAA',
      description: 'AAAAAAA',
      isActive: false,
      status: false,
      locationLat: 0,
      locationLan: 0,
      urlSFTP: 'AAAAAAA',
      urlLiveStream: 'AAAAAAA',
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

    it('should create a SysCamera', () => {
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

      service.create(new SysCamera()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SysCamera', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          camCode: 'BBBBBB',
          camName: 'BBBBBB',
          areaId: 1,
          branch: 'BBBBBB',
          origin: 'BBBBBB',
          description: 'BBBBBB',
          isActive: true,
          status: true,
          locationLat: 1,
          locationLan: 1,
          urlSFTP: 'BBBBBB',
          urlLiveStream: 'BBBBBB',
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

    it('should partial update a SysCamera', () => {
      const patchObject = Object.assign(
        {
          camName: 'BBBBBB',
          areaId: 1,
          branch: 'BBBBBB',
          description: 'BBBBBB',
          status: true,
          locationLat: 1,
          urlSFTP: 'BBBBBB',
          urlLiveStream: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new SysCamera()
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

    it('should return a list of SysCamera', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          camCode: 'BBBBBB',
          camName: 'BBBBBB',
          areaId: 1,
          branch: 'BBBBBB',
          origin: 'BBBBBB',
          description: 'BBBBBB',
          isActive: true,
          status: true,
          locationLat: 1,
          locationLan: 1,
          urlSFTP: 'BBBBBB',
          urlLiveStream: 'BBBBBB',
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

    it('should delete a SysCamera', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSysCameraToCollectionIfMissing', () => {
      it('should add a SysCamera to an empty array', () => {
        const sysCamera: ISysCamera = { id: 123 };
        expectedResult = service.addSysCameraToCollectionIfMissing([], sysCamera);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sysCamera);
      });

      it('should not add a SysCamera to an array that contains it', () => {
        const sysCamera: ISysCamera = { id: 123 };
        const sysCameraCollection: ISysCamera[] = [
          {
            ...sysCamera,
          },
          { id: 456 },
        ];
        expectedResult = service.addSysCameraToCollectionIfMissing(sysCameraCollection, sysCamera);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SysCamera to an array that doesn't contain it", () => {
        const sysCamera: ISysCamera = { id: 123 };
        const sysCameraCollection: ISysCamera[] = [{ id: 456 }];
        expectedResult = service.addSysCameraToCollectionIfMissing(sysCameraCollection, sysCamera);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sysCamera);
      });

      it('should add only unique SysCamera to an array', () => {
        const sysCameraArray: ISysCamera[] = [{ id: 123 }, { id: 456 }, { id: 67882 }];
        const sysCameraCollection: ISysCamera[] = [{ id: 123 }];
        expectedResult = service.addSysCameraToCollectionIfMissing(sysCameraCollection, ...sysCameraArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sysCamera: ISysCamera = { id: 123 };
        const sysCamera2: ISysCamera = { id: 456 };
        expectedResult = service.addSysCameraToCollectionIfMissing([], sysCamera, sysCamera2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sysCamera);
        expect(expectedResult).toContain(sysCamera2);
      });

      it('should accept null and undefined values', () => {
        const sysCamera: ISysCamera = { id: 123 };
        expectedResult = service.addSysCameraToCollectionIfMissing([], null, sysCamera, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sysCamera);
      });

      it('should return initial array if no SysCamera is added', () => {
        const sysCameraCollection: ISysCamera[] = [{ id: 123 }];
        expectedResult = service.addSysCameraToCollectionIfMissing(sysCameraCollection, undefined, null);
        expect(expectedResult).toEqual(sysCameraCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
