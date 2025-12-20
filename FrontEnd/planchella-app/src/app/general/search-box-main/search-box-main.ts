import { Component } from '@angular/core';
import { SearchService } from '../../services/search.service';

@Component({
  selector: 'search-box-main',
  templateUrl: './search-box-main.html',
  styleUrl: './search-box-main.css',
  standalone: true
})
export class SearchBoxMain {
  constructor(private searchService: SearchService) { }

  async search(input: string) {
    console.log('Searching for events:', input);
    // For now, we just log the results as we need a way to display them (e.g. update main feed)
    const results = await this.searchService.searchEvents(input);
    console.log('Found events:', results);
    // Future: Emit results or update a shared service to refresh the event feed
  }
}
