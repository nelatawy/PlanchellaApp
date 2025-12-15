import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { CommunityData } from '../models/community-data';
import { CommunityCardData } from '../models/community-card';

@Component({
  selector: 'app-community-card',
  templateUrl: './community-card.html',
  styleUrl: './community-card.css',
})
export class CommunityCard {
  @ViewChild('card', { static: false }) card!: ElementRef<HTMLDivElement>;
  @ViewChild('highlight', { static: false }) highlight_text!: ElementRef<HTMLDivElement>;

  @Input()
  cardData?: CommunityCardData = undefined;

  @Input()
  currentlySelected: boolean = false;

  @Output("selection")
  selection_emitter: EventEmitter<CommunityData> = new EventEmitter<CommunityData>();

  emit_selection() {
    if (this.cardData && this.cardData.communityData) {
      this.selection_emitter.emit(this.cardData.communityData);
    }
  }


  show() {
    this.highlight_text.nativeElement.style.opacity = '0.5';
  }
  hide() {
    this.highlight_text.nativeElement.style.opacity = '0';
  }
  redirect() {

  }
}
