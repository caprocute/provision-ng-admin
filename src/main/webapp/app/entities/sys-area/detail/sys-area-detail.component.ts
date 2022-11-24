import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysArea } from '../sys-area.model';

@Component({
  selector: 'jhi-sys-area-detail',
  templateUrl: './sys-area-detail.component.html',
})
export class SysAreaDetailComponent implements OnInit {
  sysArea: ISysArea | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sysArea }) => {
      this.sysArea = sysArea;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
