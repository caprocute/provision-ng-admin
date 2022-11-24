import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProvinces, Provinces } from '../provinces.model';

import { ProvincesService } from './provinces.service';

describe('Provinces Service', () => {
  let service: ProvincesService;
  let httpMock: HttpTestingController;
  let elemDefault: IProvinces;
  let expectedResult: IProvinces | IProvinces[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProvincesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      code: 'AAAAAAA',
      name: 'AAAAAAA',
      nameEn: 'AAAAAAA',
      fullName: 'AAAAAAA',
      fullNameEn: 'AAAAAAA',
      codeName: 'AAAAAAA',
      administrativeUnitId: 0,
      administrativeRegionId: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Provinces', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Provinces()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Provinces', () => {
      const returnedFromService = Object.assign(
        {
          code: 'BBBBBB',
          name: 'BBBBBB',
          nameEn: 'BBBBBB',
          fullName: 'BBBBBB',
          fullNameEn: 'BBBBBB',
          codeName: 'BBBBBB',
          administrativeUnitId: 1,
          administrativeRegionId: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Provinces', () => {
      const patchObject = Object.assign(
        {
          fullName: 'BBBBBB',
          codeName: 'BBBBBB',
          administrativeUnitId: 1,
          administrativeRegionId: 1,
        },
        new Provinces()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Provinces', () => {
      const returnedFromService = Object.assign(
        {
          code: 'BBBBBB',
          name: 'BBBBBB',
          nameEn: 'BBBBBB',
          fullName: 'BBBBBB',
          fullNameEn: 'BBBBBB',
          codeName: 'BBBBBB',
          administrativeUnitId: 1,
          administrativeRegionId: 1,
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

    it('should delete a Provinces', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProvincesToCollectionIfMissing', () => {
      it('should add a Provinces to an empty array', () => {
        const provinces: IProvinces = { code: 'ABC' };
        expectedResult = service.addProvincesToCollectionIfMissing([], provinces);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(provinces);
      });

      it('should not add a Provinces to an array that contains it', () => {
        const provinces: IProvinces = { code: 'ABC' };
        const provincesCollection: IProvinces[] = [
          {
            ...provinces,
          },
          { code: 'CBA' },
        ];
        expectedResult = service.addProvincesToCollectionIfMissing(provincesCollection, provinces);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Provinces to an array that doesn't contain it", () => {
        const provinces: IProvinces = { code: 'ABC' };
        const provincesCollection: IProvinces[] = [{ code: 'CBA' }];
        expectedResult = service.addProvincesToCollectionIfMissing(provincesCollection, provinces);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(provinces);
      });

      it('should add only unique Provinces to an array', () => {
        const provincesArray: IProvinces[] = [{ code: 'ABC' }, { code: 'CBA' }, { code: '4ed7ed70-b258-43c5-add6-35f3fa56fd00' }];
        const provincesCollection: IProvinces[] = [{ code: 'ABC' }];
        expectedResult = service.addProvincesToCollectionIfMissing(provincesCollection, ...provincesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const provinces: IProvinces = { code: 'ABC' };
        const provinces2: IProvinces = { code: 'CBA' };
        expectedResult = service.addProvincesToCollectionIfMissing([], provinces, provinces2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(provinces);
        expect(expectedResult).toContain(provinces2);
      });

      it('should accept null and undefined values', () => {
        const provinces: IProvinces = { code: 'ABC' };
        expectedResult = service.addProvincesToCollectionIfMissing([], null, provinces, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(provinces);
      });

      it('should return initial array if no Provinces is added', () => {
        const provincesCollection: IProvinces[] = [{ code: 'ABC' }];
        expectedResult = service.addProvincesToCollectionIfMissing(provincesCollection, undefined, null);
        expect(expectedResult).toEqual(provincesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
