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
import { DialogService } from '../services/dialog.service';

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
  searchOffset: number = 0;
  isLoading: boolean = false;
  searchQuery: string = '';
  isCreatingCommunity: boolean = false;
  myCommunityIds: Set<number> = new Set();

  constructor(
    private communityDataService: CommunityDataService,
    private searchService: SearchService,
    private userDataService: UserDataService,
    private dialogService: DialogService
  ) {
  }

  async ngOnInit() {
    // load communities
    await this.add_communities(14);
  }

  async onSearch(event: any) {
    const query = event.target.value;
    if (this.searchQuery === query) return;

    this.searchQuery = query;
    this.searchOffset = 0;
    this.communities = [];

    if (!this.searchQuery.trim()) {
      this.offset = 0;
      await this.add_communities(14);
      return;
    }

    await this.add_search_results(14);
  }

  async add_search_results(count: number) {
    if (this.isLoading) return;
    this.isLoading = true;
    try {
      const results = await this.searchService.searchCommunities(this.searchQuery, count, this.searchOffset);
      results.forEach((communityData) => {
        this.communities.push({
          id: String(communityData.id),
          communityData: communityData,
          currentlySelected: communityData.id === this.selected_community?.id
        });
      });
      this.searchOffset += count;
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
          this.myCommunityIds.add(membership.communityId);
          try {
            const communityData = await this.communityDataService.getCommunity(membership.communityId);
            if (communityData) {
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
      if (this.searchQuery.trim()) {
        await this.add_search_results(10);
      } else {
        await this.add_communities(4);
      }
    }
  }

  async onJoin(communityId: number) {
    const confirmed = await this.dialogService.confirm({
      title: 'Join Community',
      message: 'Are you sure you want to join this community?',
      confirmLabel: 'Join'
    });

    if (!confirmed) return;

    try {
      await this.communityDataService.joinCommunity(communityId);
      this.myCommunityIds.add(communityId);
      if (!this.searchQuery.trim()) {
        await this.refreshList();
      }
    } catch (error) {
      console.error('Error joining community:', error);
    }
  }

  async onLeave(communityId: number) {
    const confirmed = await this.dialogService.confirm({
      title: 'Leave Community',
      message: 'Are you sure you want to leave this community?',
      confirmLabel: 'Leave',
      type: 'danger'
    });

    if (!confirmed) return;

    try {
      await this.communityDataService.leaveCommunity(communityId);
      this.myCommunityIds.delete(communityId);
      if (!this.searchQuery.trim()) {
        this.communities = this.communities.filter(c => c.communityData?.id !== communityId);
      }
    } catch (error) {
      console.error('Error leaving community:', error);
    }
  }

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

  checkMembership(communityId: number | undefined): boolean {
    return communityId !== undefined && this.myCommunityIds.has(communityId);
  }

}
