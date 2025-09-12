import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TimeSlotsService } from '../../core/services/timeslots.service';
import { CourtsService } from '../../core/services/courts.service';
import { TimeSlotDto, Court } from '../../core/models';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

function toISODate(d: Date) { return d.toISOString().slice(0,10); }
function toLocal(dt: string) { return new Date(dt).toLocaleString(); }

@Component({
  standalone: true,
  selector: 'app-timeslots',
  imports: [
    CommonModule, FormsModule,
    MatFormFieldModule, MatSelectModule, MatDatepickerModule, MatNativeDateModule,
    MatButtonModule, MatCardModule, MatListModule, MatProgressSpinnerModule
  ],
  styles: [`.filters { display:flex; gap:12px; align-items:center; margin-bottom:12px; flex-wrap:wrap; }`],
  template: `
  <mat-card>
    <mat-card-title>Franjas horarias</mat-card-title>
    <mat-card-content>
      <div class="filters">
        <mat-form-field appearance="outline">
          <mat-label>Fecha</mat-label>
          <input matInput [matDatepicker]="picker" [value]="date" (dateChange)="onDate($event.value)">
          <mat-datepicker-toggle matSuffix [for]="picker"></mat-datepicker-toggle>
          <mat-datepicker #picker></mat-datepicker>
        </mat-form-field>

        <mat-form-field appearance="outline">
          <mat-label>Pista</mat-label>
          <mat-select [(ngModel)]="courtId" (selectionChange)="load()">
            <mat-option [value]="undefined">Todas</mat-option>
            <mat-option *ngFor="let c of courts" [value]="c.id">{{ c.name }}</mat-option>
          </mat-select>
        </mat-form-field>

        <button mat-raised-button color="primary" (click)="load()">Actualizar</button>
      </div>

      <div *ngIf="loading" style="display:flex;justify-content:center;padding:16px">
        <mat-spinner diameter="32"></mat-spinner>
      </div>

      <mat-list *ngIf="!loading && items.length">
        <mat-list-item *ngFor="let s of items">
          <div matListItemTitle>{{ toLocal(s.startsAt) }} — {{ toLocal(s.endsAt) }}</div>
          <div matListItemLine>Pista: {{ s.courtId }} • Estado: {{ s.state }}</div>
        </mat-list-item>
      </mat-list>

      <div *ngIf="!loading && !items.length" style="opacity:.7">No hay franjas.</div>
    </mat-card-content>
  </mat-card>
  `
})
export class TimeSlotsComponent {
  private api = inject(TimeSlotsService);
  private courtsApi = inject(CourtsService);

  items: TimeSlotDto[] = [];
  courts: Court[] = [];
  date: Date = new Date();
  courtId: string | undefined;
  loading = false;

  toLocal = toLocal;

  ngOnInit() {
    this.courtsApi.list().subscribe(cs => this.courts = cs);
    this.load();
  }

  onDate(d: Date | null) { if (d){ this.date = d; this.load(); } }

  load() {
    this.loading = true;
    this.api.list(toISODate(this.date), this.courtId).subscribe({
      next: list => { this.items = list; this.loading = false; },
      error: _ => { this.items = []; this.loading = false; }
    });
  }
}
