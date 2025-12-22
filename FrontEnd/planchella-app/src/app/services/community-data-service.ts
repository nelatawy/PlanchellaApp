import { Injectable } from '@angular/core';
import { CommunityData } from '../models/community-data';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { EventData } from '../models/event-data';

@Injectable({ providedIn: "root" })
export class CommunityDataService {
  private apiUrl = 'http://localhost:8080/api/community';
  private cache: Map<string, CommunityData> = new Map();

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
      'ngrok-skip-browser-warning': 'true',
    });
  }

  async fetch_community_events(count: number, offset: number, communityId: number): Promise<EventData[]> {
    try {
      const result = await firstValueFrom(
        this.http.get(`${this.apiUrl}/${communityId}/events?count=${count}&offset=${offset}`, {
          headers: this.getHeaders()
        })
      );
      return result as EventData[];
    } catch (err) {
      console.error('Error fetching events:', err);
      throw err;
    }
  }

  public get_community(name: string): CommunityData | undefined {
    return this.cache.get(name);
  }

  // Get single community by ID (GET)
  async getCommunity(communityId: number): Promise<any> {
    try {
      const result = await firstValueFrom(
        this.http.get(`${this.apiUrl}/${communityId}`, {
          headers: this.getHeaders()
        })
      );
      return result;
    } catch (err) {
      console.error('Error fetching community:', err);
      throw err;
    }
  }

  // Create new community (PUT)
  async createCommunity(communityData: CommunityData): Promise<any> {
    try {
      const result = await firstValueFrom(
        this.http.put(this.apiUrl, communityData, {
          headers: this.getHeaders()
        })
      );
      console.log('Community created successfully:', result);
      return result;
    } catch (err) {
      console.error('Error creating community:', err);
      throw err;
    }
  }

  // Update existing community (PATCH)
  async updateCommunity(communityId: number, communityData: any): Promise<any> {
    try {
      const result = await firstValueFrom(
        this.http.patch(`${this.apiUrl}/${communityId}`, communityData, {
          headers: this.getHeaders()
        })
      );
      console.log('Community updated successfully:', result);
      return result;
    } catch (err) {
      console.error('Error updating community:', err);
      throw err;
    }
  }

  // Delete community (DELETE)
  async deleteCommunity(communityId: number): Promise<any> {
    try {
      const result = await firstValueFrom(
        this.http.delete(`${this.apiUrl}/${communityId}`, {
          headers: this.getHeaders()
        })
      );
      console.log('Community deleted successfully:', result);
      return result;
    } catch (err) {
      console.error('Error deleting community:', err);
      throw err;
    }
  }

  // Join community
  async joinCommunity(communityId: number, type: 'guest' | 'member' = 'member'): Promise<void> {
    try {
      await firstValueFrom(
        this.http.post(`http://localhost:8080/api/memberships/join/${communityId}/${type}`, {}, {
          headers: this.getHeaders()
        })
      );
    } catch (err) {
      console.error('Error joining community:', err);
      throw err;
    }
  }

  // Leave community
  async leaveCommunity(communityId: number): Promise<void> {
    try {
      await firstValueFrom(
        this.http.post(`http://localhost:8080/api/memberships/leave/${communityId}`, {}, {
          headers: this.getHeaders()
        })
      );
    } catch (err) {
      console.error('Error leaving community:', err);
      throw err;
    }
  }
}
