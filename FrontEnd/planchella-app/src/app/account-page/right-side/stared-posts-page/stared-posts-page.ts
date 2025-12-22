import { Component, OnInit } from '@angular/core';
import { EmptyPage } from '../empty-page/empty-page';
import { AuthService } from '../../../services/auth-service';
import { CommonModule } from '@angular/common';
import { UserDataService } from '../../../services/user-data-service';
import { EventCard } from "../../../event-card/event-card.component";
import { EventData } from '../../../models/event-data';
import { firstValueFrom } from 'rxjs';
import { EventDisplayData } from '../../../models/event-display-data';

@Component({
  selector: 'app-stared-posts-page',
  standalone: true,
  imports: [
    EmptyPage,
    CommonModule,
    EventCard,
  ],
  templateUrl: './stared-posts-page.html',
  styleUrl: './stared-posts-page.css',
})
export class StaredPostsPage implements OnInit {
  events: EventDisplayData[] = [];
  isLoading: boolean = true;
  error: string = '';
  offset: number = 0;

  constructor(
    private userService: UserDataService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadStarredEvents(10);
  }

  async loadStarredEvents(count: number) {
    this.isLoading = true;
    const userId = this.authService.getCurrentUserId();

    if (!userId) {
      this.error = 'User not authenticated';
      this.isLoading = false;
      return;
    }

    try {
      let events: EventData[] | undefined = await this.userService.fetch_my_starred_events(count, this.offset);

      if (events) {
        const displayDataPromises = events.map(async (event) => {
          const author = await firstValueFrom(this.userService.getUserById(event.authorId));
          return { event, author } as EventDisplayData;
        });

        const newCards = await Promise.all(displayDataPromises);
        this.events.push(...newCards);
      }

      this.offset += count;
    } catch (error) {
      console.error('Error fetching starred events:', error);
      this.error = 'Failed to load starred events';
    } finally {
      this.isLoading = false;
    }
  }
}
