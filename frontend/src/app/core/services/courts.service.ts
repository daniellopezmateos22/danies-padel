import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ApiList, Court } from '../models';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class CourtsService {
  private http = inject(HttpClient);
  list() {
    return this.http.get<ApiList<Court>>(`${environment.apiUrl}/courts`).pipe(map(r => r.data));
  }
}
