import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysCamera } from '../sys-camera.model';

@Component({
  selector: 'jhi-sys-camera-detail',
  templateUrl: './sys-camera-detail.component.html',
})
export class SysCameraDetailComponent implements OnInit {
  sysCamera: ISysCamera | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sysCamera }) => {
      this.sysCamera = sysCamera;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
