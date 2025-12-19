import { Component, ElementRef, EventEmitter, HostListener, Input, Output, ViewChild } from '@angular/core';
import { CommunityCard } from '../community-card/community-card';
import { EventData } from '../models/event-data';
import { CommunityCardData } from '../models/community-card';
import { CommunityData } from '../models/community-data';
import { CommunityDataService } from '../services/community-data-service';

@Component({
  selector: 'app-community-selector',
  standalone: true,
  imports: [
    CommunityCard
  ],
  templateUrl: './community-selector.html',
  styleUrl: './community-selector.css',
})
export class CommunitySelector {

  @Input("selectedCommunity")
  selected_community: CommunityData | undefined;

  isLoading: boolean = false;

  constructor(private communityDataService: CommunityDataService) {
  }

  async ngOnInit() {
    // load communities
    await this.add_communities(14);
  }

  nums = Array.from({ length: 4 });
  communities: Array<CommunityCardData> = [];

  @Output("selection")
  selection_emitter: EventEmitter<CommunityData> = new EventEmitter<CommunityData>();

  emit_selection(event: CommunityData) {
    this.selection_emitter.emit(event);

  }

  async add_communities(count: number) {
    this.isLoading = true;
    try {
      let data: Array<CommunityData> | undefined = await this.communityDataService.fetch_communities(count, "");
      data?.forEach((communityData) => {
        this.communities.push({ id : "id", communityData: communityData, currentlySelected: true });
      });
    } catch (error) {
      console.error('Error fetching communities:', error);
    } finally {
      this.isLoading = false;
    }
  }

  @HostListener('scroll', ['$event'])
  async onScroll(e: any) {
    const el = e.target;
    if (el.scrollTop >= el.scrollHeight - el.clientHeight - 10) {
      await this.add_communities(4);
    }
  }

}
