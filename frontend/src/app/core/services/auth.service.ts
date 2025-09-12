import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Me } from '../models';

interface LoginResp { token: string }

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private key = 'dp_token';
  private meSub = new BehaviorSubject<Me | null>(null);

  me$ = this.meSub.asObservable();

  get token(): string | null { return localStorage.getItem(this.key); }
  get me(): Me | null { return this.meSub.value; }
  get isAuth(): boolean { return !!this.token; }

  login(email: string, password: string): Observable<LoginResp> {
    return this.http.post<LoginResp>(`${environment.apiUrl}/auth/login`, { email, password }).pipe(
      tap(r => localStorage.setItem(this.key, r.token)),
      tap(() => this.fetchMe().subscribe({
        error: err => console.error('Error en fetchMe:', err)
      }))
    );
  }


  fetchMe(): Observable<Me> {
    return this.http.get<Me>(`${environment.apiUrl}/me`).pipe(
      tap(me => this.meSub.next(me))
    );
  }


  logout() {
    localStorage.removeItem(this.key);
    this.meSub.next(null);
  }
}
