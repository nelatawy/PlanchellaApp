import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { AuthService } from '../../services/auth-service';


declare const google: any;

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

  username: string = '';
  password: string = '';

  ngAfterViewInit() {
    google.accounts.id.initialize({
      client_id: "493505072228-l3nc8pvhbqjanr5gvmhepv4havsrr47u.apps.googleusercontent.com",
      callback: async (response: any) => {
        console.log('ID Token:', response);
        await this.authService.signInWithGoogle(response);
        await this.router.navigate(["/main"]);
      },
      locale: 'en'
    });

    google.accounts.id.renderButton(
      document.getElementById("googleBtn"),
      {
        theme: "outline", size: "large",
        text: "continue_with",
        shape: "rectangular"
      }
    );
  }

  isNotValid(form: NgForm): boolean {
    return !this.username || !this.password || !form || !form.valid
  }

  constructor(private router: Router, private authService: AuthService) {
  }

  onSubmit(form: NgForm) {
    console.log('Form submitted:', form.value);
    this.router.navigate(['/main']);
  }


  async signIn() {
    console.log("calling now");
    let isSuccess = await this.authService.signIn(this.username, this.password);
    if (isSuccess) {
      console.log(isSuccess);
      await this.router.navigate(['/main']);
    }

  }

  onSignIn(googleUser: any) {
    let profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
  }

  handleCredentialResponse(response: any) {
    console.log("Google credential JWT:", response.credential);
  }
}
