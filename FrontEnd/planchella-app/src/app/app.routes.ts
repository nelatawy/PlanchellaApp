import {RouterModule, Routes} from '@angular/router';
import {SignIn} from './auth/sign-in/sign-in';
import { Registration } from './auth/registration/registration';
import { TopBar } from './general/top-bar/top-bar';
import {Component, NgModule} from '@angular/core';
import {MainPage} from './main-page/main-page';
import { VerificationCode } from './auth/verification-code/verification-code';
import { ResetPassword } from './auth/reset-password/reset-password';
import { SetEmail } from './auth/set-email/set-email';
import { AccountPage } from './account-page/account-page';
import { EventBuilder } from './event-builder/event-builder';
import {Billboard} from './billboard/billboard';

import { AuthGuard } from './services/auth-guard';

export const routes: Routes = [
  { path: '', redirectTo: '/signin', pathMatch: 'full' }, // default route


  { path: 'signin', component: SignIn },
  { path: 'register', component: Registration },
  { path: 'set-email', component: SetEmail },
  { path: 'main',
    component: MainPage,
    canActivate: [AuthGuard]
  },
  { path: 'main/:name', component: MainPage},
  { path: 'verification-code', component: VerificationCode },
  { path: 'reset-password', component: ResetPassword },
  { path: 'event-builder', component: EventBuilder },
  { path: 'account-page', component: AccountPage},
  { path: '**', redirectTo: '/signin' } // fallback
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})


export class AppRoutingModule {

}
