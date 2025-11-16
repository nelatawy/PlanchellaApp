import {RouterModule, Routes} from '@angular/router';
import {SignIn} from './sign-in/sign-in';
import { Registration } from './registration/registration';
import {ForgotPassword} from './forgot-password/forgot-password';
import { TopBar } from './general/top-bar/top-bar';
import {Component, NgModule} from '@angular/core';
import {MainPage} from './main-page/main-page';

export const routes: Routes = [
  { path: '', redirectTo: '/signin', pathMatch: 'full' }, // default route
  { path: 'signin', component: SignIn },
  { path: 'register', component: Registration },
  { path: 'forgot-password', component: ForgotPassword },
  { path: 'main', component: MainPage},
  { path: '**', redirectTo: '/signin' } // fallback
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})


export class AppRoutingModule {

}
