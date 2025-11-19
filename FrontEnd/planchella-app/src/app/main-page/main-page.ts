import {Component, ElementRef, ViewChild} from '@angular/core';
import {Billboard} from '../billboard/billboard';
import {TopBar} from '../general/top-bar/top-bar';
import {CommunitySelector} from '../community-selector/community-selector';
import {CommunityData} from '../models/community-data';
import {EventBuilder} from '../event-builder/event-builder';

@Component({
  selector: 'app-main-page',
  imports: [
    Billboard,
    TopBar,
    CommunitySelector,
    EventBuilder
  ],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css',
})
export class MainPage {

  protected readonly window = window;
  @ViewChild('overlay', {static : false})overlay! : ElementRef<HTMLDivElement>;
  @ViewChild('builder', {static : false, read : ElementRef})builder! : ElementRef;


  communityData : CommunityData = {name : "CSED", communitySrc : ""};

  // ngAfterViewInit(){
  //   window.alert("picked");
  // }
  select_community(communityData : CommunityData){
    this.communityData = communityData;
    window.alert("selected");
  }

  show_creation_page() {
    this.overlay.nativeElement.style.zIndex = '1500';

    setTimeout(() => {
      this.builder.nativeElement.style.transform = 'translateY(0)';
      this.overlay.nativeElement.style.opacity = '1';
    }, 50);
  }

  hide_creation_page() {

    setTimeout(() => {
      this.overlay.nativeElement.style.opacity = '0';
      this.builder.nativeElement.style.transform = 'translateY(100vh)';
    }, 50);
    this.overlay.nativeElement.style.zIndex = '-1';
  }

  onBuilderClick(event : MouseEvent){
    event.stopPropagation();
  }

}
