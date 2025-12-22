import { Component, EventEmitter, Output } from '@angular/core';
import { SearchService } from '../../services/search.service';
import { EventData } from '../../models/event-data';
import { CommunityData } from '../../models/community-data';

@Component({
  selector: 'search-box-main',
  templateUrl: './search-box-main.html',
  styleUrl: './search-box-main.css',
  standalone: true
})
export class SearchBoxMain {
  @Output() eventSearch = new EventEmitter<EventData[]>();
  @Output() communitySearch = new EventEmitter<CommunityData[]>();

  constructor(private searchService: SearchService) { }

  async search(input: string) {
    console.log('Searching for events:', input);

    // Search for events and emit results
    const eventResults = await this.searchService.searchEvents(input);
    console.log('Found events:', eventResults);
    this.eventSearch.emit(eventResults);

    // Search for communities and emit results
    const communityResults = await this.searchService.searchCommunities(input);
    console.log('Found communities:', communityResults);
    this.communitySearch.emit(communityResults);
  }
}
