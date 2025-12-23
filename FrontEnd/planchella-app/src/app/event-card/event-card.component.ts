import { Component, ElementRef, EventEmitter, HostBinding, Input, OnInit, OnChanges, Output, SimpleChanges, ViewChild } from '@angular/core';
import { SafeUrl } from '@angular/platform-browser';
import { ProfilePic } from '../general/profile-pic/profile-pic';
import { SlicePipe } from '@angular/common';
import { Router } from '@angular/router';
import { EventDataService } from '../services/event-data-service';
import { ToastService } from '../services/toast.service';
import { AttachmentService } from '../services/attachment-service';
import { MimeTypeUtils, UrlUtils } from '../services/utils';
import { EventDisplayData } from '../models/event-display-data';
import { EventSize } from '../models/Enums';
import { EventAttachment } from '../models/event-attachment';

@Component({
  selector: 'app-event-card',
  standalone: true,
  imports: [ProfilePic, SlicePipe],
  templateUrl: './event-card.component.html',
  styleUrls: ['./event-card.component.css'],
})
export class EventCard implements OnInit, OnChanges {

  @HostBinding('class') get hostClasses() {
    return this.displayData?.event.eventSize || 'small';
  }

  @ViewChild('card', { static: false }) card!: ElementRef<HTMLDivElement>;

  @Output() cardClick = new EventEmitter<number>();

  cardImageUrl: SafeUrl | null = null;

  constructor(
    private elementRef: ElementRef,
    private router: Router,
    private eventDataService: EventDataService,
    private toastService: ToastService,
    private attachmentService: AttachmentService,
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
    this.syncState();
    this.initCardImage();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['displayData'] && !changes['displayData'].firstChange) {
      this.syncState();
    }
  }

  private syncState() {
    if (this.displayData?.event) {
      this.elementRef.nativeElement.classList.add(this.displayData.event.eventSize);
      this.upVote = this.displayData.event.upvoteCount || 0;
      this.downVote = this.displayData.event.downvoteCount || 0;
      this.star = this.displayData.event.isStarred || false;

      // Set initial vote state based on event data
      if (this.displayData.event.isUpvoted) {
        this.userVoteState = 'upvote';
      } else if (this.displayData.event.isDownVoted) {
        this.userVoteState = 'downvote';
      } else {
        this.userVoteState = 'none';
      }
    }
  }

  private initCardImage() {
    if (this.displayData?.event.attachments) {
      const firstImage = this.displayData.event.attachments.find((att: EventAttachment) =>
        MimeTypeUtils.isImage(att.mimeType)
      );

      if (firstImage) {
        this.attachmentService.getAttachmentUrl(firstImage.id).subscribe({
          next: (url) => {
            this.cardImageUrl = url;
          },
          error: (error) => {
            console.error('Error loading card image preview:', error);
            this.cardImageUrl = null;
          }
        });
      }
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


  getRemainingTime(): string {
    return this.getExpirationTime();
  }

  getExpirationTime(): string {
    if (!this.displayData?.event || !this.displayData.event.expirationDate) return '';

    const now = new Date();
    const end = new Date(this.displayData.event.expirationDate);
    const diff = end.getTime() - now.getTime();

    if (diff <= 0) return 'Removed';

    const hours = Math.floor(diff / (1000 * 60 * 60));
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));

    if (hours > 24) {
      const days = Math.floor(hours / 24);
      return `Removed in ${days}d ${hours % 24}h`;
    }

    return `Removed in ${hours}h ${minutes}m`;
  }

  getFormattedDate(): string {
    if (!this.displayData?.event.hasTime || !this.displayData.event.eventStartDate) return '';
    const now = new Date();
    const start = new Date(this.displayData.event.eventStartDate);
    const diff = start.getTime() - now.getTime();

    if (diff <= 0) {
      // If started, show when it ends using eventEndDate
      if (this.displayData.event.eventEndDate) {
        const end = new Date(this.displayData.event.eventEndDate);
        const endDiff = end.getTime() - now.getTime();

        if (endDiff <= 0) return 'Ended';

        const h = Math.floor(endDiff / (1000 * 60 * 60));
        const m = Math.floor((endDiff % (1000 * 60 * 60)) / (1000 * 60));

        if (h > 24) {
          const d = Math.floor(h / 24);
          return `Ends in ${d}d ${h % 24}h`;
        }
        return `Ends in ${h}h ${m}m`;
      }
      return 'Started';
    }

    const hours = Math.floor(diff / (1000 * 60 * 60));
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));

    if (hours > 24) {
      const days = Math.floor(hours / 24);
      return `Starts in ${days}d ${hours % 24}h`;
    }

    return `Starts in ${hours}h ${minutes}m`;
  }

  getExternalLinkIcon(): string {
    return UrlUtils.getIconForUrl(this.displayData?.event.customUrl);
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

    const rotateX = normY * -8;
    const rotateY = normX * 7;

    el.style.transform = `rotateX(${rotateX}deg) rotateY(${rotateY}deg) scale3d(1.01, 1.01, 1.01)`;
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
