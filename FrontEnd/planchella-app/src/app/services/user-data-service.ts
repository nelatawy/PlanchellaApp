import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class UserDataService {
  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/${userId}`);
  }
  
  getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/me`);
  }

  updateUser(userId: number, userData: User): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${userId}`, userData);
  }
}
