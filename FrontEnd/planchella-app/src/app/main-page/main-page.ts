import { Component, ElementRef, ViewChild } from '@angular/core';
import { Billboard } from '../billboard/billboard';
import { TopBar } from '../general/top-bar/top-bar';
import { CommunitySelector } from '../community-selector/community-selector';
import { CommunityData } from '../models/community-data';
import { EventBuilder } from '../event-builder/event-builder';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { CommunityDataService } from '../services/community-data-service';
import { SidebarService } from '../services/sidebar.service';

@Component({
  selector: 'app-main-page',
  imports: [
    Billboard,
    TopBar,
    CommunitySelector,
    EventBuilder,
  ],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css',
})
export class MainPage {
  communityData: CommunityData = { id: 0, name: "CSED" };

  isOpen = false;

  constructor(private route: ActivatedRoute, private router: Router, private communityDataService: CommunityDataService, private sidebarService: SidebarService) {
    route.paramMap.subscribe(map => {
      if (map.has('name')) {
        const community_name = map.get('name') || '';
        // load events for the community name given..
        // 1. Get the cached service names
        this.communityData = this.communityDataService.get_community(community_name) || this.communityData;
        // 2. If the name exists, fetch the events

      }
    })

  }
  protected readonly window = window;
  @ViewChild('overlay', { static: false }) overlay!: ElementRef<HTMLDivElement>;
  @ViewChild('builder', { static: false, read: ElementRef }) builder!: ElementRef;


  ngOnInit() {
    this.sidebarService.open$.subscribe(state => {
      this.isOpen = state;
    })
  }

  // ngAfterViewInit(){
  //   window.alert("picked");
  // }
  select_community(communityData: CommunityData) {
    // this.communityData = communityData;
    this.router.navigate(['main', communityData.name]);
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

  onBuilderClick(event: MouseEvent) {
    event.stopPropagation();
  }

}
