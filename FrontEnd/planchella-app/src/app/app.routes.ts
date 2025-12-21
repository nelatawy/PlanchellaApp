import { RouterModule, Routes } from '@angular/router';
import { SignIn } from './auth/sign-in/sign-in';
import { Registration } from './auth/registration/registration';
import { Component, NgModule } from '@angular/core';
import { MainPage } from './main-page/main-page';
import { VerificationCode } from './auth/verification-code/verification-code';
import { ResetPassword } from './auth/reset-password/reset-password';
import { SetEmail } from './auth/set-email/set-email';
import { AccountPage } from './account-page/account-page';
import { EventBuilder } from './event-builder/event-builder';
import { EventComponent } from './event/event';
import { CommunityBuilder } from './community-builder/community-builder';
import { authGuard } from './services/auth-guard';
import { signedGuard } from './services/signed-guard';

export const routes: Routes = [
  { path: '', redirectTo: '/signin', pathMatch: 'full' }, // default route


  {
    path: 'signin',
    component: SignIn,
    canActivate: [signedGuard]
  },
  {
    path: 'register'
    , component: Registration
    , canActivate: [signedGuard]
  },
  {
    path: 'set-email',
    component: SetEmail,
    canActivate: [signedGuard]
  },
  {
    path: 'main',
    component: MainPage,
    canActivate: [authGuard]
  },
  {
    path: 'main/:name',
    component: MainPage,
    canActivate: [authGuard]
  },
  {
    path: 'verification-code',
    component: VerificationCode,
    canActivate: [signedGuard]
  },
  {
    path: 'reset-password',
    component: ResetPassword,
    canActivate: [signedGuard]
  },
  {
    path: 'event-builder',
    component: EventBuilder,
    canActivate: [authGuard]
  },
  {
    path: 'account-page',
    component: AccountPage,
    canActivate: [authGuard]
  },
  {
    path: 'event/:id',
    component: EventComponent,
    canActivate: [authGuard]
  },
  {
    path: 'community-builder',
    component: CommunityBuilder,
    canActivate: [authGuard]
  },
  { path: '**', redirectTo: '/signin' } // fallback
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})


export class AppRoutingModule {

}
