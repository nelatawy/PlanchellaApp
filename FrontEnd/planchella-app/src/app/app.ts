import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PopupNotificationComponent } from './general/popup-notification/popup-notification';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, PopupNotificationComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('planchella-app');
}
