import { Component, ElementRef, EventEmitter, HostListener, Input, Output, ViewChild } from '@angular/core';
import { CommunityCard } from '../community-card/community-card';
import { EventData } from '../models/event-data';
import { CommunityCardData } from '../models/community-card';
import { CommunityData } from '../models/community-data';
import { Membership } from '../models/membership';
import { CommunityDataService } from '../services/community-data-service';

import { SearchService } from '../services/search.service';

import { CommunityBuilder } from '../community-builder/community-builder';
import { UserDataService } from '../services/user-data-service';

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
  };

  offset: number = 0;
  isLoading: boolean = false;
  searchQuery: string = '';
  isCreatingCommunity: boolean = false;

  constructor(
    private communityDataService: CommunityDataService,
    private searchService: SearchService,
    private userDataService: UserDataService
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
      let memberships: Array<Membership> | undefined = await this.userDataService.fetch_my_communities(count, this.offset);

      if (memberships) {
        for (const membership of memberships) {
          try {
            console.log('Fetching community data for membership:', membership);
            const communityData = await this.communityDataService.getCommunity(membership.communityId);
            if (communityData) {
              console.log('Fetched community data:', communityData);
              this.communities.push({
                id: String(communityData.id),
                communityData: communityData,
                currentlySelected: communityData.id === this.selected_community?.id
              });
            }
          } catch (err) {
            console.error(`Error fetching community data for id ${membership.communityId}:`, err);
          }
        }
      }
      this.offset += count;
    } catch (error) {
      console.error('Error fetching memberships:', error);
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

  // Load search results from external search trigger
  loadSearchResults(results: CommunityData[]) {
    this.communities = [];
    results.forEach((communityData) => {
      this.communities.push({
        id: String(communityData.id),
        communityData: communityData,
        currentlySelected: communityData.id === this.selected_community?.id
      });
    });
  }

}
