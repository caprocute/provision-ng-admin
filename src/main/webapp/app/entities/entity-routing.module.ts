import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'administrative-regions',
        data: { pageTitle: 'provisionApp.administrativeRegions.home.title' },
        loadChildren: () => import('./administrative-regions/administrative-regions.module').then(m => m.AdministrativeRegionsModule),
      },
      {
        path: 'administrative-units',
        data: { pageTitle: 'provisionApp.administrativeUnits.home.title' },
        loadChildren: () => import('./administrative-units/administrative-units.module').then(m => m.AdministrativeUnitsModule),
      },
      {
        path: 'provinces',
        data: { pageTitle: 'provisionApp.provinces.home.title' },
        loadChildren: () => import('./provinces/provinces.module').then(m => m.ProvincesModule),
      },
      {
        path: 'districts',
        data: { pageTitle: 'provisionApp.districts.home.title' },
        loadChildren: () => import('./districts/districts.module').then(m => m.DistrictsModule),
      },
      {
        path: 'wards',
        data: { pageTitle: 'provisionApp.wards.home.title' },
        loadChildren: () => import('./wards/wards.module').then(m => m.WardsModule),
      },
      {
        path: 'sys-area',
        data: { pageTitle: 'provisionApp.sysArea.home.title' },
        loadChildren: () => import('./sys-area/sys-area.module').then(m => m.SysAreaModule),
      },
      {
        path: 'sys-camera',
        data: { pageTitle: 'provisionApp.sysCamera.home.title' },
        loadChildren: () => import('./sys-camera/sys-camera.module').then(m => m.SysCameraModule),
      },
      {
        path: 'sys-organization',
        data: { pageTitle: 'provisionApp.sysOrganization.home.title' },
        loadChildren: () => import('./sys-organization/sys-organization.module').then(m => m.SysOrganizationModule),
      },
      {
        path: 'sys-organization-area',
        data: { pageTitle: 'provisionApp.sysOrganizationArea.home.title' },
        loadChildren: () => import('./sys-organization-area/sys-organization-area.module').then(m => m.SysOrganizationAreaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
