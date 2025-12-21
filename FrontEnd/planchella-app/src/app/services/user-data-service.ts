import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom, Observable } from 'rxjs';
import { User } from '../models/user';
import { EventData } from '../models/event-data';
import { CommunityData } from '../models/community-data';
import { Membership } from '../models/membership';

@Injectable({
  providedIn: 'root',
})
export class UserDataService {
  private baseUrl = 'http://localhost:8080/api/users';

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
      'ngrok-skip-browser-warning': 'true',
    });
  }

  constructor(private http: HttpClient) { }

  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/${userId}`);
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/me`);
  }

  updateUser(userId: number, userData: User): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${userId}`, userData);
  }

  async fetch_user_communities(count: number, offset: number, user_id: number): Promise<Array<Membership>> {
    try {
      const result = await firstValueFrom(
        this.http.get(`${this.baseUrl}/${user_id}/memberships?count=${count}&offset=${offset}`, {
          headers: this.getHeaders()
        })
      );
      console.log(result);
      return result as Array<Membership>;
    } catch (err) {
      console.error('Error fetching user communities:', err);
      throw err;
    }
  }

  async fetch_user_events(count: number, offset: number, user_id: number): Promise<Array<EventData>> {
    try {
      const result = await firstValueFrom(
        this.http.get(`${this.baseUrl}/${user_id}/events?count=${count}&offset=${offset}`, {
          headers: this.getHeaders()
        })
      );
      return result as Array<EventData>;
    } catch (err) {
      console.error('Error fetching user events:', err);
      throw err;
    }
  }

  async fetch_my_communities(count: number, offset: number): Promise<Array<Membership>> {
    try {
      return await this.fetch_user_communities(count, offset, Number(localStorage.getItem('userId')));
    } catch (err) {
      console.error('Error fetching user communities:', err);
      throw err;
    }
  }

  async fetch_my_events(count: number, offset: number): Promise<Array<EventData>> {
    try {
      return await this.fetch_user_events(count, offset, Number(localStorage.getItem('userId')));
    } catch (err) {
      console.error('Error fetching user events:', err);
      throw err;
    }
  }



}



