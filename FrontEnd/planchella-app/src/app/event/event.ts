import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { EventData } from '../models/event-data';
import { AttachmentService } from '../services/attachment-service';
import { EventAttachment } from '../models/Event-Attachment';

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
  @Input() event!: EventData;

  attachmentStates = new Map<string, AttachmentState>();

  constructor(
    private sanitizer: DomSanitizer,
    private attachmentService: AttachmentService
  ) {}

  ngOnInit() {
    // Preload images and small files automatically
    this.event.attachments?.forEach(att => {
      if (this.shouldAutoLoad(att)) {
        this.loadAttachment(att.id);
      }
    });
  }

  shouldAutoLoad(att: EventAttachment): boolean {
    // Auto-load images under 5MB
    return att.mimeType.startsWith('image') && att.size < 5 * 1024 * 1024;
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
    return mimeType.startsWith('image');
  }

  isPdf(mimeType: string): boolean {
    return mimeType === 'application/pdf';
  }

  isVideo(mimeType: string): boolean {
    return mimeType.startsWith('video');
  }

  isAudio(mimeType: string): boolean {
    return mimeType.startsWith('audio');
  }

  getFileIcon(mimeType: string): string {
    if (this.isImage(mimeType)) return '🖼️';
    if (this.isPdf(mimeType)) return '📄';
    if (this.isVideo(mimeType)) return '🎥';
    if (this.isAudio(mimeType)) return '🎵';
    if (mimeType.includes('word')) return '📝';
    if (mimeType.includes('excel') || mimeType.includes('spreadsheet')) return '📊';
    if (mimeType.includes('powerpoint') || mimeType.includes('presentation')) return '📽️';
    if (mimeType.includes('zip') || mimeType.includes('rar') || mimeType.includes('compressed')) return '🗜️';
    return '📎';
  }

  formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return Math.round((bytes / Math.pow(k, i)) * 100) / 100 + ' ' + sizes[i];
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