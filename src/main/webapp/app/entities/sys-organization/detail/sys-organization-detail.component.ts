import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysOrganization } from '../sys-organization.model';

@Component({
  selector: 'jhi-sys-organization-detail',
  templateUrl: './sys-organization-detail.component.html',
})
export class SysOrganizationDetailComponent implements OnInit {
  sysOrganization: ISysOrganization | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sysOrganization }) => {
      this.sysOrganization = sysOrganization;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
