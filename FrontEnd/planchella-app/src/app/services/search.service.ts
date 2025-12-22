import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { CommunityData } from '../models/community-data';
import { EventData } from '../models/event-data';
import { User } from '../models/user';

@Injectable({
    providedIn: 'root'
})
export class SearchService {
    private apiUrl = 'http://localhost:8080/api/search';

    constructor(private http: HttpClient) { }

    private getHeaders(): HttpHeaders {
        const token = localStorage.getItem('authToken');
        return new HttpHeaders({
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
            'ngrok-skip-browser-warning': 'true',
        });
    }

    /**
     * Search for events by keywords
     * @param keywords Search query
     * @param count Number of results to return (default: 20)
     * @param offset Pagination offset (default: 0)
     * @returns Promise with array of events
     */
    async searchEvents(keywords: string, count: number = 20, offset: number = 0): Promise<EventData[]> {
        try {
            const params = new HttpParams()
                .set('keywords', keywords)
                .set('count', count.toString())
                .set('offset', offset.toString());

            const result = await firstValueFrom(
                this.http.get<EventData[]>(`${this.apiUrl}/events`, {
                    headers: this.getHeaders(),
                    params: params
                })
            );
            console.log('Events search results:', result);
            return result;
        } catch (err) {
            console.error('Error searching events:', err);
            throw err;
        }
    }

    /**
     * Search for communities by keywords
     * @param keywords Search query
     * @param count Number of results to return (default: 20)
     * @param offset Pagination offset (default: 0)
     * @returns Promise with array of communities
     */
    async searchCommunities(keywords: string, count: number = 20, offset: number = 0): Promise<CommunityData[]> {
        try {
            const params = new HttpParams()
                .set('keywords', keywords)
                .set('count', count.toString())
                .set('offset', offset.toString());

            const result = await firstValueFrom(
                this.http.get<CommunityData[]>(`${this.apiUrl}/communities`, {
                    headers: this.getHeaders(),
                    params: params
                })
            );
            console.log('Communities search results:', result);
            return result;
        } catch (err) {
            console.error('Error searching communities:', err);
            throw err;
        }
    }

    /**
     * Search for users by keywords
     * @param keywords Search query
     * @param count Number of results to return (default: 20)
     * @param offset Pagination offset (default: 0)
     * @returns Promise with array of users
     */
    async searchUsers(keywords: string, count: number = 20, offset: number = 0): Promise<User[]> {
        try {
            const params = new HttpParams()
                .set('keywords', keywords)
                .set('count', count.toString())
                .set('offset', offset.toString());

            const result = await firstValueFrom(
                this.http.get<User[]>(`${this.apiUrl}/users`, {
                    headers: this.getHeaders(),
                    params: params
                })
            );
            console.log('Users search results:', result);
            return result;
        } catch (err) {
            console.error('Error searching users:', err);
            throw err;
        }
    }
}
