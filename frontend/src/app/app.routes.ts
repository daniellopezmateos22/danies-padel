import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  { path: 'login', loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent) },
  {
    path: '',
    canActivate: [authGuard],
    loadComponent: () => import('./layout/app-layout.component').then(m => m.AppLayoutComponent),
    children: [
      { path: '', redirectTo: 'timeslots', pathMatch: 'full' },
      { path: 'courts', loadComponent: () => import('./pages/courts/courts.component').then(m => m.CourtsComponent) },
      { path: 'timeslots', loadComponent: () => import('./pages/timeslots/timeslots.component').then(m => m.TimeSlotsComponent) },
      { path: 'admin', canActivate: [roleGuard(['ADMIN'])], loadComponent: () => import('./pages/courts/courts.component').then(m => m.CourtsComponent) }
    ]
  },
  { path: '**', redirectTo: '' }
];
