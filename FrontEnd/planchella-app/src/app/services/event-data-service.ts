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



  // Create new event (PUT)
  async createEvent(eventData: EventData): Promise<any> {
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
  async updateEvent(eventId: number, eventData: EventData): Promise<any> {
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

  // Get events by author (GET)
  async getEventsByAuthor(userId: number, count: number = 10, offset: number = 0): Promise<any> {
    try {
      const result = await firstValueFrom(
        this.http.get(`${this.apiUrl}/author/${userId}?count=${count}&offset=${offset}`, {
          headers: this.getHeaders()
        })
      );
      return result;
    } catch (err) {
      console.error('Error fetching author events:', err);
      throw err;
    }
  }
}
