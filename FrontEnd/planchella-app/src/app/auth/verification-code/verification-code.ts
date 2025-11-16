import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-verification-code',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink], // Added RouterLink here
  templateUrl: './verification-code.html',
  styleUrl: './verification-code.css',
})
export class VerificationCode {
  constructor(private router: Router) { }

  onSubmit(form: NgForm) {
    console.log('Form submitted with data:', form.value);
    // Navigate to reset-password instead of same page
    this.router.navigate(['/reset-password']);

  }
}