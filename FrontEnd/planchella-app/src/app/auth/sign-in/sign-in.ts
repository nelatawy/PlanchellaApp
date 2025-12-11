import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import {AuthService} from '../../services/auth-service';

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

  constructor(private router: Router, private authService: AuthService) {
  }

  onSubmit(form: NgForm) {
    console.log('Form submitted:', form.value);
    this.router.navigate(['/main']);
  }


  async signIn() {
    console.log("calling now");
      let isSuccess = await this.authService.signIn(this.email, this.password);
      if(isSuccess){
        console.log(isSuccess);
        await this.router.navigate(['/main']);
      }

  }
}
