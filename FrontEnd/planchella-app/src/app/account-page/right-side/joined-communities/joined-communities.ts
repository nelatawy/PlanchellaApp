import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserDataService } from '../../../services/user-data-service';
import { CommunityDataService } from '../../../services/community-data-service';
import { CommunityData } from '../../../models/community-data';
import { Membership } from '../../../models/membership';

@Component({
  selector: 'app-joined-communities',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './joined-communities.html',
  styleUrl: './joined-communities.css'
})
export class JoinedCommunitiesPage implements OnInit {
  communities: CommunityData[] = [];
  loading: boolean = true;
  error: string = '';
  userId: number = 0;

  constructor(
    private userDataService: UserDataService,
    private communityDataService: CommunityDataService
  ) {}

  ngOnInit() {
    this.loadJoinedCommunities();
  }

  async loadJoinedCommunities() {
    try {
      this.loading = true;
      this.error = '';
      
      const userId = Number(localStorage.getItem('userId'));
      this.userId = userId;

      // Fetch memberships to see which communities the user joined
      const memberships = await this.userDataService.fetch_user_communities(100, 0, userId);
      
      // Fetch community details for each membership
      const communityPromises = memberships.map(membership =>
        this.communityDataService.getCommunity(membership.communityId)
      );
      
      this.communities = await Promise.all(communityPromises);
      this.loading = false;
    } catch (err) {
      console.error('Error loading joined communities:', err);
      this.error = 'Failed to load joined communities';
      this.loading = false;
    }
  }

  navigateToCommunity(communityId: number) {
    // TODO: Implement navigation to community page
    window.location.href = `/community/${communityId}`;
  }
}
