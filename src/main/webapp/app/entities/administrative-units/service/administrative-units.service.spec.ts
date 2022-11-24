import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdministrativeUnits, AdministrativeUnits } from '../administrative-units.model';

import { AdministrativeUnitsService } from './administrative-units.service';

describe('AdministrativeUnits Service', () => {
  let service: AdministrativeUnitsService;
  let httpMock: HttpTestingController;
  let elemDefault: IAdministrativeUnits;
  let expectedResult: IAdministrativeUnits | IAdministrativeUnits[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AdministrativeUnitsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      fullName: 'AAAAAAA',
      fullNameEn: 'AAAAAAA',
      shortName: 'AAAAAAA',
      shortNameEn: 'AAAAAAA',
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

    it('should create a AdministrativeUnits', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AdministrativeUnits()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdministrativeUnits', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fullName: 'BBBBBB',
          fullNameEn: 'BBBBBB',
          shortName: 'BBBBBB',
          shortNameEn: 'BBBBBB',
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

    it('should partial update a AdministrativeUnits', () => {
      const patchObject = Object.assign(
        {
          fullName: 'BBBBBB',
          shortNameEn: 'BBBBBB',
          codeName: 'BBBBBB',
          codeNameEn: 'BBBBBB',
        },
        new AdministrativeUnits()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdministrativeUnits', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fullName: 'BBBBBB',
          fullNameEn: 'BBBBBB',
          shortName: 'BBBBBB',
          shortNameEn: 'BBBBBB',
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

    it('should delete a AdministrativeUnits', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAdministrativeUnitsToCollectionIfMissing', () => {
      it('should add a AdministrativeUnits to an empty array', () => {
        const administrativeUnits: IAdministrativeUnits = { id: 123 };
        expectedResult = service.addAdministrativeUnitsToCollectionIfMissing([], administrativeUnits);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(administrativeUnits);
      });

      it('should not add a AdministrativeUnits to an array that contains it', () => {
        const administrativeUnits: IAdministrativeUnits = { id: 123 };
        const administrativeUnitsCollection: IAdministrativeUnits[] = [
          {
            ...administrativeUnits,
          },
          { id: 456 },
        ];
        expectedResult = service.addAdministrativeUnitsToCollectionIfMissing(administrativeUnitsCollection, administrativeUnits);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdministrativeUnits to an array that doesn't contain it", () => {
        const administrativeUnits: IAdministrativeUnits = { id: 123 };
        const administrativeUnitsCollection: IAdministrativeUnits[] = [{ id: 456 }];
        expectedResult = service.addAdministrativeUnitsToCollectionIfMissing(administrativeUnitsCollection, administrativeUnits);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(administrativeUnits);
      });

      it('should add only unique AdministrativeUnits to an array', () => {
        const administrativeUnitsArray: IAdministrativeUnits[] = [{ id: 123 }, { id: 456 }, { id: 30042 }];
        const administrativeUnitsCollection: IAdministrativeUnits[] = [{ id: 123 }];
        expectedResult = service.addAdministrativeUnitsToCollectionIfMissing(administrativeUnitsCollection, ...administrativeUnitsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const administrativeUnits: IAdministrativeUnits = { id: 123 };
        const administrativeUnits2: IAdministrativeUnits = { id: 456 };
        expectedResult = service.addAdministrativeUnitsToCollectionIfMissing([], administrativeUnits, administrativeUnits2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(administrativeUnits);
        expect(expectedResult).toContain(administrativeUnits2);
      });

      it('should accept null and undefined values', () => {
        const administrativeUnits: IAdministrativeUnits = { id: 123 };
        expectedResult = service.addAdministrativeUnitsToCollectionIfMissing([], null, administrativeUnits, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(administrativeUnits);
      });

      it('should return initial array if no AdministrativeUnits is added', () => {
        const administrativeUnitsCollection: IAdministrativeUnits[] = [{ id: 123 }];
        expectedResult = service.addAdministrativeUnitsToCollectionIfMissing(administrativeUnitsCollection, undefined, null);
        expect(expectedResult).toEqual(administrativeUnitsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
