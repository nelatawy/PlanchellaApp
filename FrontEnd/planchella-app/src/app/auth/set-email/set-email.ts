import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-set-email',
  imports: [
    FormsModule,
    RouterLink,
    CommonModule
  ],
  templateUrl: './set-email.html',
  styleUrl: './set-email.css',
})
export class SetEmail {

  constructor(private router: Router) {}

  onSubmit(form: NgForm) {
    console.log('Form submitted with data:', form.value);
    // Navigate to verification-code page after submitting email
    this.router.navigate(['/verification-code']);
  }

}
