import { Injectable } from '@angular/core';
import { CommunityData } from '../models/community-data';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

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

  async fetch_communities(count: number, username: string) {
    const response: Response = await fetch(`http://localhost:8080/data/user/communities?count=${count}&username=${username}`);
    if (!response.ok) {
      window.alert("Error fetching");
      return;
    }
    const data: CommunityData[] = await response.json();

    this.cache.clear();
    for (let item of data) {
      this.cache.set(item.name, item);
    }

    return data as CommunityData[];
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
  async createCommunity(communityData: any): Promise<any> {
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
}
