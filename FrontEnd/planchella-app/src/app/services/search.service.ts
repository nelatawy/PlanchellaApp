import { Injectable } from '@angular/core';
import { CommunityDataService } from './community-data-service';
import { EventDataService } from './event-data-service';
import { Observable, from } from 'rxjs';
import { CommunityData } from '../models/community-data';
import { EventData } from '../models/event-data';

@Injectable({
    providedIn: 'root'
})
export class SearchService {

    constructor(
        private communityService: CommunityDataService,
        private eventService: EventDataService
    ) { }

    /**
     * Search for communities by name query.
     * Leverages CommunityDataService.fetch_communities (mocking filtering if API doesn't support it yet)
     */
    async searchCommunities(query: string): Promise<CommunityData[]> {
        return Array<CommunityData>();
        // In a real app, pass 'query' to the API. 
        // fetch_communities currently takes (count, username). 
        // We might need to adjust fetch_communities or just filter client-side for now if the API is rigid.
        // Assuming for now we fetch a batch and filter client-side since API signatures vary.

        const count = 20; // Default fetch size for search
        // const allCommunities = await this.communityService.fetch_communities(count, "");

        // if (!query) return allCommunities || [];

        // return (allCommunities || []).filter(c =>
        //     c.name.toLowerCase().includes(query.toLowerCase()) ||
        //     c.description?.toLowerCase().includes(query.toLowerCase())
        // );
    }

    /**
     * Search for events by query.
     */
    async searchEvents(query: string): Promise<EventData[]> {
        return Array<EventData>();
        // Similarly, EventDataService.fetch_events takes (count, communityName).
        // We'll fetch broadly and filter client-side for this MVP step.

        // const count = 20;
        // const allEvents = await this.eventService.fetch_events(count, "");

        // if (!query) return allEvents || [];

        // return (allEvents || []).filter(e =>
        //     e.title.toLowerCase().includes(query.toLowerCase()) ||
        //     e.description.toLowerCase().includes(query.toLowerCase())
        // );
    }
}
