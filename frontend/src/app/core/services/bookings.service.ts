import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

export interface Booking {
  id: string;
  userId: string;
  courtId: string;
  startsAt: string; // ISO
  endsAt: string;   // ISO
  status: 'CONFIRMED' | 'CANCELED';
}

@Injectable({ providedIn: 'root' })
export class BookingsService {
  private http = inject(HttpClient);

  listByDate(dateISO: string, courtId?: string): Observable<Booking[]> {
    const params: any = { date: dateISO };
    if (courtId) params.courtId = courtId;
    return this.http.get<Booking[]>(`${environment.apiUrl}/bookings`, { params });
  }

  create(courtId: string, startsAt: string, endsAt: string): Observable<Booking> {
    return this.http.post<Booking>(`${environment.apiUrl}/bookings`, { courtId, startsAt, endsAt });
  }

  cancel(id: string) {
    return this.http.delete<void>(`${environment.apiUrl}/bookings/${id}`);
  }
}
