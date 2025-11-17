import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';


@Component({
  selector: 'app-registration',
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './registration.html',
  styleUrl: './registration.css',
})
export class Registration {

  constructor(private router: Router) {}

  name: string = '';
  email: string = '';
  password: string = '';
  confirmPassword :string = '';

  passwordsDontMatch() {
    return this.password !== this.confirmPassword;
  }
  isDataInvalid(form: NgForm) {
    return this.passwordsDontMatch() || !this.name || !this.email;
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log(form.value);
      this.router.navigate(['/main'])
    }
    else {
      console.log("Are you kidding me?");
    }
  }

}
