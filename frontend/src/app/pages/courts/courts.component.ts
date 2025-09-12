import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourtsService } from '../../core/services/courts.service';
import { Court } from '../../core/models';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  standalone: true,
  selector: 'app-courts',
  imports: [CommonModule, MatTableModule, MatCardModule, MatProgressSpinnerModule],
  template: `
  <mat-card>
    <mat-card-title>Pistas</mat-card-title>
    <mat-card-content>
      <div *ngIf="loading" style="display:flex;justify-content:center;padding:16px">
        <mat-spinner diameter="32"></mat-spinner>
      </div>
      <table mat-table [dataSource]="courts" *ngIf="!loading && courts.length">

        <ng-container matColumnDef="name">
          <th mat-header-cell *matHeaderCellDef> Nombre </th>
          <td mat-cell *matCellDef="let c"> {{c.name}} </td>
        </ng-container>

        <ng-container matColumnDef="status">
          <th mat-header-cell *matHeaderCellDef> Estado </th>
          <td mat-cell *matCellDef="let c"> {{c.status}} </td>
        </ng-container>

        <ng-container matColumnDef="id">
          <th mat-header-cell *matHeaderCellDef> ID </th>
          <td mat-cell *matCellDef="let c"> {{c.id}} </td>
        </ng-container>

        <tr mat-header-row *matHeaderRowDef="cols"></tr>
        <tr mat-row *matRowDef="let row; columns: cols;"></tr>
      </table>

      <div *ngIf="!loading && !courts.length" style="opacity:.7">No hay pistas.</div>
    </mat-card-content>
  </mat-card>
  `
})
export class CourtsComponent {
  private api = inject(CourtsService);
  courts: Court[] = [];
  loading = true;
  cols = ['name','status','id'];

  ngOnInit() {
    this.api.list().subscribe({
      next: list => { this.courts = list; this.loading = false; },
      error: _ => { this.courts = []; this.loading = false; }
    });
  }
}
