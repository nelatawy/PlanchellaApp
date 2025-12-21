import { Injectable, inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { AuthService } from './auth-service';
import { CanActivateChild } from '@angular/router';

Injectable(
  {
    providedIn: 'root'
  }
)
export const authGuard: CanActivateFn = () => {
  const router = inject(Router);
  const auth = inject(AuthService);
  // return auth.isAuthenticated() ? true : router.parseUrl('/signin');
  return true;
};
