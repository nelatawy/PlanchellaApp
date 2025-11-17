import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-verification-code',
  standalone: true,
  imports: [FormsModule, CommonModule], // Added RouterLink here
  templateUrl: './verification-code.html',
  styleUrl: './verification-code.css',
})
export class VerificationCode {
  constructor(private router: Router) { }

  code : string = "";
  sentCode: string = "2025"; // default value

  isCodeNotValid(form: NgForm) {
    return !this.code || !this.code.trim() || this.code !== this.sentCode || !form.valid;
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Form submitted with data:', form.value);
      // Navigate to reset-password instead of same page
      this.router.navigate(['/reset-password']);
    }
  }
}
