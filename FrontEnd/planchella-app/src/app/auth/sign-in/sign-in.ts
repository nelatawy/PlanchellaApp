import { Component } from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import { NgModule} from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-sign-in',
  imports: [
    RouterLink,
    RouterOutlet,
    CommonModule,
    FormsModule,
  ],
  templateUrl: './sign-in.html',
  styleUrl: './sign-in.css',
  standalone: true
})
export class SignIn {

  constructor(private router: Router) {}

  onSubmit(form: NgForm) {
    console.log('Form submitted:', form.value);
    this.router.navigate(['/main']);
  }

}
