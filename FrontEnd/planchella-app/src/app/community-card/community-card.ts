import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommunityData } from '../models/community-data';
import { CommunityCardData } from '../models/community-card';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-community-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './community-card.html',
  styleUrl: './community-card.css',
})
export class CommunityCard {

  @Input()
  cardData?: CommunityCardData = undefined;

  @Input()
  currentlySelected: boolean = false;

  @Input()
  isMember: boolean = false;

  @Output("selection")
  selection_emitter: EventEmitter<CommunityData> = new EventEmitter<CommunityData>();

  @Output()
  join: EventEmitter<number> = new EventEmitter<number>();

  @Output()
  leave: EventEmitter<number> = new EventEmitter<number>();

  emit_selection() {
    if (this.cardData && this.cardData.communityData) {
      this.selection_emitter.emit(this.cardData.communityData);
    }
  }

  onJoin(event: Event) {
    event.stopPropagation();
    if (this.cardData?.communityData?.id) {
      this.join.emit(this.cardData.communityData.id);
    }
  }

  onLeave(event: Event) {
    event.stopPropagation();
    if (this.cardData?.communityData?.id) {
      this.leave.emit(this.cardData.communityData.id);
    }
  }
}
