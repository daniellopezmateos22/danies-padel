import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TimeSlotsService } from '../../core/services/timeslots.service';
import { CourtsService } from '../../core/services/courts.service';
import { TimeSlotDto, Court } from '../../core/models';

function toLocal(dt: string) { return new Date(dt).toLocaleString(); }

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  selector: 'app-timeslots',
  template: `
  <h3 class="text-xl font-semibold mb-4">Franjas horarias</h3>

  <div class="flex gap-2 items-end mb-4">
    <label class="block">
      <span class="text-sm">Fecha</span>
      <input type="date" class="border p-2 rounded" [(ngModel)]="date" (change)="load()">
    </label>
    <label class="block">
      <span class="text-sm">Pista</span>
      <select class="border p-2 rounded" [(ngModel)]="courtId" (change)="load()">
        <option [ngValue]="undefined">Todas</option>
        <option *ngFor="let c of courts" [value]="c.id">{{ c.name }}</option>
      </select>
    </label>
    <button class="border px-3 py-2 rounded" (click)="load()">Actualizar</button>
  </div>

  <div class="grid gap-3">
    <ng-container *ngIf="items?.length; else empty">
      <div *ngFor="let s of items" class="border rounded p-3 flex items-center justify-between">
        <div>
          <div class="font-medium">{{ toLocal(s.startsAt) }} — {{ toLocal(s.endsAt) }}</div>
          <div class="text-sm opacity-70">Pista: {{ s.courtId }}</div>
        </div>
        <span class="text-sm">{{ s.state }}</span>
      </div>
    </ng-container>
    <ng-template #empty>
      <p class="text-sm opacity-70">No hay franjas disponibles.</p>
    </ng-template>
  </div>
  `
})
export class TimeSlotsComponent {
  private api = inject(TimeSlotsService);
  private courtsApi = inject(CourtsService);

  items: TimeSlotDto[] = [];
  courts: Court[] = [];
  date = new Date().toISOString().slice(0,10);
  courtId: string | undefined = undefined;

  toLocal = toLocal;

  ngOnInit() {
    this.courtsApi.list().subscribe(cs => this.courts = cs);
    this.load();
  }

  load() {
    this.api.list(this.date, this.courtId).subscribe(list => this.items = list);
  }
}
