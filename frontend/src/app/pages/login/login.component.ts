import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  template: `
  <div class="min-h-screen grid place-items-center p-4">
    <form (ngSubmit)="onSubmit()" class="w-full max-w-md space-y-3 border p-6 rounded">
      <h2 class="text-xl font-semibold">Iniciar sesión</h2>
      <label class="block">
        <span>Email</span>
        <input class="border w-full p-2 rounded" type="email" [(ngModel)]="email" name="email" required>
      </label>
      <label class="block">
        <span>Contraseña</span>
        <input class="border w-full p-2 rounded" type="password" [(ngModel)]="password" name="password" required>
      </label>
      <p class="text-sm text-red-600" *ngIf="error">{{ error }}</p>
      <button class="border w-full p-2 rounded" [disabled]="loading">{{ loading ? 'Entrando…' : 'Entrar' }}</button>
    </form>
  </div>
  `
})
export class LoginComponent {
  private auth = inject(AuthService);
  private router = inject(Router);

  email = 'admin@daniespadel.com';
  password = 'admin123';
  loading = false;
  error: string | null = null;

  onSubmit() {
    this.loading = true; this.error = null;
    this.auth.login(this.email, this.password).subscribe({
      next: () => this.router.navigateByUrl('/'),
      error: (e) => { this.error = e?.error || 'Error al iniciar sesión'; this.loading = false; }
    });
  }
}
