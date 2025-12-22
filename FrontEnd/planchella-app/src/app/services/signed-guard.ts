import { Injectable, inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { AuthService } from './auth-service';

Injectable(
    {
        providedIn: 'root'
    }
)
export const signedGuard: CanActivateFn = () => {
    const router = inject(Router);
    const auth = inject(AuthService);
    return auth.isAuthenticated() ? router.parseUrl('/main') : true;
};
