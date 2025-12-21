import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { SearchBoxMain } from '../../general/search-box-main/search-box-main';
import { ProfilePic } from "../profile-pic/profile-pic";
import { SidebarService } from '../../services/sidebar.service';
import { EventData } from '../../models/event-data';
import { CommunityData } from '../../models/community-data';

@Component({
  selector: 'top-bar',
  templateUrl: './top-bar.html',
  imports: [SearchBoxMain, ProfilePic],
  styleUrl: './top-bar.css',
  standalone: true
})
export class TopBar implements OnInit {
  @Output() eventSearch = new EventEmitter<EventData[]>();
  @Output() communitySearch = new EventEmitter<CommunityData[]>();

  constructor(private sidebarService: SidebarService) { }

  ngOnInit() {
  }


  toggleSidebar() {
    this.sidebarService.toggle();
  }

  onEventSearch(events: EventData[]) {
    this.eventSearch.emit(events);
  }

  onCommunitySearch(communities: CommunityData[]) {
    this.communitySearch.emit(communities);
  }

}
