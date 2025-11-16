import { Component, ViewChild, ViewContainerRef, ComponentRef } from '@angular/core';
import { EventCard } from '../event-card/event-card.component';
import {EventSize, EventType} from '../general/Enums';

@Component({
  selector: 'app-billboard',
  standalone: true,
  imports: [EventCard],
  templateUrl: './billboard.html',
  styleUrls: ['./billboard.css'],
})
export class Billboard {

  cardNums = Array.from({length : 4});
 /* cards: any = [];

  addCard() {
    this.cards.push({id: Date.now()});
  }

  clearAll() {
    this.cards = [];
  }*/

  // @ViewChild('billboardContainer', { read: ViewContainerRef, static: true })
  // container!: ViewContainerRef;
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
