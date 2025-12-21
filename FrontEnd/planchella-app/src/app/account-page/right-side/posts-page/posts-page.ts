import { Component, OnInit } from '@angular/core';
import {EmptyPage} from '../empty-page/empty-page';
import { EventDataService } from '../../../services/event-data-service';
import { AuthService } from '../../../services/auth-service';
import { CommonModule } from '@angular/common';
import { EventComponent } from '../../../event/event';

@Component({
  selector: 'app-posts-page',
  imports: [
    EmptyPage,
    CommonModule,
    EventComponent
  ],
  templateUrl: './posts-page.html',
  styleUrl: './posts-page.css',
})
export class PostsPage implements OnInit {
  post: any[] = [];
  isLoading: boolean = true;
  error: string = '';

  constructor(
    private eventDataService: EventDataService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadUserEvents();
  }

  async loadUserEvents() {
    try {
      this.isLoading = true;
      const userId = this.authService.getCurrentUserId();
      
      if (!userId) {
        this.error = 'User not authenticated';
        this.isLoading = false;
        return;
      }

      // Fetch events created by the user
      const result = await this.eventDataService.getEventsByAuthor(userId, 50, 0);
      this.post = Array.isArray(result) ? result : [];
      this.error = '';
    } catch (err) {
      console.error('Error loading user events:', err);
      this.error = 'Failed to load events';
      this.post = [];
    } finally {
      this.isLoading = false;
    }
  }
}
