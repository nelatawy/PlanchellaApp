import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-registration',
  imports: [
    RouterLink,
    RouterOutlet,
    FormsModule
  ],
  templateUrl: './registration.html',
  styleUrl: './registration.css',
})
export class Registration {

}
