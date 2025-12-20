import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopBar } from '../general/top-bar/top-bar';
import { Billboard } from '../billboard/billboard';
import { SidebarService } from '../services/sidebar.service';
import { CommunitySelector } from '../community-selector/community-selector';
import { EventBuilder } from '../event-builder/event-builder';
import { CommunityData } from '../models/community-data';
import { CommunityBuilder } from '../community-builder/community-builder';

@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [
    TopBar,
    CommunitySelector,
    Billboard,
    EventBuilder,
    EventBuilder,
    CommunityBuilder,
    CommonModule
  ],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css'
})
export class MainPage {
  @ViewChild("builder") eventBuilder!: EventBuilder;
  @ViewChild("overlay") overlay!: ElementRef;

  @ViewChild("communityBuilder", { read: ElementRef }) communityBuilder!: ElementRef;
  @ViewChild("communityOverlay") communityOverlay!: ElementRef;

  @ViewChild(CommunitySelector) communitySelector!: CommunitySelector;

  isOpen = false;

  communityData: CommunityData | undefined = undefined;

  constructor(private sidebarService: SidebarService) {
    this.sidebarService.open$.subscribe(
      (isOpen) => {
        this.isOpen = isOpen;
      }
    );
  }

  show_creation_page() {
    this.overlay.nativeElement.style.opacity = "1";
    this.overlay.nativeElement.style.zIndex = "1000";
    this.eventBuilder.toggleDropdown();
  }

  hide_creation_page() {
    this.overlay.nativeElement.style.opacity = "0";
    this.overlay.nativeElement.style.zIndex = "-1";
  }


  onBuilderClick(event: MouseEvent) {
    event.stopPropagation();
  }

  select_community(event: CommunityData) {
    this.communityData = event;
  }

  show_community_builder() {
    this.communityOverlay.nativeElement.style.opacity = "1";
    this.communityOverlay.nativeElement.style.zIndex = "1000";
    this.communityBuilder.nativeElement.style.transform = "translateY(0)";
  }

  hide_community_builder() {
    this.communityOverlay.nativeElement.style.opacity = "0";
    this.communityOverlay.nativeElement.style.zIndex = "-1";
    this.communityBuilder.nativeElement.style.transform = "translateY(100vh)";
  }

  onCommunityCreated() {
    this.hide_community_builder();
    this.communitySelector.refreshList();
  }
}
