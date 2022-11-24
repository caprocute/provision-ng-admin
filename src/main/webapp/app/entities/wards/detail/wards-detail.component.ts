import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWards } from '../wards.model';

@Component({
  selector: 'jhi-wards-detail',
  templateUrl: './wards-detail.component.html',
})
export class WardsDetailComponent implements OnInit {
  wards: IWards | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wards }) => {
      this.wards = wards;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
