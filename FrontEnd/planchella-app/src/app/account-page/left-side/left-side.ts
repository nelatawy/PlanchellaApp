import {Component, inject, Input} from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {User} from '../../models/user';
import {Router} from '@angular/router';

@Component({
  selector: 'app-left-side',
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './left-side.html',
  styleUrl: './left-side.css',
})
export class LeftSide {
    @Input()
    user: User | undefined;

    private router = inject(Router);

    logout() {
      // Clear session/token and redirect to login page
      console.log('Logout clicked');
      this.router.navigate(['/signin']);
    }
}
