import { Component } from '@angular/core';
import {PostsPage} from './posts-page/posts-page';
import {SettingsPage} from './settings-page/settings-page';
import {StaredPostsPage} from './stared-posts-page/stared-posts-page';

@Component({
  selector: 'app-right-side',
  imports: [
    PostsPage,
    SettingsPage,
    StaredPostsPage
  ],
  templateUrl: './right-side.html',
  styleUrl: './right-side.css',
})
export class RightSide {
  // This is our "Single Source of Truth".
  // 'posts' is the default value.
  selectedTab: string = 'posts';

  // This function updates our variable.
  selectTab(tabName: string) {
    this.selectedTab = tabName;
  }
}
