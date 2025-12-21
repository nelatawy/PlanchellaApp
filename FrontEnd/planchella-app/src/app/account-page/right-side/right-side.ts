import { Component, inject, Input } from '@angular/core';
import { PostsPage } from './posts-page/posts-page';
import { SettingsPage } from './settings-page/settings-page';
import { StaredPostsPage } from './stared-posts-page/stared-posts-page';
import { JoinedCommunitiesPage } from './joined-communities/joined-communities';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-right-side',
  imports: [
    PostsPage,
    SettingsPage,
    StaredPostsPage,
    JoinedCommunitiesPage
  ],
  templateUrl: './right-side.html',
  styleUrl: './right-side.css',
})
export class RightSide {
  @Input() userId?: number;

  private authService = inject(AuthService);

  // This is our "Single Source of Truth".
  // 'posts' is the default value.
  selectedTab: string = 'posts';

  // Check if viewing own profile
  get isOwnProfile(): boolean {
    const currentUserId = this.authService.getCurrentUserId();
    return currentUserId !== null && this.userId === currentUserId;
  }

  // This function updates our variable.
  selectTab(tabName: string) {
    this.selectedTab = tabName;
  }
}
