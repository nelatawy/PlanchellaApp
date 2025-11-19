import {Component, ViewChild, ViewContainerRef, ComponentRef, Input, ElementRef, HostListener} from '@angular/core';
import { EventCard } from '../event-card/event-card.component';
import {EventSize, EventType} from '../general/Enums';
import {EventData} from '../models/event-data';
import {CommunityData} from '../models/community-data';

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
  communityData : CommunityData = {name : "", communitySrc : ""};


  @ViewChild('container', { read: ViewContainerRef, static: true })
  container!: ViewContainerRef;

  constructor(private elementRef : ElementRef) {
  }

  fetch_events(count : number){
    let data : EventData = {eventType: EventType.HACKATHON,
            eventSize : EventSize.LARGE,
            authorData : {picUrl : "",
                          name : "Nour",
                          accountUrl : ""},
            title : "Aloha",
            description : "This is a placeholder description",
            creationDate: new Date().toLocaleDateString()
    };
    for (let i = 0; i < count; i++){
      // const componentRef = this.container.createComponent(EventCard);
      // componentRef.setInput("eventData" , data);
      this.cards.push(data);
    }
  }
    ngOnInit(){
      this.fetch_events(10);
    }

  @HostListener('scroll', ['$event'])
  onScroll(e: any) {
    const el = e.target;
    if (el.scrollTop >= el.scrollHeight - el.clientHeight - 10) {
      this.fetch_events(10);
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
}
