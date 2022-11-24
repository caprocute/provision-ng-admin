import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProvinces } from '../provinces.model';

@Component({
  selector: 'jhi-provinces-detail',
  templateUrl: './provinces-detail.component.html',
})
export class ProvincesDetailComponent implements OnInit {
  provinces: IProvinces | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ provinces }) => {
      this.provinces = provinces;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
