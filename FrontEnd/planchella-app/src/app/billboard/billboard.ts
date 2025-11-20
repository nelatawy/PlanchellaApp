import {Component, ElementRef, HostListener, Input, SimpleChanges, ViewChild, ViewContainerRef} from '@angular/core';
import {EventCard} from '../event-card/event-card.component';
import {EventSize, EventType} from '../models/Enums';
import {EventData} from '../models/event-data';
import {CommunityData} from '../models/community-data';
import {EventDataService} from '../services/event-data-service';
import {ActivatedRoute} from '@angular/router';
import {CommunityDataService} from '../services/community-data-service';


@Component({
  selector: 'app-billboard',
  standalone: true,
  imports: [EventCard],
  templateUrl: './billboard.html',
  styleUrls: ['./billboard.css'],
})
export class Billboard {

  // cardNums = Array.from({length : 4});
  cards : Array<EventData> = [];
  /* cards: any = [];

   addCard() {
     this.cards.push({id: Date.now()});
   }

   clearAll() {
     this.cards = [];
   }*/
  @Input()
  communityData : CommunityData = {name : ""};


  @ViewChild('container', { read: ViewContainerRef, static: true })
  container!: ViewContainerRef;

  constructor(private elementRef : ElementRef,
              // private route: ActivatedRoute,
              private communityDataService: CommunityDataService,
              private eventDataService : EventDataService) {

    // route.paramMap.subscribe(map => {
    //   if (map.has('name')) {
    //     const community_name = map.get('name') || '';
    //     // load events for the community name given..
    //     // 1. Get the cached service names
    //     this.communityData = this.communityDataService.get_community(community_name) || this.communityData;
    //     // 2. If the name exists, fetch the events
    //     this.add_events(5);
    //   }
    // })
  }

  async add_events(count : number){
    let events : EventData[] | undefined = await this.eventDataService.fetch_events(count, this.communityData.name);
    events?.forEach((event : EventData)=>{
      this.cards.push(event);
    });
  }

  async ngOnInit(){
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
    const el = e.target;
    if (el.scrollTop >= el.scrollHeight - el.clientHeight - 10) {
      await this.add_events(10);
    }
  }

  //
  // // Keep track of dynamically created cards
  // cards: any = [];
  //
  // // Add a new Event card
  // addCard() {
  //   if (!this.container) return;
  //
  //   const componentRef = this.container.createComponent(Event);
  //
  //   // Set inputs dynamically
  //   componentRef.setInput('eventSize', EventSize.MID);
  //   componentRef.setInput('title', 'Dynamic Event');
  //   componentRef.setInput('description', 'This is a dynamically added event card.');
  //   componentRef.setInput('userName', 'hamada');
  //
  //   componentRef.changeDetectorRef.detectChanges();
  //   // Store reference
  //   this.cards.push(componentRef);
  // }
  //
  // clearAll() {
  //   this.cards.forEach(card => card.destroy());
  //   this.cards = [];
  //   this.container.clear();
  // }
  protected readonly EventSize = EventSize;
  protected readonly EventType = EventType;

  private loadNewConfig(newId: any) {

  }
}
