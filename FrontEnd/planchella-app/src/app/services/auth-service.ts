import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';

class signInResponse {
  "token": string;
  "userId": string;
}

@Injectable({
  providedIn: "root"
})
export class AuthService {
  private baseUrl = "http://localhost:8080/account";

  private registerUrl = this.baseUrl + "/register";
  private loginUrl = this.baseUrl + "/login";

  private googleRegisterUrl = this.baseUrl + "/auth/google/register";
  private googleLoginUrl = this.baseUrl + "/auth/google/login";

  constructor(private http: HttpClient) { }

  async register(username: string, password: string, email: string): Promise<boolean> {
    console.log("=== AUTH SERVICE REGISTER ===");
    console.log("Received username:", username);
    console.log("Received email:", email);
    console.log("Received password:", password ? "present" : "empty");

    let data = {
      "username": username,
      "password": password,
      "email": email
    }

    console.log("Sending data:", data);

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

  async signIn(username: string, password: string): Promise<boolean> {
    let data = {
      "username": username,
      "password": password
    }
    try {
      const result: signInResponse = await firstValueFrom(
        this.http.post<signInResponse>(this.loginUrl, data)
      );
      console.log("sign in complete", result);

      localStorage.setItem("authToken", result.token);
      localStorage.setItem("userId", result.userId);
      localStorage.setItem("isAuthenticated", String(true));

      return true;
    } catch (err) {
      console.error("error while trying to sign in", err);
      return false;
    }
  }

  async signInWithGoogle(response: any): Promise<boolean> {
    try {
      console.log("data : " + response.credential);
      const result: any = await firstValueFrom(
        this.http.post(this.googleLoginUrl, response.credential)
      );
      console.log("sign in complete", result);
      if (result && result.token) {
        localStorage.setItem("authToken", result.token);
        localStorage.setItem("userId", String(result.userId));
        localStorage.setItem("isAuthenticated", String(true));
        return true;
      }
      return false;
    } catch (err) {
      console.error("error while trying to sign in", err);
      return false;
    }
  }

  async registerWithGoogle(response: any): Promise<boolean> {

    try {
      const result = await firstValueFrom(
        this.http.post(this.googleRegisterUrl, response.credential, { responseType: 'text' })
      );

      console.log("registration complete", result);
      return true;
    } catch (err) {
      console.error("error while trying to register", err);
      return false;
    }
  }


  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  getCurrentUserId(): number | null {
    // Priority 1: Check localStorage directly for stored userId
    const storedUserId = localStorage.getItem("userId");
    if (storedUserId) {
      const id = Number(storedUserId);
      if (!isNaN(id)) return id;
    }

    // Priority 2: Fallback to decoding the JWT token
    const token = this.getToken();
    if (!token) return null;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      console.log('JWT Payload:', payload);
      const idFromToken = payload.userId || payload.sub || payload.id || payload.user_id;
      return idFromToken ? Number(idFromToken) : null;
    } catch (error) {
      console.error('Error decoding token:', error);
      return null;
    }
  }



  logout(): void {
    localStorage.removeItem('authToken');
    // localStorage.removeItem('refreshToken');
    // localStorage.removeItem('tokenExpires');
    localStorage.setItem('isAuthenticated', 'false');
  }

  isAuthenticated(): boolean {
    if (localStorage.getItem("authToken") && localStorage.getItem("isAuthenticated") == "true") {
      return true;
    }
    return false;
  }

}
