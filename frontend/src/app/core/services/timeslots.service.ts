import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ApiList, TimeSlotDto } from '../models';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class TimeSlotsService {
  private http = inject(HttpClient);
  list(dateISO: string, courtId?: string) {
    const params: any = { date: dateISO };
    if (courtId) params.courtId = courtId;
    return this.http.get<ApiList<TimeSlotDto>>(`${environment.apiUrl}/timeslots`, { params }).pipe(map(r => r.data));
  }
}
