import {Component, ElementRef, Input, ViewChild} from '@angular/core';

@Component({
  selector: 'app-community-card',
  imports: [],
  templateUrl: './community-card.html',
  styleUrl: './community-card.css',
})
export class CommunityCard {
  @ViewChild('card', { static: false }) card!: ElementRef<HTMLDivElement>;
  @ViewChild('highlight', { static: false }) highlight_text!: ElementRef<HTMLDivElement>;

  @Input()
  name : string = "CSED";

  @Input()
  community_id:string = "1214124";

  @Input()
  notification_count : number = 99;

  @Input()
  currently_selected : boolean = false;

  show(){
    this.highlight_text.nativeElement.style.opacity = '0.5';
  }
  hide(){
    this.highlight_text.nativeElement.style.opacity = '0';
  }
  redirect(){

  }
}
