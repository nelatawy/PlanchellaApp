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

  @Input() displayData?: EventDisplayData = {
    event: {
      id: 1,
      eventType: EventType.CONTEST,
      eventSize: EventSize.MID,
      authorId: Number(localStorage.getItem('userId')),
      communityId: 1,
      title: 'Sample Event',
      description: 'This is a sample event description.',
      creationDate: new Date(),
      upvoteCount: 10,
      downvoteCount: 2,
      eventStartDate: new Date(),
      eventEndDate: new Date(),
      attachments: [
        {
          id: 'att1',
          fileName: 'image1.jpg',
          mimeType: MimeType.IMAGE_JPEG,
          size: 1500000,
        },
        {
          id: 'att2',
          fileName: 'document1.pdf',
          mimeType: MimeType.APPLICATION_PDF,
          size: 3000000,
        },
        {
          id: 'att3',
          fileName: 'video1.mp4',
          mimeType: MimeType.VIDEO_MP4,
          size: 50000000,
        },
        {
          id: 'att4',
          fileName: 'audio1.mp3',
          mimeType: MimeType.AUDIO_MPEG,
          size: 4000000,
        },
        {
          id: 'att5',
          fileName: 'archive1.zip',
          mimeType: MimeType.APPLICATION_ZIP,
          size: 8000000,
        },
        {
          id: 'att6',
          fileName: 'spreadsheet1.xlsx',
          mimeType: MimeType.APPLICATION_XLSX,
          size: 2000000,
        },
        {
          id: 'att7',
          fileName: 'presentation1.pptx',
          mimeType: MimeType.APPLICATION_PPTX,
          size: 2500000,
        }
      ],
    },
    author: {
      id: Number(localStorage.getItem('userId')),
      name: 'Sample Author',
      picUrl: '',
      accountUrl: '',
      bio: '',
      education: '',
      address: '',
      email: ''
    }
  };

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
    private userDataService: UserDataService
  ) { }

  ngOnInit() {
    // If we only have event data partially or from an old source, ensure we have displayData
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