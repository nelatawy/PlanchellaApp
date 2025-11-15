import {Component, ViewChild, ViewContainerRef} from '@angular/core';
import { Event } from '../event/event';

@Component({
  selector: 'app-billboard',
  imports: [],
  templateUrl: './billboard.html',
  styleUrl: './billboard.css',
})
export class Billboard {

  // Should Generate Events here
  numberOfCards: number = 10;

  @ViewChild("container", { read: ViewContainerRef }) container!: ViewContainerRef;

  addCard() {
    const componentRef = this.container.createComponent(Event);
    componentRef.setInput('size', 'LARGE'); // pass input dynamically
  }

  clearAll() {
    this.container.clear();
  }
}
