import {Component, ElementRef, Input, ViewChild} from '@angular/core';
import {CommunityCard} from '../community-card/community-card';

@Component({
  selector: 'app-community-selector',
  imports: [
    CommunityCard
  ],
  templateUrl: './community-selector.html',
  styleUrl: './community-selector.css',
})
export class CommunitySelector {
    nums = Array.from({length : 4});



}
