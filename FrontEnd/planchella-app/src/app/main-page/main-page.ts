import { Component } from '@angular/core';
import {Billboard} from '../billboard/billboard';
import {TopBar} from '../general/top-bar/top-bar';
import {CommunitySelector} from '../community-selector/community-selector';
import {CommunityData} from '../models/community-data';

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

  protected readonly window = window;

  communityData : CommunityData = {name : "CSED", communitySrc : ""};

  select_community(communityData : CommunityData){
    this.communityData = communityData;
    window.alert("selected");
  }
}
