import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDistricts, Districts } from '../districts.model';

import { DistrictsService } from './districts.service';

describe('Districts Service', () => {
  let service: DistrictsService;
  let httpMock: HttpTestingController;
  let elemDefault: IDistricts;
  let expectedResult: IDistricts | IDistricts[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DistrictsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      code: 'AAAAAAA',
      name: 'AAAAAAA',
      nameEn: 'AAAAAAA',
      fullName: 'AAAAAAA',
      fullNameEn: 'AAAAAAA',
      codeName: 'AAAAAAA',
      provinceCode: 'AAAAAAA',
      administrativeUnitId: 0,
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

    it('should create a Districts', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Districts()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Districts', () => {
      const returnedFromService = Object.assign(
        {
          code: 'BBBBBB',
          name: 'BBBBBB',
          nameEn: 'BBBBBB',
          fullName: 'BBBBBB',
          fullNameEn: 'BBBBBB',
          codeName: 'BBBBBB',
          provinceCode: 'BBBBBB',
          administrativeUnitId: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Districts', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          nameEn: 'BBBBBB',
          codeName: 'BBBBBB',
          administrativeUnitId: 1,
        },
        new Districts()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Districts', () => {
      const returnedFromService = Object.assign(
        {
          code: 'BBBBBB',
          name: 'BBBBBB',
          nameEn: 'BBBBBB',
          fullName: 'BBBBBB',
          fullNameEn: 'BBBBBB',
          codeName: 'BBBBBB',
          provinceCode: 'BBBBBB',
          administrativeUnitId: 1,
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

    it('should delete a Districts', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDistrictsToCollectionIfMissing', () => {
      it('should add a Districts to an empty array', () => {
        const districts: IDistricts = { code: 'ABC' };
        expectedResult = service.addDistrictsToCollectionIfMissing([], districts);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(districts);
      });

      it('should not add a Districts to an array that contains it', () => {
        const districts: IDistricts = { code: 'ABC' };
        const districtsCollection: IDistricts[] = [
          {
            ...districts,
          },
          { code: 'CBA' },
        ];
        expectedResult = service.addDistrictsToCollectionIfMissing(districtsCollection, districts);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Districts to an array that doesn't contain it", () => {
        const districts: IDistricts = { code: 'ABC' };
        const districtsCollection: IDistricts[] = [{ code: 'CBA' }];
        expectedResult = service.addDistrictsToCollectionIfMissing(districtsCollection, districts);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(districts);
      });

      it('should add only unique Districts to an array', () => {
        const districtsArray: IDistricts[] = [{ code: 'ABC' }, { code: 'CBA' }, { code: '2621602f-c046-4c44-822c-40acc40b1b86' }];
        const districtsCollection: IDistricts[] = [{ code: 'ABC' }];
        expectedResult = service.addDistrictsToCollectionIfMissing(districtsCollection, ...districtsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const districts: IDistricts = { code: 'ABC' };
        const districts2: IDistricts = { code: 'CBA' };
        expectedResult = service.addDistrictsToCollectionIfMissing([], districts, districts2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(districts);
        expect(expectedResult).toContain(districts2);
      });

      it('should accept null and undefined values', () => {
        const districts: IDistricts = { code: 'ABC' };
        expectedResult = service.addDistrictsToCollectionIfMissing([], null, districts, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(districts);
      });

      it('should return initial array if no Districts is added', () => {
        const districtsCollection: IDistricts[] = [{ code: 'ABC' }];
        expectedResult = service.addDistrictsToCollectionIfMissing(districtsCollection, undefined, null);
        expect(expectedResult).toEqual(districtsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
