import { Component, ElementRef, EventEmitter, HostBinding, HostListener, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { EventSize } from '../models/Enums';
import { ProfilePic } from '../general/profile-pic/profile-pic';
import { SlicePipe } from '@angular/common';
import { EventType } from '../models/Enums';
import { EventData } from '../models/event-data';
import { EventDisplayData } from '../models/event-display-data';
import { Router } from '@angular/router';
import { EventDataService } from '../services/event-data-service';
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-event-card',
  standalone: true,
  imports: [ProfilePic, SlicePipe],
  templateUrl: './event-card.component.html',
  styleUrls: ['./event-card.component.css'],
})
export class EventCard implements OnInit {

  @HostBinding('class') get hostClasses() {
    return this.displayData?.event.eventSize || 'small';
  }

  @ViewChild('card', { static: false }) card!: ElementRef<HTMLDivElement>;

  @Output() cardClick = new EventEmitter<number>();

  constructor(
    private elementRef: ElementRef,
    private router: Router,
    private eventDataService: EventDataService,
    private toastService: ToastService
  ) { }

  navigateToDetails() {
    if (this.displayData?.event.id) {
      this.cardClick.emit(this.displayData.event.id);
      // this.router.navigate(['/event', this.displayData.event.id]);
    }
  }

  upVote: number = 0;
  downVote: number = 0;
  star: boolean = false;
  userVoteState: 'upvote' | 'downvote' | 'none' = 'none';

  @Input()
  displayData?: EventDisplayData = undefined;


  ngOnInit() {
    if (this.displayData?.event) {
      this.elementRef.nativeElement.classList.add(this.displayData.event.eventSize);
      this.upVote = this.displayData.event.upvoteCount || 0;
      this.downVote = this.displayData.event.downvoteCount || 0;
    }
  }

  protected readonly EventSize = EventSize;

  async upvote() {
    if (!this.displayData?.event.id) return;

    try {
      // If already upvoted, remove the vote
      if (this.userVoteState === 'upvote') {
        await this.eventDataService.unvoteEvent(this.displayData.event.id);
        this.upVote--;
        this.userVoteState = 'none';
        this.toastService.info('Vote removed');
      } else {
        // If downvoted, first remove downvote
        if (this.userVoteState === 'downvote') {
          this.downVote--;
        }

        await this.eventDataService.upvoteEvent(this.displayData.event.id);
        this.upVote++;
        this.userVoteState = 'upvote';
        this.toastService.success('Upvoted!');
      }
    } catch (error) {
      console.error('Error upvoting event:', error);
      this.toastService.error('Failed to upvote event');
    }
  }

  async downvote() {
    if (!this.displayData?.event.id) return;

    try {
      // If already downvoted, remove the vote
      if (this.userVoteState === 'downvote') {
        await this.eventDataService.unvoteEvent(this.displayData.event.id);
        this.downVote--;
        this.userVoteState = 'none';
        this.toastService.info('Vote removed');
      } else {
        // If upvoted, first remove upvote
        if (this.userVoteState === 'upvote') {
          this.upVote--;
        }

        await this.eventDataService.downvoteEvent(this.displayData.event.id);
        this.downVote++;
        this.userVoteState = 'downvote';
        this.toastService.success('Downvoted!');
      }
    } catch (error) {
      console.error('Error downvoting event:', error);
      this.toastService.error('Failed to downvote event');
    }
  }

  async toggleStar() {
    if (!this.displayData?.event.id) return;

    try {
      await this.eventDataService.toggleStarEvent(this.displayData.event.id);
      this.star = !this.star;
      this.toastService.success(this.star ? 'Event starred!' : 'Star removed');
    } catch (error) {
      console.error('Error toggling star:', error);
      this.toastService.error('Failed to star event');
    }
  }

  onPointerEnter() {
    setTimeout(() => { this.card.nativeElement.style.transition = ''; }, 300);
  }
  onPointerMove(e: PointerEvent) {
    if (!this.card) return;

    const el = this.card.nativeElement;
    const rect = el.getBoundingClientRect();

    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    const centerX = rect.width / 2;
    const centerY = rect.height / 2;

    const normX = (x - centerX) / centerX;
    const normY = (y - centerY) / centerY;

    const rotateX = normY * 8;
    const rotateY = normX * -7;


    el.style.transform = `rotateX(${rotateX}deg) rotateY(${rotateY}deg)`;
    el.style.boxShadow = `${normX * -6}px ${normY * -6}px 10px var(--card-shadow-hover-color)`;


  }


  onLeave() {
    if (!this.card) return;

    this.card.nativeElement.style.transform = '';
    this.card.nativeElement.style.boxShadow = '';
    this.card.nativeElement.style.transition = 'all 0.2s ease-in-out ';

  }
}
// @Input()
// eventSize: string = 'MID';
// // eventSize: EventSize = EventSizentSize.MID; // dynamic input
//
// @Input()
// eventType : string = EventType.HACKATHON;
//
// @Input()
// title: string = 'Event';
//
// @Input()
// creationDate: string = '2 days ago';
//
// @Input()
// userName: string = 'Mazen';
//
// @Input()
// profile_pic_url: string = "" ;
//
// @Input()
// description: string = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
//
