import { EventData } from '../models/event-data';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Injectable({ providedIn: "root" })
export class EventDataService {
  private apiUrl = 'http://localhost:8080/api/event';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
      'ngrok-skip-browser-warning': 'true',
    });
  }

  async fetch_events(count: number, communityName: string) {
    const response: Response = await fetch(`http://localhost:8080/data/community/events?count=${count}&communityName=${communityName}`);
    if (!response.ok) {
      window.alert("Error fetching");
      return;
    }
    const data = await response.json();
    return data as EventData[];
  }

  // Create new event (PUT)
  async createEvent(eventData: any): Promise<any> {
    try {
      const result = await firstValueFrom(
        this.http.put(this.apiUrl, eventData, {
          headers: this.getHeaders()
        })
      );
      console.log('Event created successfully:', result);
      return result;
    } catch (err) {
      console.error('Error creating event:', err);
      throw err;
    }
  }

  // Get single event by ID (GET)
  async getEvent(eventId: number): Promise<any> {
    try {
      const result = await firstValueFrom(
        this.http.get(`${this.apiUrl}/${eventId}`, {
          headers: this.getHeaders()
        })
      );
      return result;
    } catch (err) {
      console.error('Error fetching event:', err);
      throw err;
    }
  }

  // Update existing event (PATCH)
  async updateEvent(eventId: number, eventData: any): Promise<any> {
    try {
      const result = await firstValueFrom(
        this.http.patch(`${this.apiUrl}/${eventId}`, eventData, {
          headers: this.getHeaders()
        })
      );
      console.log('Event updated successfully:', result);
      return result;
    } catch (err) {
      console.error('Error updating event:', err);
      throw err;
    }
  }

  // Delete event (DELETE)
  async deleteEvent(eventId: number): Promise<any> {
    try {
      const result = await firstValueFrom(
        this.http.delete(`${this.apiUrl}/${eventId}`, {
          headers: this.getHeaders()
        })
      );
      console.log('Event deleted successfully:', result);
      return result;
    } catch (err) {
      console.error('Error deleting event:', err);
      throw err;
    }
  }
}
