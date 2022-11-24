import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IWards, Wards } from '../wards.model';

import { WardsService } from './wards.service';

describe('Wards Service', () => {
  let service: WardsService;
  let httpMock: HttpTestingController;
  let elemDefault: IWards;
  let expectedResult: IWards | IWards[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WardsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      code: 'AAAAAAA',
      name: 'AAAAAAA',
      nameEn: 'AAAAAAA',
      fullName: 'AAAAAAA',
      fullNameEn: 'AAAAAAA',
      codeName: 'AAAAAAA',
      districtCode: 'AAAAAAA',
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

    it('should create a Wards', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Wards()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Wards', () => {
      const returnedFromService = Object.assign(
        {
          code: 'BBBBBB',
          name: 'BBBBBB',
          nameEn: 'BBBBBB',
          fullName: 'BBBBBB',
          fullNameEn: 'BBBBBB',
          codeName: 'BBBBBB',
          districtCode: 'BBBBBB',
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

    it('should partial update a Wards', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          nameEn: 'BBBBBB',
          fullNameEn: 'BBBBBB',
        },
        new Wards()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Wards', () => {
      const returnedFromService = Object.assign(
        {
          code: 'BBBBBB',
          name: 'BBBBBB',
          nameEn: 'BBBBBB',
          fullName: 'BBBBBB',
          fullNameEn: 'BBBBBB',
          codeName: 'BBBBBB',
          districtCode: 'BBBBBB',
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

    it('should delete a Wards', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addWardsToCollectionIfMissing', () => {
      it('should add a Wards to an empty array', () => {
        const wards: IWards = { code: 'ABC' };
        expectedResult = service.addWardsToCollectionIfMissing([], wards);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(wards);
      });

      it('should not add a Wards to an array that contains it', () => {
        const wards: IWards = { code: 'ABC' };
        const wardsCollection: IWards[] = [
          {
            ...wards,
          },
          { code: 'CBA' },
        ];
        expectedResult = service.addWardsToCollectionIfMissing(wardsCollection, wards);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Wards to an array that doesn't contain it", () => {
        const wards: IWards = { code: 'ABC' };
        const wardsCollection: IWards[] = [{ code: 'CBA' }];
        expectedResult = service.addWardsToCollectionIfMissing(wardsCollection, wards);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(wards);
      });

      it('should add only unique Wards to an array', () => {
        const wardsArray: IWards[] = [{ code: 'ABC' }, { code: 'CBA' }, { code: '28e2b4ee-fe5f-46cf-a6c9-09fff953bf4a' }];
        const wardsCollection: IWards[] = [{ code: 'ABC' }];
        expectedResult = service.addWardsToCollectionIfMissing(wardsCollection, ...wardsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const wards: IWards = { code: 'ABC' };
        const wards2: IWards = { code: 'CBA' };
        expectedResult = service.addWardsToCollectionIfMissing([], wards, wards2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(wards);
        expect(expectedResult).toContain(wards2);
      });

      it('should accept null and undefined values', () => {
        const wards: IWards = { code: 'ABC' };
        expectedResult = service.addWardsToCollectionIfMissing([], null, wards, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(wards);
      });

      it('should return initial array if no Wards is added', () => {
        const wardsCollection: IWards[] = [{ code: 'ABC' }];
        expectedResult = service.addWardsToCollectionIfMissing(wardsCollection, undefined, null);
        expect(expectedResult).toEqual(wardsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
