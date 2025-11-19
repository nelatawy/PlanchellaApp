import {Component, ElementRef, EventEmitter, HostListener, Input, Output, ViewChild} from '@angular/core';
import {CommunityCard} from '../community-card/community-card';
import {EventData} from '../models/event-data';
import {CommunityCardData} from '../models/community-card';
import {CommunityData} from '../models/community-data';

@Component({
  selector: 'app-community-selector',
  imports: [
    CommunityCard
  ],
  templateUrl: './community-selector.html',
  styleUrl: './community-selector.css',
})
export class CommunitySelector {
    nums = Array.from({length : 4});
    communities : Array<CommunityCardData> = [];

    @Output("selection")
    selection_emitter : EventEmitter<CommunityData> = new EventEmitter<CommunityData>();

    emit_selection(event : CommunityData){
      this.selection_emitter.emit(event);

    }

    fetch_communities(count : number){
      let data : CommunityCardData = {
        communityData : {name : "CSED", communitySrc : "", notificationCount : 81},
        currentlySelected  : true
      };
      for (let i = 0; i < count; i++){
        // const componentRef = this.container.createComponent(EventCard);
        // componentRef.setInput("eventData" , data);
        this.communities.push(data);
      }
    }
    ngOnInit(){
      this.fetch_communities(14);
    }

    @HostListener('scroll', ['$event'])
    onScroll(e: any) {
      const el = e.target;
      if (el.scrollTop >= el.scrollHeight - el.clientHeight - 10) {
        this.fetch_communities(4);
      }
    }

}
