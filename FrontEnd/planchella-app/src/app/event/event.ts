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
import { MimeTypeUtils } from '../services/utils';
import { ActivatedRoute } from '@angular/router';
import { EventDataService } from '../services/event-data-service';

interface AttachmentState {
  url: SafeUrl | null;
  loading: boolean;
  error: string | null;
}

@Component({
  selector: 'app-event',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './event.html',
  styleUrls: ['./event.css'],
})
export class EventComponent implements OnInit, OnDestroy {

  isModalOpen = false;
  maxVisibleAttachments = 5;

  @Input() displayData: EventDisplayData | undefined;
  @Input() eventId: number | undefined;

  isStarred: boolean = false;

  toggleStar() {
    this.isStarred = !this.isStarred;
    // In a real app, this would call a service to update the 'eventStarred' interaction
    console.log(`Event ${this.displayData?.event.id} starred: ${this.isStarred}`);
  }

  attachmentStates = new Map<string, AttachmentState>();

  constructor(
    private sanitizer: DomSanitizer,
    private attachmentService: AttachmentService,
    private userDataService: UserDataService,
    private route: ActivatedRoute,
    private eventDataService: EventDataService
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
        this.initAttachments();
      }
    } catch (error) {
      console.error('Error loading event:', error);
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