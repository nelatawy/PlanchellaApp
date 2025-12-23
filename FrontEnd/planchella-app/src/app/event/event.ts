import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { EventData } from '../models/event-data';
import { EventDisplayData } from '../models/event-display-data';
import { UserDataService } from '../services/user-data-service';
import { AttachmentService } from '../services/attachment-service';
import { EventAttachment } from '../models/event-attachment';
import { EventType, EventSize, MimeType } from '../models/Enums';
import { firstValueFrom } from 'rxjs';
import { MimeTypeUtils, UrlUtils } from '../services/utils';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EventDataService } from '../services/event-data-service';
import { ToastService } from '../services/toast.service';
import { MapService } from '../services/map.service';
import * as L from 'leaflet';
import { ElementRef, ViewChild } from '@angular/core';

interface AttachmentState {
  url: SafeUrl | null;
  loading: boolean;
  error: string | null;
}

@Component({
  selector: 'app-event',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './event.html',
  styleUrls: ['./event.css'],
})
export class EventComponent implements OnInit, OnDestroy {

  isModalOpen = false;
  maxVisibleAttachments = 5;
  @Input() displayData: EventDisplayData | undefined;
  @Input() eventId: number | undefined;

  isStarred: boolean = false;
  userVoteState: 'upvote' | 'downvote' | 'none' = 'none';

  async upvote() {
    if (!this.displayData?.event.id) return;

    try {
      // If already upvoted, remove the vote
      if (this.userVoteState === 'upvote') {
        await this.eventDataService.unvoteEvent(this.displayData.event.id);
        if (this.displayData.event.upvoteCount) {
          this.displayData.event.upvoteCount--;
        }
        this.userVoteState = 'none';
        this.toastService.info('Vote removed');
      } else {
        // If downvoted, adjust downvote count
        if (this.userVoteState === 'downvote' && this.displayData.event.downvoteCount) {
          this.displayData.event.downvoteCount--;
        }

        await this.eventDataService.upvoteEvent(this.displayData.event.id);
        this.displayData.event.upvoteCount = (this.displayData.event.upvoteCount || 0) + 1;
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
        if (this.displayData.event.downvoteCount) {
          this.displayData.event.downvoteCount--;
        }
        this.userVoteState = 'none';
        this.toastService.info('Vote removed');
      } else {
        // If upvoted, adjust upvote count
        if (this.userVoteState === 'upvote' && this.displayData.event.upvoteCount) {
          this.displayData.event.upvoteCount--;
        }

        await this.eventDataService.downvoteEvent(this.displayData.event.id);
        this.displayData.event.downvoteCount = (this.displayData.event.downvoteCount || 0) + 1;
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
      this.isStarred = !this.isStarred;
      this.toastService.success(this.isStarred ? 'Event starred!' : 'Star removed');
    } catch (error) {
      console.error('Error toggling star:', error);
      this.toastService.error('Failed to star event');
    }
  }

  attachmentStates = new Map<string, AttachmentState>();

  constructor(
    private sanitizer: DomSanitizer,
    private attachmentService: AttachmentService,
    private userDataService: UserDataService,
    private route: ActivatedRoute,
    private eventDataService: EventDataService,
    private toastService: ToastService,
    private mapService: MapService
  ) { }

  async ngOnInit() {
    if (this.eventId) {
      await this.loadEvent(this.eventId);
    } else {
      this.route.params.subscribe(async params => {
        const id = params['id'];
        if (id) {
          await this.loadEvent(+id);
        } else if (this.displayData) {
          this.initAttachments();
        }
      });
    }
  }

  async loadEvent(id: number) {
    try {
      const event = await this.eventDataService.getEvent(id);
      if (event) {
        const author = await firstValueFrom(this.userDataService.getUserById(event.authorId));
        this.displayData = { event, author };

        // Initialize vote and star state from event data
        if (event.isUpvoted) {
          this.userVoteState = 'upvote';
        } else if (event.isDownVoted) {
          this.userVoteState = 'downvote';
        } else {
          this.userVoteState = 'none';
        }
        this.isStarred = event.isStarred || false;

        this.initAttachments();
        this.initMap();
      }
    } catch (error) {
      console.error('Error loading event:', error);
    }
  }

  async sendEventMail() {
    try {
      await this.userDataService.sendEventMail(this.eventId!);
      this.toastService.success('Event mail sent successfully');
    } catch (error) {
      console.error('Error sending event mail:', error);
      this.toastService.error('Failed to send event mail');
    }
  }

  private map?: L.Map;

  initMap() {
    if (this.displayData?.event.hasLocation && this.displayData.event.latitude && this.displayData.event.longitude) {
      setTimeout(() => {
        if (this.map) {
          this.map.remove();
        }
        this.map = this.mapService.initMap('event-location-map', [this.displayData!.event.latitude!, this.displayData!.event.longitude!]);
        this.mapService.addMarker(this.map, [this.displayData!.event.latitude!, this.displayData!.event.longitude!], this.displayData!.event.title);

        // Interaction is enabled by default in Leaflet
      }, 100);
    }
  }

  initAttachments() {
    if (this.displayData) {
      this.displayData.event.attachments?.forEach(att => {
        if (this.shouldAutoLoad(att)) {
          this.loadAttachment(att.id);
        }
      });
    }
  }

  shouldAutoLoad(att: EventAttachment): boolean {
    // Auto-load images under 5MB
    return MimeTypeUtils.isImage(att.mimeType) && att.size < 5 * 1024 * 1024;
  }

  loadAttachment(attachmentId: string): void {
    const state = this.attachmentStates.get(attachmentId);

    // Don't reload if already loaded or loading
    if (state?.url || state?.loading) {
      return;
    }

    // Set loading state
    this.attachmentStates.set(attachmentId, {
      url: null,
      loading: true,
      error: null
    });

    this.attachmentService.getAttachmentUrl(attachmentId).subscribe({
      next: (safeUrl: SafeUrl) => {
        this.attachmentStates.set(attachmentId, {
          url: safeUrl,
          loading: false,
          error: null
        });
      },
      error: (error) => {
        console.error('Error loading attachment:', error);
        this.attachmentStates.set(attachmentId, {
          url: null,
          loading: false,
          error: 'Failed to load attachment'
        });
      }
    });
  }

  getAttachmentState(attachmentId: string): AttachmentState {
    return this.attachmentStates.get(attachmentId) || {
      url: null,
      loading: false,
      error: null
    };
  }

  getAttachmentUrl(attachmentId: string): SafeUrl | null {
    return this.getAttachmentState(attachmentId).url;
  }

  isLoading(attachmentId: string): boolean {
    return this.getAttachmentState(attachmentId).loading;
  }

  hasError(attachmentId: string): boolean {
    return !!this.getAttachmentState(attachmentId).error;
  }

  getError(attachmentId: string): string | null {
    return this.getAttachmentState(attachmentId).error;
  }

  isImage(mimeType: string): boolean {
    return MimeTypeUtils.isImage(mimeType);
  }

  isPdf(mimeType: string): boolean {
    return MimeTypeUtils.isPdf(mimeType);
  }

  isVideo(mimeType: string): boolean {
    return MimeTypeUtils.isVideo(mimeType);
  }

  isAudio(mimeType: string): boolean {
    return MimeTypeUtils.isAudio(mimeType);
  }

  getFileIcon(mimeType: string): string {
    return MimeTypeUtils.getIcon(mimeType);
  }

  formatFileSize(bytes: number): string {
    return MimeTypeUtils.formatSize(bytes);
  }

  getRemainingTime(): string {
    if (!this.displayData?.event.hasTime || !this.displayData.event.eventEndDate) return '';

    const now = new Date();
    const end = new Date(this.displayData.event.eventEndDate);
    const diff = end.getTime() - now.getTime();

    if (diff <= 0) return 'Expired';

    const hours = Math.floor(diff / (1000 * 60 * 60));
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));

    if (hours > 24) {
      const days = Math.floor(hours / 24);
      return `${days}d ${hours % 24}h remaining`;
    }

    return `${hours}h ${minutes}m remaining`;
  }

  getExternalLinkIcon(): string {
    return UrlUtils.getIconForUrl(this.displayData?.event.customUrl);
  }

  getVisibleAttachments(): EventAttachment[] {
    return this.displayData?.event.attachments?.slice(0, this.maxVisibleAttachments) || [];
  }

  getRemainingCount(): number {
    const total = this.displayData?.event.attachments?.length || 0;
    return Math.max(0, total - this.maxVisibleAttachments);
  }

  hasMoreAttachments(): boolean {
    return this.getRemainingCount() > 0;
  }

  openModal(): void {
    this.isModalOpen = true;
    document.body.style.overflow = 'hidden';

    // Load all attachments when modal opens
    this.displayData?.event.attachments?.forEach(att => {
      if (!this.getAttachmentUrl(att.id) && !this.isLoading(att.id)) {
        this.loadAttachment(att.id);
      }
    });
  }

  closeModal(): void {
    this.isModalOpen = false;
    document.body.style.overflow = 'auto';
  }




  ngOnDestroy() {
    // Clean up blob URLs to prevent memory leaks
    this.attachmentStates.forEach(state => {
      if (state.url) {
        const urlString = state.url.toString();
        if (urlString.startsWith('blob:')) {
          URL.revokeObjectURL(urlString);
        }
      }
    });
    this.attachmentStates.clear();
  }
}