import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { AuthService } from './auth-service';
import { CanActivateChildFn } from '@angular/router';
import { inject } from '@angular/core';

Injectable(
  {
    providedIn: 'root'
  }
)
export class AuthGuard implements CanActivate{

  constructor(private router: Router, private auth: AuthService)

  CanActivate(): boolean {
    // if authenticated -> fine
    if (this.authService?.isAuthenticated()) {
      return true;
    }
    // not authenticated -> redirect
    this.router.navigate(['/signin']);
    return false;
  }
};
