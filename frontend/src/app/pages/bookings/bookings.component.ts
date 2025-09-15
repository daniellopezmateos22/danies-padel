import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BookingsService, Booking } from '../../core/services/bookings.service';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';

function toISODate(d: Date) { return d.toISOString().slice(0,10); }
function toLocal(dt: string) { return new Date(dt).toLocaleString(); }

@Component({
  standalone: true,
  selector: 'app-bookings',
  imports: [
    CommonModule, FormsModule,
    MatTableModule, MatButtonModule, MatSnackBarModule,
    MatDatepickerModule, MatNativeDateModule, MatFormFieldModule, MatCardModule
  ],
  template: `
  <mat-card>
    <mat-card-title>Mis reservas</mat-card-title>
    <mat-card-content>
      <div style="display:flex; gap:12px; align-items:center; margin-bottom:12px;">
        <mat-form-field appearance="outline">
          <mat-label>Fecha</mat-label>
          <input matInput [matDatepicker]="picker" [value]="date" (dateChange)="onDate($event.value)">
          <mat-datepicker-toggle matSuffix [for]="picker"></mat-datepicker-toggle>
          <mat-datepicker #picker></mat-datepicker>
        </mat-form-field>
        <button mat-raised-button color="primary" (click)="load()">Actualizar</button>
      </div>

      <table mat-table [dataSource]="rows" *ngIf="rows.length; else empty">
        <ng-container matColumnDef="startsAt">
          <th mat-header-cell *matHeaderCellDef> Inicio </th>
          <td mat-cell *matCellDef="let r"> {{ toLocal(r.startsAt) }} </td>
        </ng-container>

        <ng-container matColumnDef="endsAt">
          <th mat-header-cell *matHeaderCellDef> Fin </th>
          <td mat-cell *matCellDef="let r"> {{ toLocal(r.endsAt) }} </td>
        </ng-container>

        <ng-container matColumnDef="courtId">
          <th mat-header-cell *matHeaderCellDef> Pista </th>
          <td mat-cell *matCellDef="let r"> {{ r.courtId }} </td>
        </ng-container>

        <ng-container matColumnDef="status">
          <th mat-header-cell *matHeaderCellDef> Estado </th>
          <td mat-cell *matCellDef="let r"> {{ r.status }} </td>
        </ng-container>

        <ng-container matColumnDef="actions">
          <th mat-header-cell *matHeaderCellDef> </th>
          <td mat-cell *matCellDef="let r">
            <button mat-stroked-button color="warn" (click)="cancel(r)" [disabled]="r.status !== 'CONFIRMED'">Cancelar</button>
          </td>
        </ng-container>

        <tr mat-header-row *matHeaderRowDef="cols"></tr>
        <tr mat-row *matRowDef="let row; columns: cols;"></tr>
      </table>

      <ng-template #empty>
        <div style="opacity:.7">No hay reservas para esta fecha.</div>
      </ng-template>
    </mat-card-content>
  </mat-card>
  `
})
export class BookingsComponent {
  private api = inject(BookingsService);
  private snack = inject(MatSnackBar);

  date: Date = new Date();
  rows: Booking[] = [];
  cols = ['startsAt','endsAt','courtId','status','actions'];

  toLocal = toLocal;

  ngOnInit(){ this.load(); }

  onDate(d: Date | null){ if (d){ this.date = d; this.load(); } }

  load(){
    this.api.listByDate(toISODate(this.date)).subscribe({
      next: list => this.rows = list,
      error: e => this.snack.open('Error cargando reservas', 'Cerrar', { duration: 2500 })
    });
  }

  cancel(r: Booking){
    const ok = confirm('¿Cancelar esta reserva?');
    if (!ok) return;
    this.api.cancel(r.id).subscribe({
      next: _ => { this.snack.open('Reserva cancelada', 'Cerrar', { duration: 2000 }); this.load(); },
      error: e => this.snack.open('No se pudo cancelar', 'Cerrar', { duration: 2500 })
    });
  }
}
