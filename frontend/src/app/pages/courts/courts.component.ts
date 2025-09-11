import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourtsService } from '../../core/services/courts.service';
import { Court } from '../../core/models';

@Component({
  standalone: true,
  imports: [CommonModule],
  selector: 'app-courts',
  template: `
  <h3 class="text-xl font-semibold mb-4">Pistas</h3>
  <div class="grid gap-3">
    <ng-container *ngIf="courts?.length; else empty">
      <div *ngFor="let c of courts" class="border rounded p-3 flex items-center justify-between">
        <div>
          <div class="font-medium">{{ c.name }}</div>
          <div class="text-sm opacity-70">ID: {{ c.id }}</div>
        </div>
        <span class="text-sm">{{ c.status }}</span>
      </div>
    </ng-container>
    <ng-template #empty>
      <p class="text-sm opacity-70">Aún no hay pistas.</p>
    </ng-template>
  </div>
  `
})
export class CourtsComponent {
  private api = inject(CourtsService);
  courts: Court[] = [];

  ngOnInit() {
    this.api.list().subscribe(list => this.courts = list);
  }
}
