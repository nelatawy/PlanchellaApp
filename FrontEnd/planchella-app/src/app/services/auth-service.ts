import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {firstValueFrom, Observable} from 'rxjs';

class signInResponse {
  "token" : string
}

@Injectable({
  providedIn : "root"
})
export class AuthService {
  private baseUrl = "http://localhost:8080/account";
  private registerUrl = this.baseUrl + "/register";
  private loginUrl = this.baseUrl + "/login";

  constructor(private http : HttpClient) {}

  async register(username: string, password: string, displayName: string): Promise<boolean>{
    let data = {
      "username" : username,
      "password" : password
    }
    try {
      const result = await firstValueFrom(
        this.http.post(this.registerUrl, data)
      );
      console.log("registration complete", result);
      return true;
    } catch (err) {
      console.error("error while trying to register", err);
      return false;
    }
  }

  async signIn(username: string, password: string): Promise<boolean>{
    let data = {
      "username" : username,
      "password" : password
    }
    try {
      const result : signInResponse = await firstValueFrom(
        this.http.post<signInResponse>(this.loginUrl, data)
      );
      console.log("sign in complete", result);

      localStorage.setItem("authToken",result.token)
      localStorage.setItem("isAuthenticated", String(true));

      return true;
    } catch (err) {
      console.error("error while trying to sign in", err);
      return false;
    }
  }

  getToken(): string | null {
    return localStorage.getItem('authToken');
  }



  logout(): void {
    localStorage.removeItem('authToken');
    // localStorage.removeItem('refreshToken');
    // localStorage.removeItem('tokenExpires');
    localStorage.setItem('isAuthenticated', 'false');
  }

  isAuthenticated() : boolean {
    if (localStorage.getItem("authToken") && localStorage.getItem("isAuthenticated") == "true") {
      return true;
    }
    return false;
  }

}
