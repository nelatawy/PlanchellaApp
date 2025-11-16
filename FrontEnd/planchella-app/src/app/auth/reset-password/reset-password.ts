import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-reset-password',
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './reset-password.html',
  styleUrl: './reset-password.css',
})
export class ResetPassword {

  constructor(private router: Router) {}

  onSubmit(form: NgForm) {
    console.log('Form submitted with data:', form.value);
    // Here you would typically handle the password reset logic
    this.router.navigate(['/signin']);
  }

}
