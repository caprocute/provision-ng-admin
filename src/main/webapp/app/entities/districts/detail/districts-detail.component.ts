import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDistricts } from '../districts.model';

@Component({
  selector: 'jhi-districts-detail',
  templateUrl: './districts-detail.component.html',
})
export class DistrictsDetailComponent implements OnInit {
  districts: IDistricts | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ districts }) => {
      this.districts = districts;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
