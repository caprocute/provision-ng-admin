import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdministrativeUnits } from '../administrative-units.model';

@Component({
  selector: 'jhi-administrative-units-detail',
  templateUrl: './administrative-units-detail.component.html',
})
export class AdministrativeUnitsDetailComponent implements OnInit {
  administrativeUnits: IAdministrativeUnits | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ administrativeUnits }) => {
      this.administrativeUnits = administrativeUnits;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
