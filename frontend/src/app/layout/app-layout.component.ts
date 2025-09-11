import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from '../core/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-layout',
  imports: [CommonModule, RouterLink, RouterOutlet],
  template: `
  <div class="min-h-screen grid grid-cols-12">
    <aside class="col-span-12 md:col-span-3 lg:col-span-2 p-4 border-r space-y-2">
      <h1 class="text-xl font-bold">Danies Padel</h1>
      <a routerLink="/timeslots" class="block">Franjas</a>
      <a routerLink="/courts" class="block">Pistas</a>
      <a routerLink="/admin" class="block">Admin</a>
    </aside>
    <main class="col-span-12 md:col-span-9 lg:col-span-10 p-6">
      <div class="flex items-center justify-between mb-6">
        <div>
          <p class="text-sm opacity-70">Bienvenido</p>
          <h2 class="text-2xl font-semibold">{{ auth.me?.email }}</h2>
        </div>
        <button class="border px-3 py-2 rounded" (click)="logout()">Cerrar sesión</button>
      </div>
      <router-outlet />
    </main>
  </div>
  `
})
export class AppLayoutComponent {
  auth = inject(AuthService);
  logout() { this.auth.logout(); }
}
