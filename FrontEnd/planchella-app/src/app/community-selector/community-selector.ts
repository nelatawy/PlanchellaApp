import { Component, ElementRef, EventEmitter, HostListener, Input, Output, ViewChild } from '@angular/core';
import { CommunityCard } from '../community-card/community-card';
import { EventData } from '../models/event-data';
import { CommunityCardData } from '../models/community-card';
import { CommunityData } from '../models/community-data';
import { CommunityDataService } from '../services/community-data-service';

import { SearchService } from '../services/search.service';

import { CommunityBuilder } from '../community-builder/community-builder';

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
  selected_community: CommunityData = {
    id: 0,
    name: "",
    description: "",
    memberCount: 0,
    createdAt: new Date(),
    category: "",
    iconUrl: ""
  };

  isLoading: boolean = false;
  searchQuery: string = '';
  isCreatingCommunity: boolean = false;

  constructor(
    private communityDataService: CommunityDataService,
    private searchService: SearchService
  ) {
  }

  async ngOnInit() {
    // load communities
    await this.add_communities(14);
  }

  async onSearch(event: any) {
    this.searchQuery = event.target.value;
    this.isLoading = true;
    try {
      const results = await this.searchService.searchCommunities(this.searchQuery);
      this.communities = []; // Clear current list
      results.forEach((communityData) => {
        this.communities.push({
          id: "id",
          communityData: communityData,
          currentlySelected: communityData.name === this.selected_community?.name
        });
      });
    } catch (error) {
      console.error('Search error:', error);
    } finally {
      this.isLoading = false;
    }
  }

  @Output("selection")
  selection_emitter: EventEmitter<CommunityData> = new EventEmitter<CommunityData>();

  @Output() openBuilder = new EventEmitter<void>();

  toggleCommunityBuilder() {
    this.openBuilder.emit();
  }

  // Called by parent (MainPage) when creation is done to refresh list
  async refreshList() {
    this.communities = [];
    await this.add_communities(14);
  }

  nums = Array.from({ length: 4 });
  communities: Array<CommunityCardData> = [];



  emit_selection(event: CommunityData) {
    this.selection_emitter.emit(event);

  }

  async add_communities(count: number) {
    this.isLoading = true;
    try {
      let data: Array<CommunityData> | undefined = await this.communityDataService.fetch_communities(count, "");
      data?.forEach((communityData) => {
        this.communities.push({ id: "id", communityData: communityData, currentlySelected: true });
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
