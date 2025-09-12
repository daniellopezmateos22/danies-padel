import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const snack = inject(MatSnackBar);
  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {
      const msg = `HTTP ${err.status}: ${err.error?.message || err.message || 'Error'}`;
      snack.open(msg, 'Cerrar', { duration: 3000 });
      return throwError(() => err);
    })
  );
};
