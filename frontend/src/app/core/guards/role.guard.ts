import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const roleGuard = (roles: string[]): CanActivateFn => () => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const ok = !!auth.me && roles.includes(auth.me.role);
  if (!ok) router.navigateByUrl('/');
  return ok;
};
