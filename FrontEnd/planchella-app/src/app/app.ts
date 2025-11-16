import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TopBar } from './general/top-bar/top-bar';
import { SearchBoxMain } from './general/search-box-main/search-box-main';
import { ProfilePic } from './general/profile-pic/profile-pic';
import { EventCard } from './event-card/event-card.component';
import { Billboard } from './billboard/billboard';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TopBar, SearchBoxMain, ProfilePic, EventCard, Billboard],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('planchella-app');
}
