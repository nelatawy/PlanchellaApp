import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import {AuthService} from '../../services/auth-service';
import {firstValueFrom} from 'rxjs';



@Component({
  selector: 'app-registration',
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './registration.html',
  styleUrl: './registration.css',
})
export class Registration {

  constructor(private router: Router, private http : HttpClient, private authService : AuthService) {}

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

  async onSubmit(form: NgForm) {
    if (form.valid) {
      console.log(form.value);
      await this.router.navigate(['/main'])
    }
    else {
      console.log("Are you kidding me?");
    }
  }

  async register(){
    console.log("registeration is registering");
    const isSuccessful : boolean = await this.authService.register(this.name, this.password, "");

    if (isSuccessful){
      await this.router.navigate(["/signin"]);
    }else{

    }
  }

}
