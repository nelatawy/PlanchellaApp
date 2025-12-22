import { Component, ElementRef, EventEmitter, HostListener, Input, Output, SimpleChanges, ViewChild, ViewContainerRef } from '@angular/core';
import { EventCard } from '../event-card/event-card.component';
import { EventSize, EventType } from '../models/Enums';
import { EventData } from '../models/event-data';
import { firstValueFrom } from 'rxjs';
import { CommunityData } from '../models/community-data';
import { EventDataService } from '../services/event-data-service';
import { CommunityDataService } from '../services/community-data-service';
import { EventDisplayData } from '../models/event-display-data';
import { UserDataService } from '../services/user-data-service';
import { forkJoin, map } from 'rxjs';


@Component({
  selector: 'app-billboard',
  standalone: true,
  imports: [EventCard],
  templateUrl: './billboard.html',
  styleUrls: ['./billboard.css'],
})
export class Billboard {

  @Output() eventSelected = new EventEmitter<number>();

  cards: Array<EventDisplayData> = [];
  isLoading: boolean = false;
  isLoadingSearch: boolean = false;
  offset: number = 0;

  @Input()
  communityData: CommunityData | undefined = { id: 0, name: "" };


  @ViewChild('container', { read: ViewContainerRef, static: true })
  container!: ViewContainerRef;

  constructor(private elementRef: ElementRef,
    private communityDataService: CommunityDataService,
    private eventDataService: EventDataService,
    private userDataService: UserDataService) {
  }

  async add_events(count: number) {
    if (!this.communityData) {
      console.warn('Cannot fetch events: No community data selected.');
      return;
    }

    this.isLoading = true;
    try {
      if (this.communityData.id == undefined) {
        console.warn('Cannot fetch events: No community ID provided.');
        return;
      }
      let events: EventData[] | undefined = await this.communityDataService.fetch_community_events(count, this.offset, this.communityData.id);

      if (events) {
        const displayDataPromises = events.map(async (event) => {
          const author = await firstValueFrom(this.userDataService.getUserById(event.authorId));
          return { event, author } as EventDisplayData;
        });

        const newCards = await Promise.all(displayDataPromises);

        // Filter out expired timed events
        const now = new Date();
        const activeCards = newCards.filter(card =>
          !card.event.hasTime || new Date(card.event.eventEndDate) > now
        );

        this.cards.push(...activeCards);
      }

      this.offset += count;
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
      this.offset = 0; // Reset offset to start from the beginning
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

  onCardClick(id: number) {
    this.eventSelected.emit(id);
  }

  async loadSearchResults(events: EventData[]) {
    // Prevent concurrent search operations
    if (this.isLoadingSearch) {
      console.log('Already loading, skipping search request');
      return;
    }

    // Clear current cards and load search results
    this.cards = [];
    this.offset = 0;

    if (!events || events.length === 0) {
      console.log('No search results to display');
      return;
    }

    this.isLoadingSearch = true;
    try {
      const displayDataPromises = events.map(async (event) => {
        const author = await firstValueFrom(this.userDataService.getUserById(event.authorId));
        return { event, author } as EventDisplayData;
      });

      const newCards = await Promise.all(displayDataPromises);

      // Filter out expired timed events
      const now = new Date();
      this.cards = newCards.filter(card =>
        !card.event.hasTime || new Date(card.event.eventEndDate) > now
      );
    } catch (error) {
      console.error('Error loading search results:', error);
    } finally {
      this.isLoadingSearch = false;
    }
  }

  async refreshList() {
    this.cards = [];
    this.offset = 0;
    await this.add_events(10);
  }

  async refreshEvent(eventId: number) {
    try {
      const updatedEvent = await this.eventDataService.getEvent(eventId);
      if (updatedEvent) {
        const author = await firstValueFrom(this.userDataService.getUserById(updatedEvent.authorId));
        const index = this.cards.findIndex(card => card.event.id === eventId);
        if (index !== -1) {
          this.cards[index] = { event: updatedEvent, author };
        }
      }
    } catch (error) {
      console.error('Error refreshing single event:', error);
    }
  }

  private loadNewConfig(newId: any) {

  }
}
