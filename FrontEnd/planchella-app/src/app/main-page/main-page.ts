import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopBar } from '../general/top-bar/top-bar';
import { Billboard } from '../billboard/billboard';
import { SidebarService } from '../services/sidebar.service';
import { CommunitySelector } from '../community-selector/community-selector';
import { EventBuilder } from '../event-builder/event-builder';
import { CommunityData } from '../models/community-data';
import { CommunityBuilder } from '../community-builder/community-builder';
import { EventComponent } from '../event/event';

@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [TopBar, CommunitySelector, Billboard, EventBuilder, CommunityBuilder, EventComponent],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css'
})
export class MainPage {
  @ViewChild("builder", { read: ElementRef }) eventBuilderElement!: ElementRef;
  @ViewChild("overlay") overlay!: ElementRef;

  @ViewChild("communityBuilder", { read: ElementRef }) communityBuilder!: ElementRef;
  @ViewChild("communityOverlay") communityOverlay!: ElementRef;

  @ViewChild(CommunitySelector) communitySelector!: CommunitySelector;

  isOpen = false;

  communityData: CommunityData | undefined = undefined;
  selectedEventId: number | undefined = undefined;
  isEventDetailsOpen = false;

  constructor(private sidebarService: SidebarService) {
    this.sidebarService.open$.subscribe(
      (isOpen: boolean) => {
        this.isOpen = isOpen;
      }
    );
  }

  show_creation_page() {
    this.overlay.nativeElement.style.opacity = "1";
    this.overlay.nativeElement.style.zIndex = "1000";
    this.eventBuilderElement.nativeElement.style.transform = "translateY(0)";
  }

  hide_creation_page() {
    this.overlay.nativeElement.style.opacity = "0";
    this.overlay.nativeElement.style.zIndex = "-1";
    this.eventBuilderElement.nativeElement.style.transform = "translateY(100vh)";
  }


  onBuilderClick(event: MouseEvent) {
    event.stopPropagation();
  }

  select_community(event: CommunityData) {
    this.communityData = event;
    this.sidebarService.toggle();
  }

  show_community_builder() {
    this.communityOverlay.nativeElement.style.opacity = "1";
    this.communityOverlay.nativeElement.style.zIndex = "1000";
  }

  hide_community_builder() {
    this.communityOverlay.nativeElement.style.opacity = "0";
    this.communityOverlay.nativeElement.style.zIndex = "-1";
  }

  onCommunityCreated() {
    this.hide_community_builder();
    this.communitySelector.refreshList();
  }

  show_event_details(id: number) {
    this.selectedEventId = id;
    this.isEventDetailsOpen = true;
  }

  hide_event_details() {
    this.isEventDetailsOpen = false;
    this.selectedEventId = undefined;
  }
}
