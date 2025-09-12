import { Component, inject, signal } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../core/services/auth.service';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  standalone: true,
  selector: 'app-layout',
  imports: [
    CommonModule, RouterLink, RouterOutlet,
    MatSidenavModule, MatToolbarModule, MatListModule, MatIconModule, MatButtonModule
  ],
  styles: [`
    .app-container { height: 100vh; }
    .app-toolbar-title { font-weight: 600; }
    .content { padding: 16px; }
    .spacer { flex: 1 1 auto; }
    a.mat-mdc-list-item { border-radius: 8px; }
  `],
  template: `
  <mat-sidenav-container class="app-container">
    <mat-sidenav mode="side" opened>
      <div style="padding:16px; font-weight:700; font-size:18px">Danies Padel</div>
      <mat-nav-list>
        <a mat-list-item routerLink="/timeslots" routerLinkActive="mdc-elevated">Franjas</a>
        <a mat-list-item routerLink="/courts" routerLinkActive="mdc-elevated">Pistas</a>
        <a mat-list-item routerLink="/admin" routerLinkActive="mdc-elevated">Admin</a>
      </mat-nav-list>
    </mat-sidenav>

    <mat-sidenav-content>
      <mat-toolbar color="primary">
        <span class="app-toolbar-title">Panel</span>
        <span class="spacer"></span>
        <span *ngIf="auth.me">{{ auth.me.email }}</span>
        <button mat-button (click)="logout()" *ngIf="auth.me">
          <mat-icon>logout</mat-icon> Cerrar sesión
        </button>
      </mat-toolbar>

      <div class="content">
        <router-outlet />
      </div>
    </mat-sidenav-content>
  </mat-sidenav-container>
  `
})
export class AppLayoutComponent {
  auth = inject(AuthService);
  logout() { this.auth.logout(); }
}
