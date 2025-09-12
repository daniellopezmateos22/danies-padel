import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [
    CommonModule, FormsModule,
    MatCardModule, MatFormFieldModule, MatInputModule,
    MatButtonModule, MatProgressSpinnerModule, MatSnackBarModule
  ],
  styles: [`
    .wrapper { min-height: 100vh; display:grid; place-items:center; padding:16px; }
    mat-card { width: 100%; max-width: 420px; }
  `],
  template: `
  <div class="wrapper">
    <mat-card>
      <mat-card-title>Iniciar sesión</mat-card-title>
      <mat-card-content>
        <form (ngSubmit)="onSubmit()" autocomplete="on">
          <mat-form-field appearance="outline" style="width:100%">
            <mat-label>Email</mat-label>
            <input matInput type="email" [(ngModel)]="email" name="email" required>
          </mat-form-field>

          <mat-form-field appearance="outline" style="width:100%">
            <mat-label>Contraseña</mat-label>
            <input matInput type="password" [(ngModel)]="password" name="password" required>
          </mat-form-field>

          <div *ngIf="error" style="color:#c62828; margin:8px 0">{{ error }}</div>

          <button mat-raised-button color="primary" style="width:100%" [disabled]="loading">
            <ng-container *ngIf="!loading">Entrar</ng-container>
            <mat-spinner *ngIf="loading" diameter="20"></mat-spinner>
          </button>
        </form>
      </mat-card-content>
    </mat-card>
  </div>
  `
})
export class LoginComponent {
  private auth = inject(AuthService);
  private router = inject(Router);
  private snack = inject(MatSnackBar);

  email = 'admin@daniespadel.com';
  password = 'admin123';
  loading = false;
  error: string | null = null;

  onSubmit() {
    this.loading = true; this.error = null;
    this.auth.login(this.email, this.password).subscribe({
      next: () => this.router.navigateByUrl('/'),
      error: (e) => {
        this.error = `Login fallido`;
        this.snack.open(`Login fallido`, 'Cerrar', { duration: 3000 });
        this.loading = false;
      }
    });
  }
}
