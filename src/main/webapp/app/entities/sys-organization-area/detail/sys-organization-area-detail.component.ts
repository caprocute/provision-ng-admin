import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysOrganizationArea } from '../sys-organization-area.model';

@Component({
  selector: 'jhi-sys-organization-area-detail',
  templateUrl: './sys-organization-area-detail.component.html',
})
export class SysOrganizationAreaDetailComponent implements OnInit {
  sysOrganizationArea: ISysOrganizationArea | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sysOrganizationArea }) => {
      this.sysOrganizationArea = sysOrganizationArea;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
