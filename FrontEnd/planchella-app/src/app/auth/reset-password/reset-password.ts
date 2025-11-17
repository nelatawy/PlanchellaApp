import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  imports: [FormsModule, CommonModule],
  templateUrl: './reset-password.html',
  styleUrl: './reset-password.css',
})
export class ResetPassword {

  constructor(private router: Router) {}

  password: string = '';
  confirmPassword :string = '';

  passwordsDontMatch() {
    return this.password !== this.confirmPassword;
  }


  onSubmit(form: NgForm) {
    if (this.password !== this.confirmPassword) {
      alert("Passwords don't match");
      return;
    }
    if (form.valid) {
      console.log('Form submitted with data:', form.value);
      // Here you would typically handle the password reset logic
      this.router.navigate(['/signin']);
    }
    else {
      console.log('Not Fine');
    }
  }

}
