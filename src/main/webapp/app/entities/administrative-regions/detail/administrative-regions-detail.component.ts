import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdministrativeRegions } from '../administrative-regions.model';

@Component({
  selector: 'jhi-administrative-regions-detail',
  templateUrl: './administrative-regions-detail.component.html',
})
export class AdministrativeRegionsDetailComponent implements OnInit {
  administrativeRegions: IAdministrativeRegions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ administrativeRegions }) => {
      this.administrativeRegions = administrativeRegions;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
