import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-sign-in',
  imports: [
    RouterLink,
    CommonModule,
    FormsModule,
  ],
  templateUrl: './sign-in.html',
  styleUrl: './sign-in.css',
  standalone: true
})
export class SignIn {

  email: string = '';
  password: string = '';

  isNotValid(form: NgForm): boolean {
    return !this.email || !this.password || !form || !form.valid
  }

  constructor(private router: Router) {}

  onSubmit(form: NgForm) {
    console.log('Form submitted:', form.value);
    this.router.navigate(['/main']);
  }

}
