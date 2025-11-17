import { Component } from '@angular/core';
import {Billboard} from '../billboard/billboard';
import {TopBar} from '../general/top-bar/top-bar';
import {CommunitySelector} from '../community-selector/community-selector';

@Component({
  selector: 'app-main-page',
  imports: [
    Billboard,
    TopBar,
    CommunitySelector
  ],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css',
})
export class MainPage {

}
