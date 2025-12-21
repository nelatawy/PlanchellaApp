import { Component, OnInit } from '@angular/core';
import { LeftSide } from './left-side/left-side';
import { TopBar } from '../general/top-bar/top-bar';
import { RightSide } from './right-side/right-side';
import { User } from '../models/user';
import { UserDataService } from '../services/user-data-service';
import { AuthService } from '../services/auth-service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-account-page',
  imports: [
    LeftSide,
    TopBar,
    RightSide
  ],
  templateUrl: './account-page.html',
  styleUrl: './account-page.css',
})
export class AccountPage implements OnInit {

  // User data - will be loaded from backend
  user: User = this.getDefaultUser();

  constructor(
    private userDataService: UserDataService,
    private authService: AuthService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const userId = params['id'] ? Number(params['id']) : undefined;
      this.loadUserData(userId);
    });
  }

  private loadUserData(userId?: number): void {
    if (!this.authService.isAuthenticated()) {
      console.error('User not authenticated - using default data');
      return;
    }

    const userObservable = userId
      ? this.userDataService.getUserById(userId)
      : this.userDataService.getCurrentUser();

    userObservable.subscribe({
      next: (userData) => {
        this.user = userData;
      },
      error: (error) => {
        console.error('Error loading user data:', error);
        this.user = this.getDefaultUser();
      }
    });
  }

  private getDefaultUser(): User {
    return {
      id: 0,
      name: "Guest User",
      bio: "No bio available",
      education: "Not specified",
      address: "Not specified",
      email: "guest@example.com",
      picUrl: "",
      accountUrl: ""
    };
  }
}
