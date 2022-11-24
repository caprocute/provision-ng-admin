import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdministrativeRegions, AdministrativeRegions } from '../administrative-regions.model';

import { AdministrativeRegionsService } from './administrative-regions.service';

describe('AdministrativeRegions Service', () => {
  let service: AdministrativeRegionsService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdministrativeRegions;
  let expectedResult: IAdministrativeRegions | IAdministrativeRegions[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdministrativeRegionsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      nameEn: 'AAAAAAA',
      codeName: 'AAAAAAA',
      codeNameEn: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a AdministrativeRegions', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdministrativeRegions()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdministrativeRegions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          nameEn: 'BBBBBB',
          codeName: 'BBBBBB',
          codeNameEn: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdministrativeRegions', () => {
      const patchObject = Object.assign(
        {
          codeName: 'BBBBBB',
          codeNameEn: 'BBBBBB',
        },
        new AdministrativeRegions()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdministrativeRegions', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          nameEn: 'BBBBBB',
          codeName: 'BBBBBB',
          codeNameEn: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a AdministrativeRegions', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdministrativeRegionsToCollectionIfMissing', () => {
      it('should add a AdministrativeRegions to an empty array', () => {
        const administrativeRegions: IAdministrativeRegions = { id: 123 };
        expectedResult = service.addAdministrativeRegionsToCollectionIfMissing([], administrativeRegions);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(administrativeRegions);
      });

      it('should not add a AdministrativeRegions to an array that contains it', () => {
        const administrativeRegions: IAdministrativeRegions = { id: 123 };
        const administrativeRegionsCollection: IAdministrativeRegions[] = [
          {
            ...administrativeRegions,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdministrativeRegionsToCollectionIfMissing(administrativeRegionsCollection, administrativeRegions);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdministrativeRegions to an array that doesn't contain it", () => {
        const administrativeRegions: IAdministrativeRegions = { id: 123 };
        const administrativeRegionsCollection: IAdministrativeRegions[] = [{ id: 456 }];
        expectedResult = service.addAdministrativeRegionsToCollectionIfMissing(administrativeRegionsCollection, administrativeRegions);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(administrativeRegions);
      });

      it('should add only unique AdministrativeRegions to an array', () => {
        const administrativeRegionsArray: IAdministrativeRegions[] = [{ id: 123 }, { id: 456 }, { id: 44070 }];
        const administrativeRegionsCollection: IAdministrativeRegions[] = [{ id: 123 }];
        expectedResult = service.addAdministrativeRegionsToCollectionIfMissing(
          administrativeRegionsCollection,
          ...administrativeRegionsArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const administrativeRegions: IAdministrativeRegions = { id: 123 };
        const administrativeRegions2: IAdministrativeRegions = { id: 456 };
        expectedResult = service.addAdministrativeRegionsToCollectionIfMissing([], administrativeRegions, administrativeRegions2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(administrativeRegions);
        expect(expectedResult).toContain(administrativeRegions2);
      });

      it('should accept null and undefined values', () => {
        const administrativeRegions: IAdministrativeRegions = { id: 123 };
        expectedResult = service.addAdministrativeRegionsToCollectionIfMissing([], null, administrativeRegions, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(administrativeRegions);
      });

      it('should return initial array if no AdministrativeRegions is added', () => {
        const administrativeRegionsCollection: IAdministrativeRegions[] = [{ id: 123 }];
        expectedResult = service.addAdministrativeRegionsToCollectionIfMissing(administrativeRegionsCollection, undefined, null);
        expect(expectedResult).toEqual(administrativeRegionsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
