import { Component, ElementRef, HostListener, Input, SimpleChanges, ViewChild, ViewContainerRef } from '@angular/core';
import { EventCard } from '../event-card/event-card.component';
import { EventSize, EventType } from '../models/Enums';
import { EventData } from '../models/event-data';
import { CommunityData } from '../models/community-data';
import { EventDataService } from '../services/event-data-service';
import { ActivatedRoute } from '@angular/router';
import { CommunityDataService } from '../services/community-data-service';


@Component({
  selector: 'app-billboard',
  standalone: true,
  imports: [EventCard],
  templateUrl: './billboard.html',
  styleUrls: ['./billboard.css'],
})
export class Billboard {

  cards: Array<EventData> = [];
  isLoading: boolean = false;

  @Input()
  communityData: CommunityData = {id: "", name: "" };


  @ViewChild('container', { read: ViewContainerRef, static: true })
  container!: ViewContainerRef;

  constructor(private elementRef: ElementRef,
    private communityDataService: CommunityDataService,
    private eventDataService: EventDataService) {
  }

  async add_events(count: number) {
    this.isLoading = true;
    try {
      let events: EventData[] | undefined = await this.eventDataService.fetch_events(count, this.communityData.name);
      events?.forEach((event: EventData) => {
        this.cards.push(event);
      });
    } catch (error) {
      console.error('Error fetching events:', error);
    } finally {
      this.isLoading = false;
    }
  }

  async ngOnInit() {
    await this.add_events(10);
  }

  async ngOnChanges(changes: SimpleChanges) {
    // Check if the specific property you care about has changed
    if (changes['communityData']) {
      this.cards = [];
      await this.add_events(10);
    }
  }

  @HostListener('scroll', ['$event'])
  async onScroll(e: any) {
    if (this.isLoading) return; // Prevent multiple simultaneous requests

    const el = e.target;
    if (el.scrollTop >= el.scrollHeight - el.clientHeight - 10) {
      await this.add_events(10);
    }
  }

  protected readonly EventSize = EventSize;
  protected readonly EventType = EventType;

  private loadNewConfig(newId: any) {

  }
}
